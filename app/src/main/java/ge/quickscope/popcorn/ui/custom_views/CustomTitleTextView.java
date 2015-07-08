package ge.quickscope.popcorn.ui.custom_views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Jay on 7/8/2015.
 */
public class CustomTitleTextView extends TextView {

    public CustomTitleTextView(Context context) {
        super(context);
        if (!isInEditMode())
            init(context);
    }

    public CustomTitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init(context);
    }

    public CustomTitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            init(context);
    }

    private void init(Context context){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "custom-font.ttf");
        this.setTypeface(font);
    }
}
