package lk.ijse.hostelManagement.view.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginTm {
    private String userID;
    private String name;
    private String address;
    private String contact_no;
    private String Password;
    private String gender;
}
