//package com.yonyou.cloud.mom.demo;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
//import com.yonyou.cloud.mom.demo.msg.entity.BizEntity;
//
//public class MethodFC {
// public static void main(String[] args) throws InstantiationException {
//	Class cs=SunClass.class;
////	Method[] ms=cs.getMethods();
//	Method[] ms=cs.getDeclaredMethods();
//	for(Method md:ms) {
//		System.out.println(md.getName());
//		try {
//			if("cxff".equals(md.getName())) {
//				md.invoke(cs.newInstance(), new String("我的名字"));
//			}
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//	}
//}
//}
