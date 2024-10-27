package lk.ijse.gdse71.finalproject.jotit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date dateOfBirth;
}
