package life.qyh.community.mapper;

import life.qyh.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

@Mapper
public interface UserMapper {

    @Insert("insert into user values(default, #{account_id}, #{name}, #{token}, #{gmt_create}, #{gmt_modified}, #{avatar_url})")
    void insertUser (User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") int id);
}
