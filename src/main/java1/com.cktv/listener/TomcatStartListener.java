package com.cktv.listener;

import com.cktv.server.DeviceServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by 每名子 on 2016/7/20.
 */
public class TomcatStartListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        new DeviceServer().start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
