package lt.vilniustech.ezukauskas.library_management.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_copy")
@XmlRootElement(name = "bookCopy")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer copyId;

    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false)
    @XmlTransient
    private Book book;

    private String status;

    @OneToMany(mappedBy = "bookCopy", cascade = CascadeType.ALL)
    @XmlTransient
    private List<Loan> loans;
}
