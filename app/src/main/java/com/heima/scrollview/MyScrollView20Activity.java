package com.heima.scrollview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.heima.scrollview.MyScrollView.IScrollListener;
import com.heima.scrollview20.R;

public class MyScrollView20Activity extends Activity {
    /** Called when the activity is first created. */
	
	private int [] ids = new int[]{R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6};
	
	private MyScrollView myScrollView;
	
	private RadioGroup radioGroup;
	
	private LinearLayout body_layout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        myScrollView = new MyScrollView(this);
        
        for (int i = 0; i < ids.length; i++) {
        	ImageView image = new ImageView(this);
			image.setBackgroundResource(ids[i]);
			myScrollView.addView(image);
		}
        
//        setContentView(msv);
        
        View testView = getLayoutInflater().inflate(R.layout.test, null);
        myScrollView.addView(testView, 2);
        
        body_layout = (LinearLayout) findViewById(R.id.body_layout);
        body_layout.addView(myScrollView);
        
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        for (int i = 0; i < myScrollView.getChildCount(); i++) {
			RadioButton rb =  new RadioButton(this);
			radioGroup.addView(rb);
			rb.setId(i);
			if(i==0){
				rb.setChecked(true);
			}
		}
        
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				myScrollView.moveToDest(checkedId);
			}
		});
        
        
        myScrollView.setScrollListener(new IScrollListener() {
			@Override
			public void moveToDest(int destId) {
				((RadioButton)radioGroup.getChildAt(destId)).setChecked(true);
			}
		});
        final Button button1 = (Button) findViewById(R.id.button1);
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				button1.setVisibility(View.GONE);
			}
		});
    }
}