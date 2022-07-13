package net.durmont.ipchanger.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@RegisterRestClient(configKey = "ipservice")
@Path("/")
public interface IpService {
    @GET
    String getMyIp();
}
