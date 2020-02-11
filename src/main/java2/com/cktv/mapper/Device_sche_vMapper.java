package com.cktv.mapper;

import com.cktv.domain.Device_sche_v;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by hws on 2016/5/27.
 */
@Component
public interface Device_sche_vMapper {
    void insertDevice_sche_v(Device_sche_v deviceSchev);

    Device_sche_v selectByPrimaryKey(long device_sche_v_id);

    void updateByPrimaryKey(Device_sche_v deviceSchev);

    List<Device_sche_v> selectAll ();

    void updateSche_vByDevice_dids(@Param("update_time") Date update_time,@Param("array") String... device_dids);

    long selectSche_vByDevice_did(String device_did);

    void deleteDevice_sche_vsByDevice_dids(@Param("device_dids") List<String> device_dids);
}
