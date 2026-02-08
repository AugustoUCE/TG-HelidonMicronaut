package uce.edu.ec.clients;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import uce.edu.ec.dto.AuthorDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient(configKey = "authors-api")
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AuthorRestClient {

    @GET
    @Path("/isbn/{isbn}")  // ← RUTA CORREGIDA
    @Retry(maxRetries = 4, delay = 100)
    @Fallback(fallbackMethod = "findByBookFallback")
    List<AuthorDto> findByBook(@PathParam("isbn") String isbn);

    default List<AuthorDto> findByBookFallback(String isbn) {
        System.err.println(" FALLBACK activado para ISBN: " + isbn);
        var dto = new AuthorDto();
        dto.setId(0);
        dto.setName("Service Unavailable");
        return List.of(dto);
    }

    @GET
    @Path("/")
    List<AuthorDto> findAllAuthors();
}