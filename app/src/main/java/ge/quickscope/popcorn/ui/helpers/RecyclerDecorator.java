package ge.quickscope.popcorn.ui.helpers;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ge.quickscope.popcorn.R;

/**
 * Created by Jay on 7/7/2015.
 */
public class RecyclerDecorator extends RecyclerView.ItemDecoration{
    private int pad;

    public RecyclerDecorator(Context context){
        pad = context.getResources().getDimensionPixelSize(R.dimen.pad_size);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(pad, pad, pad, pad);
    }
}
