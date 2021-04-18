package git.dimitrikvirik.springmiddleexam.user;


import git.dimitrikvirik.springmiddleexam.RecordAlreadyExistException;
import git.dimitrikvirik.springmiddleexam.RecordNotFoundException;
import git.dimitrikvirik.springmiddleexam.UsernameNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class UserServiceImp implements UserService {

    @Autowired
    DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String registration(UserView userView, HttpServletRequest request) throws RecordAlreadyExistException {
        try {
            Map<String, Object> parameters = new HashMap<>();
            userView.setUsername(userView.getUsername().toLowerCase(Locale.ROOT));
            final String forbiddenPrefix = "deleted";

            if (userView.getUsername().startsWith(forbiddenPrefix)) {
                throw new UsernameNotAllowedException(
                        String.format("Username with prefix \"%s\" not allowed", forbiddenPrefix));
            }
            parameters.put("username", userView.getUsername());
            parameters.put("firstname", userView.getFirstname());
            parameters.put("lastname", userView.getLastname());
            parameters.put("password", userView.getPassword());
            System.out.println(userView);
            userTable().execute(parameters);
            return "success";
        } catch (DataAccessException e) {
            if (Objects.requireNonNull(e.getMessage()).contains("duplicate key")) {
                throw new RecordAlreadyExistException(String.format("username %s already exists!", userView.getUsername()));
            }
            throw new RecordAlreadyExistException(String.format("%s", e.getMessage()));
        }
    }

    @Override
    public UserView login(String username, String password, HttpServletRequest request) {
        UserView userView = jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ?", new UserViewRowMapper(), username);
        assert userView != null;
        if (userView.getPassword().equals(password)) {
            addToSession(userView, request);
            return userView;
        }
        throw new RecordNotFoundException(String.format("User with username %s not found or wrong password", username));
    }

    @Override
    public UserView getLoggedUser(HttpServletRequest request) {
        return get((Integer) request.getSession().getAttribute("USER_SESSION_ID"));
    }

    @Override
    public UserView get(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = ?", new UserViewRowMapper(), id);
    }

    @Override
    public String getUsername(int id) {
        return get(id).getUsername();
    }


    public void addToSession(UserView userView, HttpServletRequest request) {
        try {
            request.getSession().setAttribute("USER_SESSION_ID", userView.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession().removeAttribute("USER_SESSION_ID");
    }

    @Override
    public void delete(UserView loggedUser, HttpServletRequest request) {

        //Random password
        byte[] array = new byte[10]; // length is bounded by 10
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);

        Object[] params = {
                "deleted-" + loggedUser.getUserId(),
                generatedString,
                loggedUser.getUserId()
        };
        jdbcTemplate.update("UPDATE users set username = ?, password = ? where user_id = ?", params);
        logout(request);
    }

    @Override
    public void edit(UserView loggedUser, UserView updatedUserView) throws RecordAlreadyExistException {

        Object[] params = {
                updatedUserView.getFirstname() == null ? loggedUser.getFirstname() : updatedUserView.getFirstname(),
                updatedUserView.getLastname() == null ? loggedUser.getLastname() : updatedUserView.getLastname(),
                updatedUserView.getPassword() == null ? loggedUser.getPassword() : updatedUserView.getPassword(),
                loggedUser.getUserId()};

        jdbcTemplate.update("UPDATE users set firstname = ?, lastname = ?, password = ?  where user_id = ?", params);
    }

    private SimpleJdbcInsert userTable() {
        return
                new SimpleJdbcInsert(dataSource).
                        withTableName("users").
                        usingGeneratedKeyColumns("user_id", "create_date");
    }

}
