package com.cktv.mapper;

import com.cktv.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mgh on 2016/5/29.
 */
@Repository(value="userMapper")
@Component
public interface UserMapper {
    User userLogin(@Param("user_name") String user_name, @Param("user_pwd") String user_pwd);

    List<User> selectUsersByUser_status(@Param("pageSize") long pageSize ,@Param("count") long count,@Param("user_status") long user_status);

    //得到user_status的人的个数
    long lengthOfUsersByUser_Status(long user_status);

    User selectByUser_id(long user_id);

    String selectLengthOfUser();

    void insertUser(User user);

    User selectByUser_name(String user_name);

    void deleteUserByUser_id(long user_id);

    void updateUser_statusByUser_id(@Param("user_status") long user_status,@Param("user_id") long user_id);
}
