package git.dimitrikvirik.springmiddleexam.user;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserViewRowMapper implements RowMapper<UserView> {
    @Override
    public UserView mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserView(
                rs.getInt("user_id"),
                rs.getString("username"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("password"),
                rs.getString("create_date")
        );
    }
}
