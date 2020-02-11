package com.cktv.mapper;

import com.cktv.domain.Device;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mgh on 2016/6/2.
 */

@Repository(value="deviceMapper")
@Component
public interface DeviceMapper {
    void insertDevice(Device device);

    List<Device> selectDevicesByUser_id(long user_id);

    List<Device> selectDevicesOfUser(@Param("user_id") long user_id,@Param("count") long count,@Param("userDevicePageSize") long userDevicePageSize);

    long selectLengthOfUserDevice(long user_id);

    long lengthOfUserDeviceByScreenName(@Param("user_id") long user_id,@Param("screen_name") String screen_name);

    List<Device> selectIs_registerDevicesOfUser(@Param("user_id") long user_id,@Param("is_register") long is_register,@Param("count") long count,@Param("userIs_registerDevicePageSize") long userIs_registerDevicePageSize);

    long selectLengthOfUserIs_registerDevice(@Param("user_id") long user_id,@Param("is_register") long is_register);

    long lengthOfUserDeviceByRunStatus(@Param("user_id")long user_id,@Param("run_status")long run_status);

    Device selectByPrimaryKey(String device_did);

    List<Device> selectAllDevices();

    void insertDevice();

    void updatestatusByDevice_did(
            @Param("device_dids") List<String> device_dids,
            @Param("status") String status
    );

    long selectStatusByDevice_did(String device_did);

    void updateByPrimaryKey(Device device);

    void updateScreen_nameByDevice_did(@Param("device_did") String device_did, @Param("screen_name") String screen_name);
    void deleteDevice_didByUserId(@Param("device_dids") List<String> device_dids,@Param("user_id")long user_id );

    List<Device> selectDevicesByScreenName(@Param("user_id") long user_id,@Param("screen_name")String screen_name,@Param("count")long count,@Param("size")long size);

    List<Device> selectDevicesByRunStatus(@Param("user_id") long user_id,@Param("run_status") long run_status,@Param("count")long count,@Param("size")long size);

}
