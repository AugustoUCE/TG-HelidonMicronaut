package com.example.bookstore.views;

import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Libros")
@Route(value = "books", layout = MainLayout.class)
public class BooksView extends VerticalLayout {

    private final BookService bookService;
    private final Grid<Book> grid;
    private final ProgressBar progressBar;

    public BooksView() {
        this.bookService = new BookService();

        H2 title = new H2("Libros disponibles");

        progressBar = new ProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);

        Button loadButton = new Button("Cargar datos", event -> {
            loadBooks();
        });

        grid = new Grid<>(Book.class, false);
        grid.addColumn(Book::getIsbn).setHeader("ISBN").setFlexGrow(1);
        grid.addColumn(Book::getTitle).setHeader("Título").setFlexGrow(1);
        grid.addColumn(Book::getPrice).setHeader("Precio").setFlexGrow(1);

        // Añadir listener para click en fila
        grid.addItemClickListener(event -> {
            Book book = event.getItem();
            getUI().ifPresent(ui -> ui.navigate("book-detail/" + book.getIsbn()));
        });

        add(title, loadButton, progressBar, grid);

        setPadding(true);
        setSpacing(true);
        addClassNames(LumoUtility.Padding.LARGE);

        // Cargar libros al iniciar
        loadBooks();
    }

    private void loadBooks() {
        progressBar.setVisible(true);

        // Ejecutar en un thread separado para no bloquear la UI
        new Thread(() -> {
            var books = bookService.getAllBooks();
            getUI().ifPresent(ui -> ui.access(() -> {
                grid.setItems(books);
                progressBar.setVisible(false);
            }));
        }).start();
    }
}
