package ge.quickscope.popcorn.ui.helpers;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import butterknife.ButterKnife;
import ge.quickscope.popcorn.R;

/**
 * Created by Jay on 7/9/2015.
 */
public class GuiHelper {
    public static final int DEFAULT_DELAY = 30;
    public static final int SCALE_DELAY = 300;
    public static final float SCALE_START_ANCHOR = 0.3f;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void makeStatusbarTranslucent (Activity activity) {
        Window w = activity.getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    public static ViewPropertyAnimator showViewByScale (View v) {
        ViewPropertyAnimator propertyAnimator = v.animate().setStartDelay(DEFAULT_DELAY).scaleX(1).scaleY(1);
        return propertyAnimator;
    }

    public static ViewPropertyAnimator showViewByScaleY (View v, Animator.AnimatorListener animatorListener) {
        ViewPropertyAnimator propertyAnimator = v.animate().setStartDelay(SCALE_DELAY).scaleY(1);
        propertyAnimator.setListener(animatorListener);
        return propertyAnimator;
    }

    public static void startScaleAnimationFromPivotY (int pivotX, int pivotY, final View v, final Animator.AnimatorListener animatorListener) {
        final AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        v.setScaleY(SCALE_START_ANCHOR);
        v.setPivotX(pivotX);
        v.setPivotY(pivotY);
        v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                v.getViewTreeObserver().removeOnPreDrawListener(this);
                ViewPropertyAnimator viewPropertyAnimator = v.animate()
                        .setInterpolator(interpolator)
                        .scaleY(1)
                        .setDuration(SCALE_DELAY);
                if (animatorListener != null)
                    viewPropertyAnimator.setListener(animatorListener);
                viewPropertyAnimator.start();
                return true;
            }
        });
    }

    public static final ButterKnife.Setter<TextView, Integer> setter = new ButterKnife.Setter<TextView, Integer>() {
        @Override
        public void set(TextView view, Integer value, int index) {
            view.setTextColor(value);
        }
    };

    public static void tintAndSetCompoundDrawable (Context context, @DrawableRes int drawableRes, int color, TextView textview) {
        Resources res = context.getResources();
        int padding = (int) res.getDimension(R.dimen.activity_horizontal_margin_half);
        Drawable drawable = res.getDrawable(drawableRes);
        drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        textview.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
        textview.setCompoundDrawablePadding(padding);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setTheStatusbarNotTranslucent(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void makeTheStatusbarTranslucent (Activity activity) {
        Window w = activity.getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

}
