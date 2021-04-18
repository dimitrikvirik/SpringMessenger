package git.dimitrikvirik.springmiddleexam.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import git.dimitrikvirik.springmiddleexam.RecordAlreadyExistException;
import git.dimitrikvirik.springmiddleexam.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;

    //აბრუნებს შესულ მომხმარებელს
    @GetMapping
    UserView getLoggedUser(HttpServletRequest request){
     try {
         return userService.getLoggedUser(request);
     }catch (Exception e){
         return null;
     }
    }
    @GetMapping("/is-logged")
    boolean isLogged(HttpServletRequest request){
        return getLoggedUser(request) != null;
    }

    @PostMapping("/logout")
     void logout(HttpServletRequest request){
        userService.logout(request);
    }

    //იღებს გარკვეულ მოხმარებელს მოხმარებელს მონაცემთა ბაზებიდან და უბრუნებს ფრონტს
    @GetMapping("/{id}")
    String get(@PathVariable int id) {
            return userService.getUsername(id);
    }
    //მომხმარებლის რეგისტრაცია. ე.წ ჩაწერა ბაზაში
    @PostMapping("/reg")
    String registration(@RequestBody UserView userView, HttpServletRequest request) throws RecordAlreadyExistException {
        return userService.registration(userView, request);
    }
    //მომხმარებლის შესვლა. ე.წ შემოწმება ბაზაში
    @PostMapping("/login")
    UserView login(@RequestBody ObjectNode json, HttpServletRequest request){
        return  userService.login(json.get("username").asText(), json.get("password").asText(), request);
      //  addToSession(userView, request);
    }
    //FIXME
    //მომხმარებლის სახელი,გვარი ანდა პაროლი შეცვლა ONLY[firstname,lastname, password]. ე.წ მონაცემის განახლება ბაზაში
    @PutMapping
    void edit(HttpServletRequest request, @RequestBody UserView userView) throws RecordAlreadyExistException {
        userService.edit(getLoggedUser(request), userView);
    }
    //მომხმარებლის წაშლა. ე.წ მონაცემის ამოშლა ბაზიდან
    @DeleteMapping
    void delete(HttpServletRequest request) {
         userService.delete(getLoggedUser(request), request);
    }

}
