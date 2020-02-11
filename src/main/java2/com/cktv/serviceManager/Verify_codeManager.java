package com.cktv.serviceManager;

import com.cktv.domain.Verify_code;

import java.util.List;
import java.util.Map;

/**
 * Created by mgh on 2016/6/3.
 */
public interface Verify_codeManager {
    //根据激活码查询激活对象
    Verify_code selectByPrimaryKey(String verify_code);
    //更新激活码状态
    void updateVerify_codeIs_register(long verify_code_id, long is_register);
    //添加激活码
    void insertVerify_code(Verify_code verify_code);
    //随机生成number个长为length的激活码
    void generateVerify_codes(long number,long length);

    Map<String,Object> selectVerify_codeByIs_register(long is_register, long count, long pageSize);

    void deleteVerify_codeByVerify_code_id(List<Long> verify_code_ids);
}
