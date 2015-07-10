package ge.quickscope.popcorn.ui.helpers;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Transition;

import ge.quickscope.popcorn.R;

/**
 * Created by Jay on 7/9/2015.
 */
public class TransitionAnimations {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Transition setEnterTransition () {
        Explode explodeTransition = new Explode();
        return explodeTransition;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Transition setSharedElementEnterTransition(Context context) {
        Transition changeBounds = new ChangeBounds();
        changeBounds.addTarget(R.id.cover);
        return changeBounds;
    }
}
