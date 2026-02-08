package uce.edu.ec.rest;

import uce.edu.ec.dto.BookDto;
import uce.edu.ec.db.Book;
import uce.edu.ec.clients.AuthorRestClient;
import uce.edu.ec.repo.BookRepository;  // ← NUEVO
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.modelmapper.ModelMapper;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BookRest {

    @Inject
    @RestClient
    private AuthorRestClient authorRestClient;

    @Inject
    private BookRepository bookRepository;  // ← NUEVO

    @Inject
    private ModelMapper mapper;

    @GET
    @Path("/{isbn}")
    public Response findByIsbn(@PathParam("isbn") String isbn) {
        System.out.println("📚 Buscando libro con ISBN: " + isbn);

        Book book = bookRepository.findByIsbn(isbn);  // ← CAMBIADO

        if (book == null) {
            System.err.println("❌ Libro no encontrado: " + isbn);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        System.out.println("✅ Libro encontrado: " + book.getTitle());
        var authors = authorRestClient.findByBook(isbn);

        BookDto dto = new BookDto();
        mapper.map(book, dto);
        dto.setAuthors(authors);

        return Response.ok(dto).build();
    }

    @GET
    @Path("/all")
    public List<BookDto> findAll() {
        System.out.println("📚 Obteniendo todos los libros...");

        List<Book> books = bookRepository.findAll();  // ← CAMBIADO
        System.out.println("✅ Total de libros: " + books.size());

        return books.stream()
                .map(book -> {
                    System.out.println("  - Procesando: " + book.getIsbn());

                    BookDto dto = new BookDto();
                    mapper.map(book, dto);

                    try {
                        var authors = authorRestClient.findByBook(book.getIsbn());
                        dto.setAuthors(authors);
                        System.out.println("    ✅ Autores: " + authors.size());
                    } catch (Exception e) {
                        System.err.println("    ⚠️ Error: " + e.getMessage());
                        dto.setAuthors(List.of());
                    }

                    return dto;
                })
                .toList();
    }

    @GET
    @Path("/test")
    public String test() {
        return "test app-books helidon funciona ✅";
    }
}