package pt.alex.flickrized.modules.common;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by nb19875 on 24/06/16.
 * Taken from https://github.com/chrisbanes/PhotoView/blob/master/sample/src/main/java/uk/co/senab/photoview/sample/HackyViewPager.java
 */
public class HackyViewPager extends ViewPager{
    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            // remove log da exception
//            e.printStackTrace();
            return false;
        }
    }
}
