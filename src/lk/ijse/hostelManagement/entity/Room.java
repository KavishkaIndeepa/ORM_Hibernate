package lk.ijse.hostelManagement.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Room {
    private String roomTypeId;
    private String type;
    private String keyMoney;
    private int qty;

}
