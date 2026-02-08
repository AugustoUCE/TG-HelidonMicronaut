package uce.edu.ec.rest;


import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import lombok.AllArgsConstructor;

@Controller("/ping")
@AllArgsConstructor
public class PingRest {

    @Get(produces = MediaType.TEXT_PLAIN)
    public String ping() {
        return "Pong from Customers Service";
    }


}
