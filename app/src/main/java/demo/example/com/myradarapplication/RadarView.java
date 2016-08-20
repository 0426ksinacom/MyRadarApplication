package demo.example.com.myradarapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//myLinealayout button = new myLinealayout(getApplicationContext());
//button.getWidth();
//
//ImageView iv = new ImageView(getApplicationContext());
//button.setEnabled(true);
//button.setClickable(true);
//
//button.setOnClickListener(new OnClickListener() {
//	public void onClick(View v) {
//		if (v.getTag() != null) {
//			int pos = (Integer) (v.getTag());
//			Log.i("aaa", "aa = " + pos);
//		}
//
//	}
//});
//button.setTag(addviewCountTag);
//addviewCountTag++;
//
//seismicWaveView.addViewlist(button);
//
//// ImageLoader.getInstance().displayImage("http://s17.mogucdn.com/p1/150711/14dhcd_ie2wimjumm3domrvgizdambqhayde_640x960.jpg_468x468.jpg",
//// iv, options);
//button.setSpimg("http://p2.so.qhimg.com/t012e37d392e092ed82.jpg");
//button.setName("asrfwo我 ");
//
//Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buttongobig);
//button.startAnimation(animation);

/**
 * 雷达波
 * 
 * 设置波宽setPaintStrokeWidth
 * 
 * 设置浮现View的宽度 （默认210）setViewWidth
 * 
 * 
 * @author Administrator
 * 
 */
//
public class RadarView extends FrameLayout {

	private Paint paint;
	private boolean isStarting = false;
	private List<String> alphaList = new ArrayList<String>();
	private List<String> startWidthList = new ArrayList<String>();
	/** 记录每个头像坐标 */
	private List<Coordinate> coordinates = new ArrayList<Coordinate>();
	/** 预置头像像坐标 */
	private List<Point> points = new ArrayList<Point>();
	/** 已经使用的预置头像像坐标 与points互斥 */
	private List<Point> hasPoints = new ArrayList<Point>();

	private int viewWidth = 210;
	private int width;
	private int height;

	public RadarView(Context context) {
		super(context);
		init();
	}

	public RadarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RadarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setColor(Color.parseColor("#d9d2e9"));// 此处颜色可以改为自己喜欢的
		alphaList.add("255");// 圆心的不透明度
		startWidthList.add("0");
		paint.setStyle(Style.STROKE);
		// paint.setStyle(Style.FILL);
		paint.setStrokeWidth(5);
		paint.setAntiAlias(true);
		setBackgroundColor(Color.TRANSPARENT);// 颜色：完全透明

	}

	public void setPaintStrokeWidth(float w) {

		paint.setStrokeWidth(w);
	}

	public void setViewWidth(int w) {

		viewWidth = w;
	}

	float xc = -1F, yc = -1F;

	public void setcircleCenter(float xc, float yc) {

		this.xc = xc;
		this.yc = yc;
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 依次绘制 同心圆
		for (int i = 0; i < alphaList.size(); i++) {

			if (xc > 0 && yc > 0) {

				int startWidth = Integer.parseInt(startWidthList.get(i));
				paint.setAlpha(220);

				// 坐标相对自己
				canvas.drawCircle(xc, yc, startWidth, paint);
				// 同心圆扩散
				// if (isStarting && alpha > 0 && startWidth < getHeight()) {
				// alphaList.set(i, (alpha - 1) + "");
				// startWidthList.set(i, (startWidth + 3) + "");
				// }
				if (isStarting && startWidth < getHeight()) {
					startWidthList.set(i, (startWidth + 2) + "");
				}

			}
		}
		if (isStarting && Integer.parseInt(startWidthList.get(startWidthList.size() - 1)) >= getWidth() / 6) {
			alphaList.add("255");
			startWidthList.add("0");
		}
		// 同心圆数量达到6个，删除最外层圆
		if (isStarting && startWidthList.size() >= 300) {
			startWidthList.remove(0);
			alphaList.remove(0);
		}
		// 刷新界面
		invalidate();
	}

	// 地震波开始/继续进行
	public void start() {
		isStarting = true;
	}

	// 地震波暂停
	public void stop() {
		isStarting = false;
	}

	public boolean isStarting() {
		return isStarting;
	}

	public boolean isContain(Point mp1, Point mp2, Point mp3, Point mp4, Point mp) {
		if (Multiply(mp, mp1, mp2) * Multiply(mp, mp4, mp3) <= 0 && Multiply(mp, mp4, mp1) * Multiply(mp, mp3, mp2) <= 0)
			return true;
		return false;
	}

	// 计算叉乘 |P0P1| × |P0P2|
	private double Multiply(Point p1, Point p2, Point p0) {
		return ((p1.X - p0.X) * (p2.Y - p0.Y) - (p2.X - p0.X) * (p1.Y - p0.Y));
	}

	public void startCountPonts() {

		width = getWidth();
		height = getHeight();
		int Nx = width / viewWidth;
		int Ny = height / viewWidth;

		for (int i = 1; i < Nx; i++) {

			for (int j = 2; j < Ny; j++) {

				Point point = new Point();// 选择的中心点

				point.X = (int) (viewWidth * (i + 0.2));
				point.Y = (int) (viewWidth * (j + 0.3));
				points.add(point);

			}

		}

	}

	public void removeOneView() {

		if (hasPoints.size() > 1) {
			int randm = new Random().nextInt(hasPoints.size() - 1);

			points.add(hasPoints.get(randm));

			hasPoints.remove(randm);
			coordinates.remove(randm);

			removeViewAt(randm);
		}

	}

	//
	public void addViewlist(View v) {
		Coordinate coordinate = new Coordinate();
		coordinate.rand = getChildCount();

		if (width == 0) {

			startCountPonts();

		}
		if (points.size() <= 1) {
			removeOneView();
		}

		int randm = new Random().nextInt(points.size() - 1);

		Point point2 = points.get(randm);

		int rand = new Random().nextInt(viewWidth / 2);// 产生 错乱感
		int rand1 = new Random().nextInt(viewWidth / 2);// 产生 错乱感

		rand1 -= viewWidth / 2;

		coordinate.l = point2.X - viewWidth / 2 + rand + rand1;
		coordinate.t = point2.Y - viewWidth / 2 + rand + rand1;
		coordinate.r = point2.X + viewWidth / 2 + rand + rand1;
		coordinate.b = point2.Y + viewWidth / 2 + rand + rand1;

		coordinates.add(coordinate);

		hasPoints.add(point2);
		points.remove(randm);

		addView(v, new ViewGroup.LayoutParams(viewWidth, viewWidth + viewWidth ));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		Log.i("app","onLayout");

		int count = getChildCount();

		for (int i = 0; i < count; i++) {
			View v = getChildAt(i);

			if (v != null) {

				Coordinate coordinate = coordinates.get(i);

				v.layout(coordinate.l, coordinate.t, coordinate.r, coordinate.b+viewWidth );
			}

		}

	}

}
