package com.example.datastructure.view;

import java.util.HashMap;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.example.datastructure.R;


/**
 * 
 * ============================================================
 * 
 * project name : TiantianFangFu
 * 
 * copyright ZENG HUI (c) 2015
 * 
 * author : HUI
 * 
 * QQ ��240336124
 * 
 * version : 1.0
 * 
 * date created : On July, 2015
 * 
 * description : 雪花飞舞
 * 
 * revision history :
 * 
 * ============================================================
 *
 */
public class SingleSnowView extends View {
	// �����������е�����Ƭ״����,������Ǳ�ڵ����԰ټƵĶ����Ķ���,����ֻʹ��һ��,Ȼ���������Ƭÿһ֡�Ķ�����
	private ValueAnimator mAnimator = ValueAnimator.ofFloat(0, 1);
	// ����׷��ʱ��Ϊ������ Frames per second
	private long mPrevTime;
	private Snow mSnow;
	private Matrix mMatrix; // ���������ƶ�ÿƬ����
	private Bitmap mSnowBitmap;

	public SingleSnowView(Context context) {
		this(context, null);
	}

	public SingleSnowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SingleSnowView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initData();
	}

	private void initData() {
		mMatrix = new Matrix();

		mSnowBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_1);

		mAnimator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator arg0) {
				// ÿһ֡�Ķ���,���Ǽ�������ʱ��͸���ÿһƬ��λ�ú���ת�����ٶȡ�
				long nowTime = System.currentTimeMillis();
				float secs = (nowTime - mPrevTime) / 1000f;
				mPrevTime = nowTime;
				// �µ�λ��
				mSnow.y += mSnow.speed * secs;

				if (mSnow.y > getHeight()) {
					// ����Ѿ�����ײ����������������һ��,����snow y ��λ��
					mSnow.y = 0 - mSnow.height;
				}

				// ѩ����ת
				mSnow.rotation = mSnow.rotation + (mSnow.rotationSpeed * secs);

				// ��������ȥˢ�»��ƽ���
				invalidate();
			}
		});
		mAnimator.setRepeatCount(ValueAnimator.INFINITE);
		mAnimator.setDuration(2000);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// ���þ���ȥʵ��
		mMatrix.setTranslate(-mSnow.width / 2, -mSnow.height / 2);
		mMatrix.postRotate(mSnow.rotation);
		mMatrix.postTranslate(mSnow.width / 2 + mSnow.x, mSnow.height
				/ 2 + mSnow.y);
		canvas.drawBitmap(mSnow.bitmap, mMatrix, null);
	}

	/**
	 * ��ʼ��ѩ
	 */
	public void startSnow(int rangeWidth) {
		if (mSnow == null) {
			mPrevTime = System.currentTimeMillis();
			mSnow = Snow.createSnow(mSnowBitmap, rangeWidth);
		}
		mAnimator.start();
	}

	/**
	 * ֹͣ��ѩ
	 */
	public void stopSnow() {
		mAnimator.cancel();
	}

	public static class Snow {
		// ��ת�ǶȺ�����
		public float rotation;
		public float rotationSpeed;
		// ��view�е�x,y���λ��
		public float x, y;
		// ������ٶ�
		public float speed;
		// �߶ȺͿ��
		public int width, height;
		// bitmap
		public Bitmap bitmap;

		// ���ݿ�ȴ��bitmap���Ա�ͬ���Ŀ�ȿ����ؼ����л�ȡ
		private static HashMap<Integer, Bitmap> bitmapMap = new HashMap<Integer, Bitmap>();

		public static Snow createSnow(Bitmap snowBitmap, int xRange) {
			Snow snow = new Snow();
			// �����8~58֮��
			snow.width = (int) (8 + (float) Math.random() * 50);
			// ���ݴ�������bitmap����������
			float hwRatio = snowBitmap.getHeight() / snowBitmap.getWidth();
			// ����߶�
			snow.height = (int) (snow.width * hwRatio);

			// xλ����[snow.width,����View�п��] ����λ��
			snow.x = (float) Math.random() * (xRange - snow.width);

			// ��λѩ����ֱ��΢ƫ��Ķ�����ʾ
			snow.y = 0 - (snow.height + (float) Math.random() * snow.height);

			// ÿ��������ٶ��� 50~300֮��
			snow.speed = 50 + (float) Math.random() * 250;

			// Ƭ��ʼ��-90 - 90����ת,��ת�ٶȵ�-45 - 45֮��
			snow.rotation = (float) Math.random() * 180 - 90;
			snow.rotationSpeed = (float) Math.random() * 90 - 45;

			// �ȸ��ݿ�ȴӻ����л�ȡ
			snow.bitmap = bitmapMap.get(snow.width);
			if (snow.bitmap == null) {
				// ���������û�У�����,���浽����
				snow.bitmap = Bitmap.createScaledBitmap(snowBitmap, snow.width,
						snow.height, true);
				bitmapMap.put(snow.width, snow.bitmap);
			}
			return snow;
		}

		@Override
		public String toString() {
			return "width:" + width + "  " + "height:" + height + "  "
					+ "speed:" + speed + "  " + "x:" + x + "  " + "y:" + y;
		}
	}
}
