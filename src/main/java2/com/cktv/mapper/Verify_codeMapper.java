package com.cktv.mapper;

import com.cktv.domain.Verify_code;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by mgh on 2016/6/3.
 */
@Component
@Repository(value="verify_codeMapper")
public interface Verify_codeMapper {
    Verify_code selectByPrimaryKey(String verify_code);

    void updateVerify_codeIs_register(@Param("verify_code_id") long verify_code_id, @Param("is_register") long is_register);

    void insertVerify_code(Verify_code verify_code);

    List<Verify_code> selectVerify_codeByIs_register(@Param("is_register") long is_register,@Param("count") long count,@Param("pageSize") long pageSize);

    Verify_code selectByVerify_code_id(long verify_code_id);

    long selectLengthOfVerify_code(long is_register);

    void deleteVerify_codeByVerify_code_id(@Param("verify_code_ids") List<Long> verify_code_ids);
}
