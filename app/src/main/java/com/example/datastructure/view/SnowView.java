package com.example.datastructure.view;

import java.util.ArrayList;
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


public class SnowView extends View {
	// �����������е�����Ƭ״����,������Ǳ�ڵ����԰ټƵĶ����Ļ�ʦ,����ֻʹ��һ��,Ȼ���������Ƭÿһ֡�Ķ�����
	private ValueAnimator mAnimator = ValueAnimator.ofFloat(0, 1);
	// �ϸ�ʱ��
	private long mPrevTime;
	private Matrix mMatrix; // ���������ƶ�ÿƬ����
	private Bitmap mSnowBitmapOne, mSnowBitmapTwo, mSnowBitmapThree,
			mSnowBitmapFour, mSnowBitmapFive;
	private ArrayList<Bitmap> mSnowBitmaps;
	// ѩ���ĸ���
	private int mSnowNumber = 0;
	// �����ʾѩ���ĸ��� ��75��
	private static final int MAX_SNOW_NUMBER = 75;

	private ArrayList<Snow> mSnows;// ѩ������

	public SnowView(Context context) {
		this(context, null);
	}

	public SnowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SnowView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initData();
	}

	private void initData() {
		mMatrix = new Matrix();
		mSnows = new ArrayList<Snow>();

		mSnowBitmapOne = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_1);
		mSnowBitmapTwo = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_2);
		mSnowBitmapThree = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_3);
		mSnowBitmapFour = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_4);
		mSnowBitmapFive = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_5);

		mSnowBitmaps = new ArrayList<Bitmap>();
		mSnowBitmaps.add(mSnowBitmapOne);
		mSnowBitmaps.add(mSnowBitmapTwo);
		mSnowBitmaps.add(mSnowBitmapThree);
		mSnowBitmaps.add(mSnowBitmapFour);
		mSnowBitmaps.add(mSnowBitmapFive);

		mAnimator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator arg0) {
				// ÿһ֡�Ķ���,���Ǽ�������ʱ��͸���ÿһƬ��λ�ú���ת�����ٶȡ�
				long nowTime = System.currentTimeMillis();
				float secs = (nowTime - mPrevTime) / 1000f;
				mPrevTime = nowTime;

				for (Snow snow : mSnows) {
					// �µ�λ��
					snow.y += snow.speed * secs;

					if (snow.y > getHeight()) {
						// ����Ѿ�����ײ����������������һ��,����snow y ��λ��
						snow.y = 0 - snow.height;
					}

					// ѩ����ת
					snow.rotation = snow.rotation + (snow.rotationSpeed * secs);
				}
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
		for (Snow snow : mSnows) {
			// ���þ���ȥʵ��
			mMatrix.setTranslate(-snow.width / 2, -snow.height / 2);
			mMatrix.postRotate(snow.rotation);
			mMatrix.postTranslate(snow.width / 2 + snow.x, snow.height
					/ 2 + snow.y);
			canvas.drawBitmap(snow.bitmap, mMatrix, null);
		}
		// TODO �������֡����
	}

	/**
	 * ��ʼ��ѩ
	 */
	public void startSnow(int rangeWidth) {
		mPrevTime = System.currentTimeMillis();
		// û�����ֱ�ӿ�ʼ��ѩʱ��Ĭ�����15��
		addSnows(15, rangeWidth);
		mAnimator.start();
	}

	public int getSnowNumber() {
		return this.mSnowNumber >= MAX_SNOW_NUMBER ? MAX_SNOW_NUMBER
				: mSnowNumber;
	}

	/**
	 * ���ѩ��
	 */
	public void addSnows(int snowNumber, int rangeWidth) {
		if (mSnowNumber < MAX_SNOW_NUMBER) {
			for (int i = 0; i < snowNumber; i++) {
				// �����ѡ (Ҳ���Ը���λ����%,�ȱ���)
				int index = (int) (Math.random() * 5);
				Bitmap snowBitmap = mSnowBitmaps.get(index);
				Snow snow = Snow.createSnow(snowBitmap, rangeWidth);
				mSnows.add(snow);
			}
			// ��ǰѩ����������
			mSnowNumber += snowNumber;
		}
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
			// ���ݱ�������߶�
			snow.height = (int) (snow.width * hwRatio);

			// xλ����[snow.width,�����ݵĿ��] ����λ��
			snow.x = (float) Math.random() * (xRange - snow.width);

			// ��λѩ����ֱ��΢ƫ�붥����ʾ
			snow.y = 0 - (snow.height + (float) Math.random() * snow.height);

			// ÿ��������ٶ��� 50~300֮�� + snow.width
			snow.speed = 50 + (float) Math.random() * 250 + snow.width;

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
					+ "speed:" + speed + "  " + "x:" + x + "  " + "y:" + y
					+ "rotation:" + rotation + "rotationSpeed:" + rotationSpeed;
		}
	}
}
