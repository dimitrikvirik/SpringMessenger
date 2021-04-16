package git.dimitrikvirik.springmiddleexam;

public class RecordAlreadyExistException extends Exception {
    public RecordAlreadyExistException(String format) {
        super(format);
    }
}
