package lk.ijse.hostelManagement.view.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentTm {
    private String roomTypeId;
    private String type;
    private String keyMoney;
    private int qty;
}
