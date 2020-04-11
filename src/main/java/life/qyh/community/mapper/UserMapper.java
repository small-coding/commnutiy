package life.qyh.community.mapper;

import life.qyh.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into user values(default, #{account_id}, #{name}, #{token}, #{gmt_create}, #{gmt_modified}, #{avatar_url})")
    void insertUser (User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where account_id=#{creator}")
    User findById(@Param("creator") String creator);

    @Update("update user set name=#{name}, token=#{token}, gmt_modified=#{gmt_modified}, avatar_url=#{avatar_url} where id=#{id}")
    void updateUser(User user);
}
