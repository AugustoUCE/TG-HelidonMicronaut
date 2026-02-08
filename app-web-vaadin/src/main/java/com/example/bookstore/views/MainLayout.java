package com.example.bookstore.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.theme.lumo.LumoUtility;

@RoutePrefix("")
public class MainLayout extends AppLayout implements HasDynamicTitle {

    private H1 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Books Store");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);

        SideNav nav = new SideNav();
        nav.addItem(
                new SideNavItem("Inicio", HomeView.class, VaadinIcon.HOME.create()),
                new SideNavItem("Libros", BooksView.class, VaadinIcon.BOOK.create())
        );

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.MEDIUM);

        addToDrawer(appName, scroller);
    }

    @Override
    public String getPageTitle() {
        return viewTitle.getText();
    }
}
