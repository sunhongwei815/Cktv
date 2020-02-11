package com.cktv.mapper;

import com.cktv.domain.Tpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hws on 2016/5/27.
 */
@Component
public interface TplMapper {
    void insertTpl(Tpl tpl);

    Tpl selectByPrimaryKey(long tpl_id);

    void updateByPrimaryKey(Tpl tpl);

    List<Tpl> selectAll ();

    void deleteTplByTpl_id(int tpl_id);
    //查询所有的模板模式tpl_model
    List<Tpl> selectTplsByTpl_model(int tp1_model);

    Tpl selectByTplName(String tp_name);

    List<Tpl>selectTplsByPages(@Param("startNum")long startNum,@Param("size")long size);

    Long lengthOfAllTpl();
}
