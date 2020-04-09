package life.qyh.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageInfo {
    private int page;
    private boolean firstPage;
    private boolean previousPage;
    private boolean afterPage;
    private boolean endPage;
    private int totalPage;
    private List<QuestionDTO> questionDTOList;
    private List<Integer> pages = new ArrayList<>();
}
