package uce.edu.ec.rest;

import uce.edu.ec.db.Author;
import uce.edu.ec.repo.AuthorRepository;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class AuthorRest {

    @Inject
    private AuthorRepository authorRepository;

    private Integer httpPort;

    private AtomicInteger index = new AtomicInteger(0);

    @PostConstruct
    void init() {
        Config config = ConfigProvider.getConfig();
        httpPort = config.getOptionalValue("server.port", Integer.class).orElse(8080);
    }

    @GET
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) {
        Author author = authorRepository.findById(id);
        if (author != null) {
            author.setName(author.getName() + " " + httpPort);
            return Response.ok(author).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/isbn/{isbn}")
    public List<Author> findByBook(@PathParam("isbn") String isbn) {
        return authorRepository.findByBook(isbn).stream()
                .map(obj -> {
                    obj.setName(String.format("%s (%s)", obj.getName(), httpPort));
                    return obj;
                })
                .toList();
    }

    @GET
    @Path("/test")
    public String test() {
        var config = org.eclipse.microprofile.config.ConfigProvider.getConfig();
        String url = config.getValue("mp.datasource.db.url", String.class);
        Integer puerto = config.getValue("server.port", Integer.class);
        return String.format("DB URL: %s, Port: %d", url, puerto);
    }
}
