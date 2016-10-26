package cn.yaxma.autoscrollviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * ViewPager自定义轮播器
 * Created by Administrator on 2016/10/26.
 */
public class AutoScrollViewPager extends ViewPager {

    public AutoScrollViewPager(Context context) {
        super(context);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 轮播时间
     */
    private int showTime = 3 * 1000;

    /**
     * 轮播方向
     */
    private Direction direction = Direction.LEFT;

    /**
     * 设置轮播时间
     *
     * @param millis 毫秒
     */
    public void setShowTime(int millis) {
        this.showTime = millis;
    }

    /**
     * 设置轮播方向
     *
     * @param direction 方向
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * 开始播放
     */
    public void start() {
        stop();
        postDelayed(player, showTime);
    }

    /**
     * 停止播放
     */
    public void stop() {
        removeCallbacks(player);
    }

    /**
     * 播放上一个
     */
    public void previous() {
        if (direction == Direction.LEFT) {
            play(Direction.RIGHT);
        } else if (direction == Direction.RIGHT) {
            play(Direction.LEFT);
        }
    }

    /**
     * 播放下一个
     */
    public void next() {
        play(direction);
    }

    /**
     * @param direction 方向
     */
    private synchronized void play(Direction direction) {
        PagerAdapter pagerAdapter = getAdapter();
        if (pagerAdapter != null) {
            int count = pagerAdapter.getCount();
            int currentItem = getCurrentItem();
            switch (direction) {
                case LEFT:
                    currentItem++;
                    if (currentItem > count)
                        currentItem = 0;
                    break;
                case RIGHT:
                    currentItem--;
                    if (currentItem < 0)
                        currentItem = count;
                    break;
            }
            setCurrentItem(currentItem);
        }
        start();
    }

    /**
     * 播放器
     */
    private Runnable player = new Runnable() {
        @Override public void run() {
            play(direction);
        }
    };

    public enum Direction {
        LEFT,
        RIGHT
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override public void onPageSelected(int position) {

            }

            @Override public void onPageScrollStateChanged(int state) {
                // state: 三种状态，1-->2-->0
                // SCROLL_STATE_DRAGGING正在滑动/拖动;
                // SCROLL_STATE_SETTLING拖动/拖动结束;
                // SCROLL_STATE_IDLE空闲状态
                if (state == SCROLL_STATE_IDLE) {
                    start();
                } else if (state == SCROLL_STATE_DRAGGING) {
                    stop();
                }
            }
        });
    }
}
