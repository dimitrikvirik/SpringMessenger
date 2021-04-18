package git.dimitrikvirik.springmiddleexam.message;

import com.fasterxml.jackson.databind.node.ObjectNode;
import git.dimitrikvirik.springmiddleexam.RecordAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MessageController {
    @Autowired
    private MessageService messageServe;

    //აბრუნებს ყველა მესიჯებს და ფრონტი მაგას აჩვენებს
    @GetMapping
    List<Map<String, Object>> read(HttpServletRequest request, HttpServletResponse response) {
            return messageServe.read();
    }
    //For Debugging
    @GetMapping("/print")
    void print(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            System.out.println(cookie.getName() + " " + cookie.getValue());
        }
    }

    //ვამოწმებთ საჭიროა თუ არა მესიჯების განახლება
    @GetMapping("/check")
    boolean checkState(HttpServletResponse response, HttpServletRequest request) {
        if(request.getCookies() == null) return  false;
        return Arrays.stream(request.getCookies()).anyMatch(cookie
                -> cookie.getName().equals("MESSAGE_ACTIONS") &&
                (Long.parseLong(cookie.getValue()) <  getAndReloadActions(response))
        );
    }
    //ფრონტენდი დასაწყისში იძახებს
    @GetMapping("/initial")
    private void initialState(HttpServletResponse response) {
        Cookie cookie = new Cookie("MESSAGE_ACTIONS", "-1");
        cookie.setPath("/message");
        response.addCookie(cookie);
    }
    //ამატებს მესიჯს მონაცემთა ბაზებში
    @PostMapping
    void write(@RequestBody MessageView messageView, HttpServletRequest request, HttpServletResponse response) throws RecordAlreadyExistException {
        messageServe.write(messageView, request);

    }

    //ცვლის მესიჯს მონაცემთა ბაზებში
    @PutMapping("/{id}")
    void edit(@PathVariable int id, @RequestBody ObjectNode json, HttpServletRequest request, HttpServletResponse response) throws RecordAlreadyExistException {
        messageServe.edit(id, json.get("text").asText(), request);

    }

    //შლის მონაცემთა ბაზიდან მესიჯს
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) throws RecordAlreadyExistException {
        messageServe.delete(id, request);

    }

    //Helper Functions
    private   long getAndReloadActions(HttpServletResponse response) {
        saveState(response);
        return  messageServe.ActionNumber();

    }

    private void saveState(HttpServletResponse response) {
        Cookie cookie = new Cookie("MESSAGE_ACTIONS", Long.toString(messageServe.ActionNumber()));
        cookie.setPath("/message");
        response.addCookie(cookie);
    }


}
