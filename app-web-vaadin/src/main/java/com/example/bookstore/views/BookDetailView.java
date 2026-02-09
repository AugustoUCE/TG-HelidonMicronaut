package com.example.bookstore.views;

import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Detalle del Libro")
@Route(value = "book-detail/:isbn", layout = MainLayout.class)
public class BookDetailView extends VerticalLayout implements BeforeEnterObserver {

    private BookService bookService;
    private String isbn;
    private ProgressBar progressBar;
    private H2 title;
    private Paragraph priceLabel;
    private Paragraph inventoryLabel;
    private Grid<Author> authorsGrid;

    public BookDetailView() {
        this.bookService = new BookService();

        title = new H2();
        priceLabel = new Paragraph();
        inventoryLabel = new Paragraph();

        progressBar = new ProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);

        authorsGrid = new Grid<>(Author.class, false);
        authorsGrid.addColumn(Author::getName).setHeader("Autores");

        Button backButton = new Button("Volver", event -> {
            getUI().ifPresent(ui -> ui.navigate("books"));
        });

        add(progressBar, title, priceLabel, inventoryLabel, new H2("Autores"), authorsGrid, backButton);

        setPadding(true);
        setSpacing(true);
        addClassNames(LumoUtility.Padding.LARGE);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        isbn = event.getRouteParameters().get("isbn").orElse("");
        if (isbn.isEmpty()) {
            event.forwardTo("books");
        } else {
            loadBookDetail();
        }
    }

    private void loadBookDetail() {
        progressBar.setVisible(true);

        // Ejecutar en un thread separado
        new Thread(() -> {
            Book book = bookService.getBookByIsbn(isbn);
            getUI().ifPresent(ui -> ui.access(() -> {
                if (book != null) {
                    title.setText("Título: " + book.getTitle());
                    priceLabel.setText("Precio: $" + book.getPrice());
                    inventoryLabel.setText("Inventario - Vendidos: " + book.getInventorySold() 
                            + " | Suministrados: " + book.getInventorySupplied());
                    authorsGrid.setItems(book.getAuthors() != null ? book.getAuthors() : new java.util.ArrayList<>());
                } else {
                    title.setText("Libro no encontrado");
                }
                progressBar.setVisible(false);
            }));
        }).start();
    }
}
