package lk.ijse.hostelManagement.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Room {
    @Id
    private String roomTypeId;
    private String type;
    private String keyMoney;
    private int qty;

    @OneToMany(mappedBy = "roomTypeId", fetch = FetchType.EAGER)
    private List<Reservation> roomDetails = new ArrayList<>();

    public Room(String roomTypeId, String type, String keyMoney, int qty) {
        this.roomTypeId = roomTypeId;
        this.type = type;
        this.keyMoney = keyMoney;
        this.qty = qty;
    }
}
