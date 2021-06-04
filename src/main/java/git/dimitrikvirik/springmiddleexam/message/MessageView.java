package git.dimitrikvirik.springmiddleexam.message;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageView {


    private int message_id;
    private int user_id;
    private String text;
    private String create_date;
    MessageView(int user_id, String text) {
        this.user_id = user_id;
        this.text = text;
    }
}
