package git.dimitrikvirik.springmiddleexam.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserView {
    int userId;
    String username;
    String firstname;
    String lastname;
    String password;
    String createDate;
}
