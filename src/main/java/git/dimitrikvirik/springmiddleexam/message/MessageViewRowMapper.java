package git.dimitrikvirik.springmiddleexam.message;

import git.dimitrikvirik.springmiddleexam.user.UserView;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageViewRowMapper implements RowMapper<MessageView> {
    @Override
    public MessageView mapRow(ResultSet resultSet, int i) throws SQLException {
        return new MessageView(
                resultSet.getInt("message_id"),
                resultSet.getInt("user_id"),
                resultSet.getString("text"),
                resultSet.getString("create_date")
        );
    }
}
