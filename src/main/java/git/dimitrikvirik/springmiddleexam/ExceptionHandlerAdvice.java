package git.dimitrikvirik.springmiddleexam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionHandlerAdvice {
    @ExceptionHandler(RecordAlreadyExistException.class)
    public ResponseEntity handleException(RecordAlreadyExistException e) {
        // log exception
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Error Message");
    }
}
