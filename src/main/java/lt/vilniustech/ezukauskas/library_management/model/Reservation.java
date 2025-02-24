package lt.vilniustech.ezukauskas.library_management.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
@XmlRootElement(name = "reservation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false)
    private Book book;

    // Avoid cycles by hiding the member
    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    @XmlTransient
    private Member member;

    @Temporal(TemporalType.DATE)
    private Date reservationDate;

    private String status;
}
