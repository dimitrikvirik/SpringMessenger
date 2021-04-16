package git.dimitrikvirik.springmiddleexam.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import git.dimitrikvirik.springmiddleexam.RecordAlreadyExistException;
import git.dimitrikvirik.springmiddleexam.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

@Component
public class UserServiceImp implements UserService {
    Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    String JSON_DATA = "storage/users.json";
    @Autowired
    DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public String registration(UserView userView) throws RecordAlreadyExistException {
        try {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("username", userView.getUsername());
            parameters.put("firstname", userView.getFirstname());
            parameters.put("lastname", userView.getLastname());
            parameters.put("password", userView.getPassword());
            System.out.println(userView);
             userTable().execute(parameters);
             return "success";
        }catch (DataAccessException e){
            if(Objects.requireNonNull(e.getMessage()).contains("duplicate key")){
                throw new RecordAlreadyExistException(String.format("username %s already exists!", userView.getUsername()));
            }
           throw new RecordAlreadyExistException(String.format("%s", e.getMessage()));
        }
    }

    @Override
    public UserView login(String username, String password) {
        List<UserView> userViewList = read();
        Optional<UserView> userViewOptional = userViewList.stream().filter((e) ->
                e.getUsername().equals(username) && e.getPassword().equals(password)
        ).findFirst();

        if (userViewOptional.isPresent()) {
            return userViewOptional.get();
        }

        throw new RecordNotFoundException(String.format("User with username %s not found", username));
    }

    @Override
    public UserView get(int id) {
       return jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = ?",new UserViewRowMapper(), id);
    }

    private List<UserView> read() {
        List<UserView> userViewList = null;
        try {
            userViewList = gson.fromJson(new FileReader(JSON_DATA),
                    new TypeToken<List<UserView>>() {}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return userViewList == null ? new ArrayList<>() : userViewList;
    }

    @Override
    public void delete(int id) {
        var userViews = read();
        Iterator<UserView> userViewIterator = userViews.iterator();
        while (userViewIterator.hasNext()) {
            var userView = userViewIterator.next();
            if (userView.getUserId() == id) {
                userViewIterator.remove();
               // write(userViews);
                return;
            }
        }
        throw new RecordNotFoundException(String.format("Can't find User with id %s", id));
    }

    @Override
    public void edit(int userId, UserView userView) throws RecordAlreadyExistException {
        delete(userId);
        registration(userView);
    }

    private SimpleJdbcInsert userTable(){
        return
                new SimpleJdbcInsert(dataSource).
                        withTableName("users").
                        usingGeneratedKeyColumns("user_id","create_date");
    }

}
