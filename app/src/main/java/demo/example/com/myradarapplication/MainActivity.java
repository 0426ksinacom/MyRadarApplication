package demo.example.com.myradarapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView btn;
    private ImageView imageView1;
    private RadarView radarView;

    private int addviewCountTag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//注释
        initView();
        initData();

        newBranch00();
    }
    private void newBranch00() {
    }

    /**
     * Radar
     */
    public void RadarStop() {
        if (radarView != null) {
            radarView.stop();
        }
    }

    /**
     * Radar
     */
    public void RadarStart() {
        if (radarView != null) {
            radarView.start();
        }
    }

    private void initData() {
    }

    private void initView() {
        btn = (ImageView) findViewById(R.id.button);
        radarView = (RadarView) findViewById(R.id.seismicwaveview);
        radarView.start();
        radarView.setViewWidth(140);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                radarView.removeOneView();
            }
        });
        imageView1.postDelayed(new Runnable() {

            @Override
            public void run() {

                final int[] imagLocation = new int[2];
                final int[] seiLocation = new int[2];

                // imageView1.getLocationOnScreen(location);
                imageView1.getLocationInWindow(imagLocation);
                radarView.getLocationInWindow(seiLocation);
                // drawCircle 相对自己的坐标（本身左上角为（0，0）） 现在都转到 radarView 内的坐标 x0
                // 没变 不用转
                radarView.setcircleCenter(imagLocation[0] + imageView1.getWidth() / 2, imagLocation[1] - seiLocation[1] + imageView1.getHeight() / 2);
                // radarView.setcircleCenter(location[0], location[1]);

            }
        }, 400);

        // 控制地震波的按钮
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                RadarViewitem button = new RadarViewitem(MainActivity.this);
                button.getWidth();
                addviewCountTag++;
                button.setTag(addviewCountTag);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (v.getTag() != null) {
                            int pos = (Integer) (v.getTag());
                            Toast.makeText(MainActivity.this, "position=" + pos, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                radarView.addViewlist(button);
                // ImageLoader.getInstance().displayImage("http://s17.mogucdn.com/p1/150711/14dhcd_ie2wimjumm3domrvgizdambqhayde_640x960.jpg_468x468.jpg",
                // iv, options);
                button.setSpimg("http://v1.qzone.cc/skin/201512/21/13/00/5677878225e8d943.jpg%21600x600.jpg");
                button.setName("@我 " + addviewCountTag);

                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.buttongobig);
                button.startAnimation(animation);
                //

                // if (seismicWaveView.isStarting()) {
                // seismicWaveView.stop();
                // } else {
                // seismicWaveView.start();
                // }
            }
        });
    }
}
