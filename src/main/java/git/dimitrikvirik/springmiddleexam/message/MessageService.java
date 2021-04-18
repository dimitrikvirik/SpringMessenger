package git.dimitrikvirik.springmiddleexam.message;

import git.dimitrikvirik.springmiddleexam.RecordAlreadyExistException;
import git.dimitrikvirik.springmiddleexam.RecordNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface MessageService {
    String write(MessageView messageViews, HttpServletRequest request) throws RecordAlreadyExistException;

    List<Map<String, Object>> read();

    void edit(int messageId, String text, HttpServletRequest request) throws RecordNotFoundException, RecordAlreadyExistException;

    void delete(int messageId, HttpServletRequest request) throws RecordAlreadyExistException;

    long ActionNumber();


}
