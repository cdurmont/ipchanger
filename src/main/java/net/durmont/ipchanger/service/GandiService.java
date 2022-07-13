package net.durmont.ipchanger.service;

import net.durmont.ipchanger.model.DnsRecord;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

@RegisterRestClient(configKey = "dnsservice")
@Path("/livedns/domains")
@ClientHeaderParam(name = "Authorization", value = "${quarkus.rest-client.dnsservice.apiKey}")
public interface GandiService {

    @GET
    @Path("/{fqdn}/records/{name}/{type}")
    DnsRecord getDnsRecord(String fqdn, String name, String type);

    @GET
    @Path("/{fqdn}/records")
    List<DnsRecord> getAllDnsRecords(String fqdn);

    @PUT
    @Path("/{fqdn}/records/{name}/{type}")
    Response setDnsRecord(DnsRecord record, @PathParam("fqdn") String fqdn, @PathParam("name") String name, @PathParam("type") String type);
}
