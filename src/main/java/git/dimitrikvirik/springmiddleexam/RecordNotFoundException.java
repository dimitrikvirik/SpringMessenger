package git.dimitrikvirik.springmiddleexam;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String format) {
        super(format);
    }
}
