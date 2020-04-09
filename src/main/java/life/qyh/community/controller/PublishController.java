package life.qyh.community.controller;

import life.qyh.community.mapper.QuestionMapper;
import life.qyh.community.mapper.UserMapper;
import life.qyh.community.model.Question;
import life.qyh.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish () {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish (@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("tag") String tag,
                             HttpServletRequest request) {
        request.setAttribute("title", title);
        request.setAttribute("description", description);
        request.setAttribute("tag", tag);
        if (title == null || title == "") {
            request.setAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            request.setAttribute("error", "描述内容不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            request.setAttribute("error", "标签不能为空");
            return "publish";
        }
        Cookie[] cookies = request.getCookies();
        User user = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if (user == null) {
            request.setAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        questionMapper.insertQuestion(question);
        return "redirect:/";
    }

}
