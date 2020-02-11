package com.cktv.serviceManagerImpl;

import com.cktv.domain.Verify_code;
import com.cktv.mapper.Verify_codeMapper;
import com.cktv.serviceManager.Verify_codeManager;
import com.cktv.util.date.DateUtils;
import com.cktv.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mgh on 2016/6/3.
 */
@Component
public class Verify_codeManagerImpl implements Verify_codeManager {
    @Autowired
    private Verify_codeMapper verify_codeMapper;

    @Override
    public Verify_code selectByPrimaryKey(String verify_code) {
        Verify_code verify_code1 = verify_codeMapper.selectByPrimaryKey(verify_code);
        return verify_code1;
    }

    @Override
    public void updateVerify_codeIs_register(long verify_code_id, long is_register) {
        verify_codeMapper.updateVerify_codeIs_register(verify_code_id,is_register);
    }

    @Override
    public void insertVerify_code(Verify_code verify_code) {
        verify_codeMapper.insertVerify_code(verify_code);
    }

    @Override
    public void generateVerify_codes(long number, long length) {
        String verify_code = "";
        for(long i =0;i<number;i++){
            verify_code = StringUtil.generateString(length);
            Verify_code verify_code1 = new Verify_code();
            verify_code1.setIs_register(2);
            verify_code1.setVerify_code(verify_code);
            verify_codeMapper.insertVerify_code(verify_code1);
        }
    }

    @Override
    public Map<String,Object> selectVerify_codeByIs_register(long is_register, long pageNow, long pageSize) {
        long count = (pageNow -1)*pageSize;
        Map<String,Object> map = new HashMap<>();

        if(is_register == Verify_code.IS_REGISRER){
            List<Verify_code> verify_codes = verify_codeMapper.selectVerify_codeByIs_register(is_register,count,pageSize);
            long lengthOfVerify_code = verify_codeMapper.selectLengthOfVerify_code(is_register);

            if(verify_codes !=null){
                for(int i =0;i<verify_codes.size();i++){
                    verify_codes.get(i).loadVerifyDevice();
                    verify_codes.get(i).loadVerifyDevice().loadDeviceUser();
                }
                map.put("verify_codes",verify_codes);
                map.put("lengthOfVerify_code",lengthOfVerify_code);
                map.put("success",true);
            }else{
                map.put("msg","没有匹配的数据！");
                map.put("success",false);
            }

        }else{
            List<Verify_code> verify_codes = verify_codeMapper.selectVerify_codeByIs_register(is_register,count,pageSize);
            long lengthOfVerify_code = verify_codeMapper.selectLengthOfVerify_code(is_register);

            if(verify_codes !=null){
                map.put("verify_codes",verify_codes);
                map.put("lengthOfVerify_code",lengthOfVerify_code);
                map.put("success",false);
            }else{
                map.put("msg","没有匹配的数据！");
                map.put("success",false);
            }

        }
        return map;
    }

    @Override
    public void deleteVerify_codeByVerify_code_id(List<Long> verify_code_ids) {
        verify_codeMapper.deleteVerify_codeByVerify_code_id(verify_code_ids);
    }
}
