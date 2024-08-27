package com.ilot.utils;

public class UserSessionDatas {
	private final static ThreadLocal<Integer> threadLocalUserSessionDatas = new ThreadLocal<Integer>();

	public static Integer get() {
		return Utilities.isValidID(threadLocalUserSessionDatas.get()) 
				? threadLocalUserSessionDatas.get() 
						:null;
	}

	public static void set(Integer datas) {
		threadLocalUserSessionDatas.set(datas);
	}

	public static void clear() {
		threadLocalUserSessionDatas.remove();
	}
}
