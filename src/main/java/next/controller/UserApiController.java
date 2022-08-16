package next.controller;

import core.annotation.web.Controller;
import core.annotation.web.RequestBody;
import core.annotation.web.RequestMapping;
import core.db.DataBase;
import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dto.UserCreatedDto;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static core.annotation.web.RequestMethod.POST;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;

@Controller
public class UserApiController {

    @RequestMapping(value = "/api/users", method = POST)
    public ModelAndView addUser(@RequestBody UserCreatedDto userCreatedDto, HttpServletResponse response) {
        String userId = userCreatedDto.getUserId();
        if (Objects.nonNull(DataBase.findUserById(userId))) {
            throw new IllegalStateException("이미 사용중인 userId입니다.");
        }

        DataBase.addUser(userCreatedDto.toUser());
        response.setStatus(SC_CREATED);
        response.addHeader("location", String.format("/api/users/%s", userId));

        return new ModelAndView(new JsonView());
    }
}