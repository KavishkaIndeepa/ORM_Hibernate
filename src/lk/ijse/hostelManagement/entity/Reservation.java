package lk.ijse.hostelManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Reservation {
    @Id
    private String resId;
    private LocalDate date;
    private String statues;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "studentId", referencedColumnName = "StudentId")
    private Student studentId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roomId", referencedColumnName = "roomId")
    private Room roomId;
    private String keyMoney;
    private Double advance;
}
