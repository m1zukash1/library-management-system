package lt.vilniustech.ezukauskas.library_management.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")
@XmlRootElement(name = "member")
@XmlAccessorType(XmlAccessType.FIELD)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    private String firstName;
    private String lastName;
    private String email;

    @Temporal(TemporalType.DATE)
    private Date membershipDate;

    /**
     * We now expose loans in the XML so we can see which books the member has borrowed.
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Loan> loans;

    /**
     * We now expose reservations in the XML so we can see which books the member has reserved.
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reservation> reservations;
}
