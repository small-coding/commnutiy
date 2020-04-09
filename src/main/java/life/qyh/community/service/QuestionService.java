package life.qyh.community.service;

import life.qyh.community.dto.PageInfo;
import life.qyh.community.dto.QuestionDTO;
import life.qyh.community.mapper.QuestionMapper;
import life.qyh.community.mapper.UserMapper;
import life.qyh.community.model.Question;
import life.qyh.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    public PageInfo pageInfo = new PageInfo();

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuestionMapper questionMapper;

    public List<QuestionDTO> selList (int currentPage, int size) {
        int totalCount = questionMapper.selCount();
        int totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
        if (currentPage < 1) {
            currentPage = 1;
        } else if (currentPage > totalPage) {
            currentPage = totalPage;
        }
        int offset = (currentPage - 1) * size;
        List<Question> questionList = questionMapper.selByPage(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageInfo.setQuestionDTOList(questionDTOList);
        pageInfo.setPage(currentPage);
        setPage(totalCount, size, currentPage, totalPage);
        return questionDTOList;
    }

    public void setPage (int totalCount, int size, int currentPage, int totalPage) {
        pageInfo.setTotalPage(totalPage);
        if (currentPage < 1) {
            currentPage = 1;
        } else if (currentPage > totalPage) {
            currentPage = totalPage;
        }
        List<Integer> list = new ArrayList<>();
        list.add(currentPage);
        for (int i = 1; i <= 3; i ++) {
            if (currentPage - i > 0) {
                list.add(0, currentPage - i);
            }
            if (currentPage + i <= totalPage) {
                list.add(currentPage + i);
            }
        }
        pageInfo.setPages(list);
        if (currentPage == 1) {
            pageInfo.setAfterPage(true);
            pageInfo.setFirstPage(false);
            pageInfo.setPreviousPage(false);
        } else {
            pageInfo.setPreviousPage(true);
        }
        if (currentPage == totalPage) {
            pageInfo.setPreviousPage(true);
            pageInfo.setEndPage(false);
            pageInfo.setAfterPage(false);
        } else {
            pageInfo.setAfterPage(true);
        }
        if (!pageInfo.getPages().contains(1)) {
            pageInfo.setFirstPage(true);
        } else {
            pageInfo.setFirstPage(false);
        }
        if (!pageInfo.getPages().contains(totalPage)) {
            pageInfo.setEndPage(true);
        } else {
            pageInfo.setEndPage(false);
        }
    }

}
