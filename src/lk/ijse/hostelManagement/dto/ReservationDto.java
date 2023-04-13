package lk.ijse.hostelManagement.dto;

import lk.ijse.hostelManagement.entity.Room;
import lk.ijse.hostelManagement.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReservationDto {
    private String resId;
    private LocalDate date;
    private String statues;
    private String studentId;
    private String roomId;
    private String keyMoney;
    private Double advance;


}
