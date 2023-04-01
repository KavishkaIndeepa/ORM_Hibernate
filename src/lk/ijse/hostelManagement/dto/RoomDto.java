package lk.ijse.hostelManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RoomDto {
    private String roomTypeId;
    private String type;
    private String keyMoney;
    private int qty;

}
