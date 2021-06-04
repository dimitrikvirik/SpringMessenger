package git.dimitrikvirik.springmiddleexam.user;

import git.dimitrikvirik.springmiddleexam.RecordNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class UserControllerTest {


    @Mock
    @Autowired
   private UserService userService;

    @Test()
    void get() {

    }
}