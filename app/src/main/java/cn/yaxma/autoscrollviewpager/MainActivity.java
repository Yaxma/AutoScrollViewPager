package cn.yaxma.autoscrollviewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AutoScrollViewPager autoViewPager;
    private BannerAdapter bannerAdapter;
    private BannerPagerListener pagerListener;
    private LinearLayout indicatorLay;//轮播图指示小圆点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoViewPager = (AutoScrollViewPager) findViewById(R.id.auto_viewpager);
        indicatorLay = (LinearLayout) findViewById(R.id.indicator_lay);

        bannerAdapter = new BannerAdapter(this);
        autoViewPager.setAdapter(bannerAdapter);
        // 以下两个方法不是必须的，因为有默认值
        autoViewPager.setShowTime(3 * 1000); // 设置轮播时间
        autoViewPager.setDirection(AutoScrollViewPager.Direction.LEFT); // 设置轮播方向

        // 设置滑动监听器，作用是改变小圆点背景
        pagerListener = new BannerPagerListener();
        autoViewPager.addOnPageChangeListener(pagerListener);

        // 轮播图点击事件，只返回真正的position，自行根据接口返回数据处理。
        bannerAdapter.setOnItemClickListener(new BannerAdapter.OnItemClickListener() {
            @Override public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        loadBannerData();
    }

    private void loadBannerData() {
        // 假数据测试
        List<String> list = new ArrayList<>();
        list.add("http://pic2.ooopic.com/11/73/27/94bOOOPIC7c_1024.jpg");
        list.add("http://img6.pplive.cn/2012/05/22/16095665041.jpg");
        list.add("http://img.dgtle.com/portal/201305/17/132941q00zl0ar66t10n65.jpg");
        list.add("http://img.zcool.cn/community/01b0ed555b4bef0000009af0b778ab.jpg");
        list.add("http://pic.58pic.com/58pic/13/57/38/52k58PICGqB_1024.jpg");

        // 设置LayoutParams是为了添加外边距.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.rightMargin = 10;

        // 我这里写这行是多余的，但是如果你们多次调用接口不清除就会出现许多小圆点
        // 例如首页是列表，banner只是个header，多次刷新就会出现，或者接口调用写在onResume里面，等等。
        indicatorLay.removeAllViews();

        // for循环添加小圆点。
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.dot_bg_selector);
            imageView.setEnabled(false);
            imageView.setLayoutParams(lp);
            indicatorLay.addView(imageView);
        }
        // 默认选中第一个小圆点
        View view = indicatorLay.getChildAt(0);
        if (view != null)
            view.setEnabled(true);

        pagerListener.setDotChangeData(list, indicatorLay);
        bannerAdapter.addUrlList(list);
        // 开启轮播
        autoViewPager.start();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        // 停止轮播
        autoViewPager.stop();
    }
}
