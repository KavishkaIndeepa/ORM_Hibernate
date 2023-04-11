package lk.ijse.hostelManagement.view.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTn {
    private String roomTypeId;
    private String type;
    private String keyMoney;
    private int qty;
}
