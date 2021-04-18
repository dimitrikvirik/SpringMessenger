package git.dimitrikvirik.springmiddleexam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {
    @RequestMapping(value={"/", "/login", "/chat"})
    ModelAndView index(){
        return new ModelAndView("index");
    }

}
