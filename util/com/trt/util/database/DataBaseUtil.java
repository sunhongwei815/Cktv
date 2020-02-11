package com.trt.util.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseUtil {

	
	public static void closePs(PreparedStatement ps){
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭ps出错:" + e.getMessage(), e);
			}
		}
	}
	
	public static void closeResultSet(ResultSet resultSet){
		if(resultSet!=null){
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭ps出错:" + e.getMessage(), e);
			}
		}
	}
}
