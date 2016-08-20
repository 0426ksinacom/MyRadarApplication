package demo.example.com.myradarapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

//加载网络头像

public class RadarViewitem extends LinearLayout {

	DisplayImageOptions options;

		public RadarViewitem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public RadarViewitem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public RadarViewitem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private TextView tv;
	private ImageView iv;
//	DisplayImageOptions options;

	private void init(Context context) {

		LayoutInflater.from(context).inflate(R.layout.radar_item, this);
		tv = (TextView) findViewById(R.id.tv);
		iv = (ImageView) findViewById(R.id.iv);

		options = new DisplayImageOptions.Builder()
		// // 设置图片在下载期间显示的图片
		// .showImageOnLoading(icon)
		// // 设置图片Uri为空或是错误的时候显示的图片
		// .showImageForEmptyUri(icon)
		// // 设置图片加载/解码过程中错误时候显示的图片
		// .showImageOnFail(icon)
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				// 列表中用IN_SAMPLE_POWER_OF_2
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.ARGB_8888)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				.considerExifParams(true)
				// 设置图片下载前的延迟
				.delayBeforeLoading(300)// int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				// .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				.displayer(new CircleBitmapDisplayer(Color.WHITE, 5))// 是否设置为圆角，弧度为多少
				// .displayer(new FadeInBitmapDisplayer(500))// 淡入
				.build();
		// setSpimg("ww");
		// setName("sss");
	}
//加载图片
	public void setSpimg(String urlpath) {
		if (iv != null) {
			ImageLoader.getInstance().displayImage(urlpath, iv, options);
		}

	}

	public void setName(String name) {

		if (tv != null) {
			tv.setText(name);
		}

	}

}
