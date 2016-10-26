package cn.yaxma.autoscrollviewpager;

import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Banner page 切换监听器
 * Created by Administrator on 2016/10/26.
 */
public class BannerPagerListener implements ViewPager.OnPageChangeListener {

    /**
     * 轮播的图片地址集
     */
    private List<String> urlList;
    /**
     * 指示器小圆点容器
     */
    private LinearLayout dotGroup;
    /**
     * 上一个被选中的小圆点索引，默认为0
     */
    private int preDotPosition = 0;

    public void setDotChangeData(List<String> urlList, LinearLayout dotGroup) {
        this.urlList = urlList;
        this.dotGroup = dotGroup;
    }

    @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override public void onPageScrollStateChanged(int state) {

    }

    @Override public void onPageSelected(int position) {
        if (urlList != null) {
            // 取余后的索引，得到新的page的索引.
            int newPosition = position % urlList.size();
            // 把上一个点设置为未选中.
            dotGroup.getChildAt(preDotPosition).setEnabled(false);
            // 当前索引点设置为被选中.
            dotGroup.getChildAt(newPosition).setEnabled(true);
            // 新的索引赋值给上一个索引.
            preDotPosition = newPosition;
        }
    }
}
