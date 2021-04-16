package git.dimitrikvirik.springmiddleexam.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MessageController {
    @Autowired
    private MessageService messageServe;
    @GetMapping("/message")
    List<MessageView> get() {
        return messageServe.read();
    }

    @PostMapping("/message")
    void write(@RequestBody MessageView messageView) {
        messageServe.write(messageView);
    }

    @PutMapping("/message/{id}")
    void edit(@PathVariable int id, @RequestBody String text) {
        messageServe.edit(id, text);
    }

    @DeleteMapping("/message/{id}")
    void delete(@PathVariable int id) {
        messageServe.delete(id);
    }
}
