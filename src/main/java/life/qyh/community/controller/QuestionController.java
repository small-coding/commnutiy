package life.qyh.community.controller;

import life.qyh.community.dto.QuestionDTO;
import life.qyh.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {

    @Resource
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question (@PathVariable("id") int id,
                            HttpServletRequest request) {
        QuestionDTO questionDTO = questionService.getById (id);
        if (questionDTO != null) {
            questionService.setView_Count(questionDTO);
        }
        request.setAttribute("question", questionDTO);
        return "question";
    }

}
