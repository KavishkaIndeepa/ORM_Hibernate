package lk.ijse.hostelManagement.view.tm;

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
}
