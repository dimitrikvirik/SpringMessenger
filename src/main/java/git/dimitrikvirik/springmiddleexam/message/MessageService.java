package git.dimitrikvirik.springmiddleexam.message;

import git.dimitrikvirik.springmiddleexam.RecordNotFoundException;

import java.util.List;

public interface MessageService {
    void write(MessageView messageViews);

    void write(List<MessageView> messageViewList);

    List<MessageView> read();

    void edit(int messageId, String text) throws RecordNotFoundException;

    void delete(int messageId);
}
