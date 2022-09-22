package maktab.team.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import maktab.team.library.utils.enums.BookStatus;

@Getter
@Setter
@NoArgsConstructor
public class Book {
    private int id;
    private String title;
    private BookStatus status;
    private String feedback;
    private int isbn;

    public Book(int id, String title, BookStatus status, String feedback, int isbn) {
        this.title = title;
        this.status = status;
        this.feedback = feedback;
        this.isbn = isbn;
    }

    public Book(int id, String title, BookStatus status, int isbn) {
        this(id, title, status, null, isbn);
    }
}
