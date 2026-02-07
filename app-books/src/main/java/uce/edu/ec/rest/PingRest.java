package uce.edu.ec.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.enterprise.context.ApplicationScoped;

@Path("/ping")
@ApplicationScoped
public class PingRest {

    @GET
    public String ping(){
        return "pong";
    }
}
