package org.yishang.util;

import com.alibaba.fastjson.JSON;
import com.intellij.openapi.application.ApplicationManager;
import org.yishang.StatisticsData;
import org.yishang.persistent.UITimesState;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SingletonUtil {

	// 读写锁
	private final static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final static Lock readLock = readWriteLock.readLock();
	private final static Lock writeLock = readWriteLock.writeLock();
	// 私有静态变量保存唯一实例
	private static volatile StatisticsData statisticsData;

	// 提供全局访问点
	public static StatisticsData getInstance() {
		readLock.lock();
		try {
			if (statisticsData == null) { // 第一次检查
				synchronized (StatisticsData.class) {
					if (statisticsData == null) { // 第二次检查
						UITimesState uiTimesState = ApplicationManager.getApplication().getService(UITimesState.class);
						statisticsData = JSON.parseObject(uiTimesState.getStatisticsData(), StatisticsData.class);
						if (statisticsData == null) {
							statisticsData = new StatisticsData();
						}
						// 启动线程，定时保存数据
						new Thread(() -> {
							while (true) {
								try {
									Thread.sleep(2000);
								} catch (Exception e) {
									e.printStackTrace();
								}
								//System.out.println("缓存的数据详情："+JSON.toJSONString(statisticsData));
								uiTimesState.setStatisticsData(JSON.toJSONString(statisticsData));
							}
						}).start();
					}
				}
			}
			return statisticsData;
		} finally {
			readLock.unlock();
		}
	}

	public static StatisticsData getClearStatisticsData() {
		writeLock.tryLock();
		try {
			StatisticsData instance = SingletonUtil.getInstance();
			instance.setRunTime(new AtomicLong(0));
			instance.setActiveTime(new AtomicLong(0));
			instance.setKeyCount(new AtomicLong(0));
			instance.setAddLineCount(new AtomicLong(0));
			instance.setRemoveLineCount(new AtomicLong(0));
			instance.setCopyCount(new AtomicLong(0));
			instance.setPasteCount(new AtomicLong(0));
			instance.setPushCount(new AtomicLong(0));
			instance.setCreateDate(DateUtil.getCurDate());
			return instance;
		} finally {
			writeLock.unlock();
		}
	}

//	// 线程安全的读取方法
//	public static int getData() {
//		readLock.lock();
//		try {
//			return data;
//		} finally {
//			readLock.unlock();
//		}
//	}
//
//	// 线程安全的写入方法
//	public void setData(int data) {
//		writeLock.lock();
//		try {
//			this.data = data;
//		} finally {
//			writeLock.unlock();
//		}
//	}

}
