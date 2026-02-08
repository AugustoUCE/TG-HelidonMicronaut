package com.example.bookstore.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Inicio")
@Route(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    public HomeView() {
        setSpacing(false);

        H2 title = new H2("Bienvenido");
        Paragraph intro = new Paragraph("Aplicación creada con Vaadin Flow y Java.");

        add(title, intro);

        setPadding(true);
        setAlignItems(Alignment.START);
        addClassNames(LumoUtility.Padding.LARGE);
    }
}
