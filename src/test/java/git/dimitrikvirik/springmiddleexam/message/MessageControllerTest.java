package git.dimitrikvirik.springmiddleexam.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import git.dimitrikvirik.springmiddleexam.user.UserService;
import git.dimitrikvirik.springmiddleexam.user.UserView;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@AutoConfigureMockMvc
@SpringBootTest
class MessageControllerTest {

    @Mock
    @Autowired
    private MessageService messageService;

    @Test
    void read() {




    }

    @Test
    void print() {
    }

    @Test
    void checkState() {
    }

    @Test
    void write() {
    }

    @Test
    void edit() {
    }

    @Test
    void delete() {
    }
}