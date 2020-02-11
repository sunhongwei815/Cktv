//package com.trt.util;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//public class HibernateUtil {
//	private static final SessionFactory sessionFactory;
//	//使用线程局部模式
//	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
//	static{
//		@SuppressWarnings("resource")
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//		sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
//	}
//
//	//获取全新的session
//	public static Session openSession(){
//		return sessionFactory.openSession();
//	}
//
//	public static Session getCurrentSesison(){
//		Session session = threadLocal.get();
//		//判断当前线程是否已经得到session
//		if(session == null){
////			session = sessionFactory.openSession();
//			session = HibernateUtil.openSession();
//			//把session对象设置到threadLocal,相当于session已经和线程绑定
//			threadLocal.set(session);
//		}
//		return session;
//	}
//
//}
