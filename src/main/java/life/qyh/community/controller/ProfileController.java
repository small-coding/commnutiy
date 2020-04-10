package life.qyh.community.controller;

import com.fasterxml.jackson.databind.deser.impl.NullsAsEmptyProvider;
import life.qyh.community.dto.QuestionDTO;
import life.qyh.community.mapper.UserMapper;
import life.qyh.community.model.User;
import life.qyh.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/profile/{action}")
    public String profile (@PathVariable("action") String action,
                           HttpServletRequest request,
                           @RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
                           @RequestParam(name = "size", defaultValue = "5") int size) {
        if("questions".equals(action)) {
            request.setAttribute("section", "questions");
            request.setAttribute("sectionName", "我的问题");
            User user = (User) request.getSession().getAttribute("user");
//            if (user == null) {
//                return "redirect:/";
//            }
            List<QuestionDTO> list = questionService.selList(user, currentPage, size);
            request.setAttribute("list", list);
            request.setAttribute("pageInfo", questionService.pageInfo);
            return "profile";
        }
        return "";
    }

}
