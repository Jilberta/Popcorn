package ge.quickscope.popcorn.ui.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Jay on 7/9/2015.
 */
public class ScrollWatcherScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;

    public ScrollWatcherScrollView(Context context) {
        super(context);
    }

    public ScrollWatcherScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWatcherScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener){
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if(scrollViewListener != null){
            scrollViewListener.onScrollChanged(this, x, y, oldX, oldY);
        }
    }
}
