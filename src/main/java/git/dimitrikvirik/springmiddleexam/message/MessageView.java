package git.dimitrikvirik.springmiddleexam.message;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageView {


    private int messageId;
    private int userId;
    private String text;
    private String createDate;
    MessageView(int userId, String text) {
        this.userId = userId;
        this.text = text;
    }
}
