package git.dimitrikvirik.springmiddleexam.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import git.dimitrikvirik.springmiddleexam.RecordAlreadyExistException;
import git.dimitrikvirik.springmiddleexam.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    Object get(@PathVariable int id) {
        try {
            return userService.get(id);
        } catch (RecordNotFoundException e) {
            return e.getMessage();
        }
    }
    @GetMapping("/joined")
    UserView isUserLogged(HttpServletRequest request){
    /*  return (UserView) request.getSession().getAttribute("USER_SESSION");*/
        return null;
    }

    @PostMapping("/reg")
    String reg(@RequestBody UserView userView) throws RecordAlreadyExistException {
        return userService.registration(userView);

      //  addToSession(userView, request);
    }
    @PostMapping("/login")
    void login(@RequestBody ObjectNode json, HttpServletRequest request){
        UserView userView =  userService.login(json.get("username").asText(), json.get("password").asText());
      //  addToSession(userView, request);
    }
    @PutMapping("/{id}")
    void edit(@PathVariable int id, @RequestBody UserView userView) throws RecordAlreadyExistException {
        userService.edit(id, userView);
    }
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        userService.delete(id);
    }
/*
    void addToSession(UserView userView, HttpServletRequest request){
       @SuppressWarnings("unchecked")
        List<String> user = (List<String>) request.getSession().getAttribute("USER_SESSION");
        if (user == null) {
            user = new ArrayList<>();
            request.getSession().setAttribute("USER_SESSION", user);
        }
        request.getSession().setAttribute("USER_SESSION", userView);
        request.getSession().setAttribute("USER_SESSION_LOGIN", true);
    }
    */

}
