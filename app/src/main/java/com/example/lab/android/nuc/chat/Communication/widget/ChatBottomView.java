package com.example.lab.android.nuc.chat.Communication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.lab.android.nuc.chat.R;


public class ChatBottomView extends LinearLayout{
	private View baseView;
	private LinearLayout locationGroup;
	private LinearLayout imageGroup;
	private LinearLayout cameraGroup;
	private LinearLayout videoGroup;
	private LinearLayout voiceGroup;
	private HeadIconSelectorView.OnHeadIconClickListener onHeadIconClickListener;
	public static final int FROM_CAMERA = 1;
	public static final int FROM_GALLERY = 2;
	public static final int FROM_LOCATION = 3;
	public static final int FROM_VIDEO = 4;
	public static final int FROM_VOICE = 5;
	public ChatBottomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		findView();
		init();
	}
	
	private void findView(){
		baseView = LayoutInflater.from(getContext()).inflate( R.layout.layout_tongbaobottom, this);
		imageGroup = (LinearLayout) baseView.findViewById(R.id.image_bottom_group);
		cameraGroup = (LinearLayout) baseView.findViewById(R.id.camera_group);
		locationGroup = (LinearLayout) baseView.findViewById( R.id.location_bottom_group );
		videoGroup = (LinearLayout) baseView.findViewById( R.id.video_bottom_group );
		voiceGroup = (LinearLayout) baseView.findViewById( R.id.voice_bottom_group );
	}
	private void init(){
		cameraGroup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != onHeadIconClickListener) {
					onHeadIconClickListener.onClick(FROM_CAMERA);
				}
			}
		});
		imageGroup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (null != onHeadIconClickListener) {
					onHeadIconClickListener.onClick(FROM_GALLERY);
				}
			}
		});
		locationGroup.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != onHeadIconClickListener){
					onHeadIconClickListener.onClick( FROM_LOCATION );
				}
			}
		} );
		videoGroup.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != onHeadIconClickListener){
					onHeadIconClickListener.onClick( FROM_VIDEO );
				}
			}
		} );
		voiceGroup.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != onHeadIconClickListener){
					onHeadIconClickListener.onClick( FROM_VOICE );
				}
			}
		} );
	}

	public void setOnHeadIconClickListener(
			HeadIconSelectorView.OnHeadIconClickListener onHeadIconClickListener) {
		// TODO Auto-generated method stub
		this.onHeadIconClickListener = onHeadIconClickListener;
	}
}
