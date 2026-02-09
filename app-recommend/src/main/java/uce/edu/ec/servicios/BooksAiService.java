package uce.edu.ec.servicios;



import uce.edu.ec.dto.BookRecDto;

import java.util.List;

public interface BooksAiService {

    /**
     * Recomienda libros basándose en un título dado
     * @param title Título del libro de referencia
     * @return Lista de libros recomendados
     */
    List<BookRecDto> recommend(String title);
}