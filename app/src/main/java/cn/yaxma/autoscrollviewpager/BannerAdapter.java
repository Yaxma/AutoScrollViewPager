package cn.yaxma.autoscrollviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 轮播图适配器
 * Created by Administrator on 2016/10/26.
 */
public class BannerAdapter extends PagerAdapter {

    private Context mContext;
    private OnItemClickListener listener;
    private List<String> urlList;

    public BannerAdapter(Context context) {
        this.mContext = context;
    }

    public void addUrlList(List<String> urlList) {
        this.urlList = urlList;
        notifyDataSetChanged();
    }

    @Override public int getCount() {
        return urlList == null ? 0 : Integer.MAX_VALUE;
    }

    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext).load(urlList.get(getPosition(position))).into(imageView);
        // 设置监听器
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (listener != null)
                    listener.onItemClick(getPosition(position));
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 获取真正的position
     */
    public int getPosition(int position) {
        return position % urlList.size();
    }

    /**
     * 设置页卡点击监听器
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
