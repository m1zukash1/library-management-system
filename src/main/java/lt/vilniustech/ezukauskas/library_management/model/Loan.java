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
@Table(name = "loan")
@XmlRootElement(name = "loan")
@XmlAccessorType(XmlAccessType.FIELD)
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loanId;

    @ManyToOne
    @JoinColumn(name = "copyId", nullable = false)
    private BookCopy bookCopy;

    // Avoid cycles by hiding the member in the XML
    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    @XmlTransient
    private Member member;

    @Temporal(TemporalType.DATE)
    private Date loanDate;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Temporal(TemporalType.DATE)
    private Date returnDate;
}
