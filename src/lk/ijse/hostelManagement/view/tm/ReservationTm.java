package lk.ijse.hostelManagement.view.tm;

import lk.ijse.hostelManagement.entity.Room;
import lk.ijse.hostelManagement.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationTm {
    private String resId;
    private LocalDate date;
    private String statues;
    private Student studentId;
    private Room roomId;
    private String keyMoney;
    private Double advance;
}
