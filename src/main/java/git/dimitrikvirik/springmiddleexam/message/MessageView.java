package git.dimitrikvirik.springmiddleexam.message;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MessageView {

    private String userId;
    private int messageId;
    private String text;
    private String createDate;
    MessageView(String userId, String text) {
        this.userId = userId;
        this.text = text;
    }
}
