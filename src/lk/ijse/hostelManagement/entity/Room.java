package lk.ijse.hostelManagement.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Room {
    @Id
    @Column(columnDefinition = "VARCHAR(200)")
    private String roomId;
    private String type;
    private String keyMoney;
    private int qty;

    @OneToMany(mappedBy = "roomId", fetch = FetchType.EAGER)
    private List<Reservation> roomDetails = new ArrayList<>();

    public Room(String roomId, String type, String keyMoney, int qty) {
        this.roomId = roomId;
        this.type = type;
        this.keyMoney = keyMoney;
        this.qty = qty;
    }
}
