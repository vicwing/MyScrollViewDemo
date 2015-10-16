package com.heima.scrollview;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyScrollView extends ViewGroup{

//public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		// TODO Auto-generated constructor stub
//	}

//public MyScrollView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		// TODO Auto-generated constructor stub
//	}
	
	private static final String TAG = MyScrollView.class.getSimpleName();
	private TextView tv;
	private Context ctx;
	private RelativeLayout rl;
	private MyScroller myScroller;
//	private Scroller myScroller;
	
	
	public MyScrollView(Context context) {
		super(context);
		this.ctx = context;
		initview();
	}

	private GestureDetector detector;
	
	private void initview() {
		
		myScroller = new MyScroller(ctx);
//		myScroller = new Scroller(ctx);

		detector = new GestureDetector(ctx, new GestureDetector.OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				Log.d("1","onSingleTapUp...............");
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				Log.d("1","onShowPress...............");
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				System.out.println("onscroll.....distanceX::"+distanceX);
				scrollBy((int)distanceX, 0);
				return true;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				Log.d("1","onLongPress...............");
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				Log.d("1","onFling...............");
				isFling = true;
				if(velocityX>0 && curId>0){
					moveToDest(curId-1);
				}else if(velocityX<0 && curId<getChildCount()-1){
					moveToDest(curId+1);
				}else{
					moveToDest();
				}
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				Log.d("1","onDown...............");
				return false;
			}
		});

		detector.setOnDoubleTapListener(new DoubleTapListener());
	}
	
	/**
	 * �Ƿ���ٻ���״̬
	 */
	private boolean isFling = false;
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.d(TAG, "myscrollview............onMeasure");
		//ȡ�ø�viewָ�ɵĴ�С��ʵ�ʴ�С��
		MeasureSpec.getSize(widthMeasureSpec);
		//ȡ�ø�viewָ�ɵĴ�С(mode�ǲ�ȷ���Ĵ�С ������wrap)
		MeasureSpec.getMode(widthMeasureSpec);
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.measure(widthMeasureSpec, heightMeasureSpec);
		}
	}
	/**
	 * 
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.d(TAG, "myscrollview............onLayout");
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			//�����������ſ�
			view.layout(i*getWidth(), 0, (1+i)*getWidth(), getHeight());
		}
	}
	private LinearLayout ll;
	private int lastX;
	private int lastY;
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.dispatchTouchEvent(ev);
	}
	/**
	 * �ж��¼�
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		boolean result  = false;
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int) ev.getX();
			lastY = (int) ev.getY();
			//���down�¼�
//			detector.onTouchEvent(ev);
			break;
			
		case MotionEvent.ACTION_MOVE:
			
			int distanceX = (int) Math.abs(ev.getX()-lastX);
			int distanceY = (int) Math.abs(ev.getY()-lastY);
			
			if(distanceX > distanceY && distanceX>10){
				//ˮƽ�ƶ�
				result = true;
			}
			
			break;

		default:
			break;
		}
		return result;
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		detector.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			if(!isFling){
				moveToDest();
			}
			isFling = false;
			break;

		default:
			break;
		}
		
		return true;
	}
	
	/**
	 * �ƶ����ʵ���λ����
	 */
	private void moveToDest() {
		int destId = (getScrollX()+getWidth()/2)/getWidth();
		
		if(destId>getChildCount()-1){
			destId=getChildCount()-1;
		}
		
		moveToDest(destId);
		
	}

	private int curId;
	
	/**
	 * ��ָ���±��ͼƬ�ƶ�����Ļ
	 * @param destId
	 */
	public void moveToDest(int destId) {
		int distance = destId*getWidth()-getScrollX();
		curId = destId;
		
		if(scrollListener!=null){
			scrollListener.moveToDest(destId);
		}
		
//		scrollBy(distance, 0);
		myScroller.startScroll(getScrollX(), getScrollY(), distance, 0);
//		myScroller.startScroll(getScrollX(), getScrollY(), distance, 0,Math.abs(distance));
		invalidate();
	}
	/**
	 *invalidateˢ�»���� computeScroll,��ȡƫ����,�ݹ����ֱ��ֹͣ
	 */
	@Override
	public void computeScroll() {
		if(myScroller.computeScrollOffset()){
			int x = myScroller.getCurrX();
			scrollTo(x, 0);
			invalidate();
		}
	}
		
	public IScrollListener getScrollListener() {
		return scrollListener;
	}

	public void setScrollListener(IScrollListener scrollListener) {
		this.scrollListener = scrollListener;
	}

	private IScrollListener scrollListener;

	public interface IScrollListener {
		void moveToDest(int destId);
	}
//	@Override
//	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
//		Log.d(TAG, "scrollview......ondraw..");


	 class DoubleTapListener implements GestureDetector.OnDoubleTapListener{

		public boolean onSingleTapConfirmed(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.d("2","onSingleTapConfirmed..................");
			return false;
		}

		public boolean onDoubleTap(MotionEvent e) {
			Log.d("2","onDoubleTap..................");
			return false;
		}

		public boolean onDoubleTapEvent(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.d("2","onDoubleTapEvent..................");
			return false;
		}
	}
}
