package lt.vilniustech.ezukauskas.library_management.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_author")
@XmlRootElement(name = "bookAuthor")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookAuthor {

    @EmbeddedId
    private BookAuthorId id;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id", nullable = false, insertable = false, updatable = false)
    @XmlTransient
    private Book book;

    @ManyToOne
    @MapsId("authorId")
    @JoinColumn(name = "author_id", nullable = false, insertable = false, updatable = false)
    @XmlTransient
    private Author author;
}
