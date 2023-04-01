package lk.ijse.hostelManagement.dto;

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
}
