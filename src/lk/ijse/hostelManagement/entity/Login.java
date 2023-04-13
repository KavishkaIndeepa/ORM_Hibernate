package lk.ijse.hostelManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    private String userID;
    private String name;
    private String address;
    private String contact_no;
    private String Password;
    private String gender;
}
