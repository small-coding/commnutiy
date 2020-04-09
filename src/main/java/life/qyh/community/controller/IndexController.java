package life.qyh.community.controller;

import life.qyh.community.dto.QuestionDTO;
import life.qyh.community.mapper.QuestionMapper;
import life.qyh.community.mapper.UserMapper;
import life.qyh.community.model.User;
import life.qyh.community.service.QuestionService;
import oracle.jrockit.jfr.events.RequestableEventEnvironment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionService QuestionService;

    @GetMapping("/")
    public String index (HttpServletRequest request,
                         @RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
                         @RequestParam(name = "size", defaultValue = "5") int size) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        List<QuestionDTO> questionDTOList = QuestionService.selList(currentPage, size);
        request.setAttribute("questionDTOList", questionDTOList);
        request.setAttribute("pageInfo", QuestionService.pageInfo);
        return "index";
    }

}
