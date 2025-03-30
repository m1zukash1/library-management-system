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
@Table(name = "book")
@XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.FIELD)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    private String title;
    private String isbn;
    private int publicationYear;

    @ManyToOne
    @JoinColumn(name = "publisherId", nullable = false)
    @XmlTransient
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    @XmlTransient
    private Category category;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @XmlTransient
    private java.util.List<BookCopy> copies;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @XmlTransient
    private java.util.List<Reservation> reservations;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @XmlTransient
    private java.util.List<BookAuthor> bookAuthors;
}
