package life.qyh.community.model;

import lombok.Data;

@Data
public class Question {
    private int id;
    private String title;
    private String description;
    private long gmt_create;
    private long gmt_modified;
    private String creator;
    private int comment_count;
    private int view_count;
    private int like_count;
    private String tag;
}
