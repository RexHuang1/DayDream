package com.android.rexuhuang.simpletextdream;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created On 2017/8/31 0031      13: 58.
 * Contact me in these ways below:
 * Email:
 *
 * @author RexHuang
 */

public class SimpleTextView extends TextView {

    private static final int START = 1;
    private static final int MOVE = 2;

    private int mDelay = 4000;
    private int mAnimatime = 4000;

    private boolean mAnimate = false;

    private Status status = Status.LEFT;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            boolean animate = false ;
            switch (msg.what){
                case MOVE:
                    animate = mAnimate;
                case START:
                    final View view = (View) SimpleTextView.this.getParent();
                    if (view == null)
                        return;
                        final float framew = view.getMeasuredWidth();
                        final float frameh = view.getMeasuredHeight();
                        final float textw = getMeasuredWidth();
                        final float texth = getMeasuredHeight();
                        float newx = (framew  - textw) / 2;
                        float newy = 0;
                    switch (status){
                        case LEFT:

                            break;
                        case UP:
                            newx = 0;
                            newy = (frameh - texth) / 2;
                            break;
                        case RIGHT:
                            newx = (framew  - textw) / 2;
                            newy = frameh - texth;
                            break;
                        case BOTTOM:
                            newx = framew - textw;
                            newy = (frameh  - texth) / 2;
                            break;
                    }
                    if (status == Status.BOTTOM) {
                        status = Status.LEFT;
                    }else {
                        status = Status.values()[status.ordinal() + 1];
                    }
                    if (animate){
                        animate().x(newx)
                                .y(newy)
                                .setDuration(mAnimatime)
                                .start();
                    }else{
                        setX(newx);
                        setY(newy);
                    }

                    removeMessages(MOVE);
                    sendEmptyMessageDelayed(MOVE,mDelay);
                    break;
            }
        }
    };

    public SimpleTextView(Context context) {
        super(context);
    }

    public SimpleTextView(Context context,  AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SimpleTextView(Context context,  AttributeSet attrs, int flags) {
        super(context, attrs,flags);
    }

    public void setmAnimate(boolean mAnimate) {
        this.mAnimate = mAnimate;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        final View parent = (View) SimpleTextView.this.getParent();

        parent.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (v == parent && right != oldRight){
                    mHandler.removeMessages(MOVE);
                    mHandler.sendEmptyMessage(START);
                }
            }
        });

        mHandler.sendEmptyMessage(START);
    }
}
