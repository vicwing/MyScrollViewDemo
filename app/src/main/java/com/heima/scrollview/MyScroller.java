package com.heima.scrollview;

import android.content.Context;
import android.os.SystemClock;


/**
 * ����������
 * @author Administrator
 *
 */
public class MyScroller {

	private Context ctx;
	public MyScroller(Context context){
		ctx = context;
	}
	
	private int startX;
	private int startY;
	private int distanceX;
	private int distanceY;
	
	private int currentX;
	private int currentY;
	
	private long startTime;
	private long duration = 1000l;
	/**
	 * 
	 * @param scrollX
	 * @param scrollY
	 * @param distanceX
	 * @param distanceY
	 */
	public void startScroll(int scrollX, int scrollY, int distanceX, int distanceY) {
		startX = scrollX;
		startY = scrollY;
		this.distanceX = distanceX;
		this.distanceY = distanceY;
		isFinish = false;
		startTime = SystemClock.uptimeMillis();
	}
	public void startScroll(int scrollX, int scrollY, int distanceX, int distanceY,int duration) {
		startX = scrollX;
		startY = scrollY;
		this.distanceX = distanceX;
		this.distanceY = distanceY;
		isFinish = false;
		startTime = SystemClock.uptimeMillis();
		
		this.duration = duration;
	}
	
	
	private boolean isFinish ;
	
	/**
	 * ����ƫ������
	 * @return true �����ƶ�  false���ƶ��Ѿ�ֹͣ
	 */
	public boolean computeScrollOffset() {

		if (isFinish) {
			return false;
		}

		long timePassed = SystemClock.uptimeMillis()- startTime;

		if (timePassed < duration) {

			currentX = (int) (startX + distanceX  * timePassed / duration);
			currentY = (int) (startY + distanceY * timePassed/ duration );

//			System.out.println("currentX:::" + currentX);
		} else if (timePassed >= duration) {
			currentX = startX + distanceX;
			currentY = startY + distanceY;
			isFinish = true;
		}
		
		return true;
	}

	public int getCurrX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public int getCurrentY() {
		return currentY;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}

	

}
