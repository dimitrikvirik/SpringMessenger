package git.dimitrikvirik.springmiddleexam;

public class UsernameNotAllowedException extends RuntimeException {

    public UsernameNotAllowedException(String format) {
        super(format);
    }
}
