package net.durmont.ipchanger.model;

import java.util.List;

public class DnsRecord {

    String rrset_href;
    String rrset_name;
    String rrset_type;
    List<String> rrset_values;
    int rrset_ttl = 1800;

    public String getRrset_href() {
        return rrset_href;
    }

    public void setRrset_href(String rrset_href) {
        this.rrset_href = rrset_href;
    }

    public String getRrset_name() {
        return rrset_name;
    }

    public void setRrset_name(String rrset_name) {
        this.rrset_name = rrset_name;
    }

    public String getRrset_type() {
        return rrset_type;
    }

    public void setRrset_type(String rrset_type) {
        this.rrset_type = rrset_type;
    }

    public List<String> getRrset_values() {
        return rrset_values;
    }

    public void setRrset_values(List<String> rrset_values) {
        this.rrset_values = rrset_values;
    }

    public int getRrset_ttl() {
        return rrset_ttl;
    }

    public void setRrset_ttl(int rrset_ttl) {
        this.rrset_ttl = rrset_ttl;
    }

    @Override
    public String toString() {
        return "DnsRecord{" +
                "rrset_href='" + rrset_href + '\'' +
                ", rrset_name='" + rrset_name + '\'' +
                ", rrset_type='" + rrset_type + '\'' +
                ", rrset_ttl=" + rrset_ttl +
                ", rrset_values=" + rrset_values +
                '}';
    }
}
