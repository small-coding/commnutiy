package life.qyh.community.controller;

import life.qyh.community.dto.QuestionDTO;
import life.qyh.community.mapper.QuestionMapper;
import life.qyh.community.mapper.UserMapper;
import life.qyh.community.model.Question;
import life.qyh.community.model.User;
import life.qyh.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Resource
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish () {
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String updateQuestion (@PathVariable("id") int id,
                                  HttpServletRequest request) {
        QuestionDTO question = questionService.getById(id);
        request.setAttribute("title", question.getTitle());
        request.setAttribute("description", question.getDescription());
        request.setAttribute("tag", question.getTag());
        request.setAttribute("id", id);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish (@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("tag") String tag,
                             @RequestParam(value = "id") String id,
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
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getAccount_id());
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        if (id == null || "".equals(id)) {
            questionService.createOrUpdateQuestion(0, question);
        } else {
            questionService.createOrUpdateQuestion(Integer.parseInt(id), question);
        }
//        questionMapper.insertQuestion(question);
        return "redirect:/";
    }

}
