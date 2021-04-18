package git.dimitrikvirik.springmiddleexam.message;


import git.dimitrikvirik.springmiddleexam.RecordAlreadyExistException;
import git.dimitrikvirik.springmiddleexam.RecordNotFoundException;
import git.dimitrikvirik.springmiddleexam.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageServiceImp implements MessageService {
    @Autowired
    DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;
    @Override
    public String write(MessageView messageView, HttpServletRequest request) throws RecordAlreadyExistException {
        try {
            Map<String, Object> parameters = new HashMap<>();
            int userId = userService.getLoggedUser(request).getUserId();
            parameters.put("text", messageView.getText());
            parameters.put("user_id", userId);
            messageTable().execute(parameters);
            addAction(userId, "write");
            return "success";
        } catch (DataAccessException e) {
            throw new RecordAlreadyExistException(String.format("%s", e.getMessage()));
        }
    }


    @Override
    public List<Map<String, Object>> read() {
       var list = jdbcTemplate.queryForList("SELECT * FROM messages ORDER BY message_id");
        for (Map<String, Object> msg : list) {
            msg.put("username", userService.get(
                    (Integer) msg.get("user_id")
            ).getUsername());
        }
        return list;
    }

    @Override
    public void edit(int messageId, String text, HttpServletRequest request) throws RecordNotFoundException, RecordAlreadyExistException {
        int userId = userService.getLoggedUser(request).getUserId();

        // define query arguments
        Object[] params = {text, userId, messageId};

        jdbcTemplate.update("UPDATE messages set text = ? where user_id = ? and message_id = ?", params);
        addAction(userId, "edit");

    }

    @Override
    public void delete(int messageId, HttpServletRequest request) throws RecordAlreadyExistException {
        int userId = userService.getLoggedUser(request).getUserId();

        // define query arguments
        Object[] params = { userId, messageId };


        // define SQL types of the arguments
        int[] types = {Types.BIGINT, Types.BIGINT};


        int rows = jdbcTemplate.update("DELETE FROM messages WHERE user_id = ? AND message_id = ?", params, types);
        addAction(userId, "delete");
        System.out.println(rows + " row(s) deleted.");

    }

    @Override
    public long ActionNumber() {

      var list =  jdbcTemplate.queryForList("SELECT COUNT(*) FROM message_actions");
      var map = list.get(0);
     return (long) map.get("count");
    }

    private SimpleJdbcInsert messageTable() {
        return
                new SimpleJdbcInsert(dataSource).
                        withTableName("messages").
                        usingGeneratedKeyColumns("message_id", "create_date");
    }
    private SimpleJdbcInsert messageActionTable() {
        return
                new SimpleJdbcInsert(dataSource).
                        withTableName("message_actions").
                        usingGeneratedKeyColumns("message_action_id", "create_date");
    }
    private void addAction(int userId, String type) throws RecordAlreadyExistException {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("type", type);
            parameters.put("user_id", userId);
            messageActionTable().execute(parameters);

        } catch (DataAccessException e) {
            throw new RecordAlreadyExistException(String.format("%s", e.getMessage()));
        }
    }
}
