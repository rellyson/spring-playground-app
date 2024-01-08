package io.rellyson.playground.core.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FakeController {
    @RequestMapping(path = "/fake")
    public boolean fakeRequest() {
        return true;
    }
}
