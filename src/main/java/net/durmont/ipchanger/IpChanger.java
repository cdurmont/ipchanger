package net.durmont.ipchanger;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import net.durmont.ipchanger.model.DnsRecord;
import net.durmont.ipchanger.service.GandiService;
import net.durmont.ipchanger.service.IpService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.util.Collections;

@ApplicationScoped
public class IpChanger {

    @RestClient
    IpService ipService;
    @RestClient
    GandiService gandiService;

    @ConfigProperty(name = "ipchanger.timerCron")
    private String timerCron;
    @ConfigProperty(name = "ipchanger.domain.fqdn")
    private String fqdn;
    @ConfigProperty(name = "ipchanger.domain.recordName")
    private String recordName;
    @ConfigProperty(name = "ipchanger.domain.recordType")
    private String recordType;

    private String dnsARecordIp = null;

    @Scheduled(cron = "{ipchanger.timerCron}")
    void check() {
        String ip = ipService.getMyIp();
        Log.debug("Public IP : " + ip);
        if (dnsARecordIp == null) {
            Log.info("DNS A record IP unknown, fetching...");
            DnsRecord dnsRecord = gandiService.getDnsRecord(fqdn, recordName, recordType);
            if (dnsRecord != null && dnsRecord.getRrset_values() != null && dnsRecord.getRrset_values().size()>0) {
                dnsARecordIp = dnsRecord.getRrset_values().get(0);
                Log.debug("ip fetched: " + dnsARecordIp);
            }
        }
        else
            Log.debug("Cached DNS ip: " + dnsARecordIp);

        if (dnsARecordIp != null) {
            if (!dnsARecordIp.equals(ip)) {
                Log.info("DNS ip not up-to-date ! Updating...");
                DnsRecord record = new DnsRecord();
                record.setRrset_values(Collections.singletonList(ip));

                try (Response response = gandiService.setDnsRecord(record, fqdn, recordName, recordType)) {
                    Log.info("PUT response : " + response.getStatus() + " " + response.getStatusInfo().getReasonPhrase());
                }
            }
            else
                Log.debug("Cached DNS up-to-date, going back to sleep");
        }
    }
}
