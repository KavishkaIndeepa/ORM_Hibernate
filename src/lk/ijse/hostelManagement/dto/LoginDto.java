package lk.ijse.hostelManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String userID;
    private String name;
    private String address;
    private String contact_no;
    private String Password;
    private String gender;
}
