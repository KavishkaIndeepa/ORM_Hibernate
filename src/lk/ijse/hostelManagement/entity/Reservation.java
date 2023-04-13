package lk.ijse.hostelManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reservation {
    @Id
    @Column(columnDefinition = "VARCHAR(200)")
    private String resId;
    private LocalDate date;
    private String statues;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "studentId", referencedColumnName = "studentId")
    private Student studentId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roomId", referencedColumnName = "roomId")
    private Room roomId;
    private String keyMoney;
    private Double advance;
}
