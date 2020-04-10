package life.qyh.community.mapper;

import com.sun.scenario.effect.Offset;
import life.qyh.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (id, title, description, gmt_create, gmt_modified, creator, tag) values (default, #{title}, #{description}, #{gmt_create}, #{gmt_modified}, #{creator}, #{tag})")
    void insertQuestion (Question question);

    @Select("select * from question")
    List<Question> selList();

    @Select("select * from question limit #{offset}, #{size}")
    List<Question> selByPage(@Param("offset") int offset, @Param("size") int size);

    @Select("select count(1) from question")
    int selCount ();

    @Select("select count(1) from question where creator=#{creator}")
    int selById(@Param("creator") String creator);

    @Select("select * from question where creator=#{creator} limit #{offset}, #{size}")
    List<Question> selByIdPage(@Param("creator") String creator, @Param("offset") int offset, @Param("size") int size);
}
