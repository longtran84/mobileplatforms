/*
 *  Copyright (C) 2018 - 2019 FINTECHVIET
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package vn.fintechviet.mobileplatforms.ui.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

/**
 * Increases the vertical touch threshold before we are locked into vertical scrolling
 */
public class HorizontalFavoringNestedScrollView extends NestedScrollView {
    private static final int INVALID_POINTER = -1;
    private int mLastMotionY;
    private int mTouchSlop;
    private int mActivePointerId;

    public HorizontalFavoringNestedScrollView(Context context) {
        super(context);
        init();
    }

    public HorizontalFavoringNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalFavoringNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Gives extra preference to horizontal scrolling
        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_MOVE: {
                final int activePointerId = mActivePointerId;
                if (activePointerId == INVALID_POINTER) {
                    // If we don't have a valid id, the touch down wasn't on content.
                    break;
                }

                final int pointerIndex = MotionEventCompat.findPointerIndex(ev, activePointerId);
                if (pointerIndex == -1) {
                    break;
                }
                final int x = (int) MotionEventCompat.getX(ev, pointerIndex);
                final int y = (int) MotionEventCompat.getY(ev, pointerIndex);
                final int yDiff = Math.abs(y - mLastMotionY);
                if (yDiff < mTouchSlop) {
                    return false;
                }
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                final int y = (int) ev.getY();
                if (!inChild((int) ev.getX(), y)) {
                    break;
                }

                /*
                 * Remember location of down touch.
                 * ACTION_DOWN always refers to pointer index 0.
                 */
                mLastMotionY = y;
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                break;
            }
            case MotionEvent.ACTION_UP: {
                /* Release the drag */
                mActivePointerId = INVALID_POINTER;
                break;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    private void init() {
        mTouchSlop = (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP, 40f, getResources().getDisplayMetrics());
    }

    private boolean inChild(int x, int y) {
        if (getChildCount() > 0) {
            final int scrollY = getScrollY();
            final View child = getChildAt(0);
            return !(y < child.getTop() - scrollY
                    || y >= child.getBottom() - scrollY
                    || x < child.getLeft()
                    || x >= child.getRight());
        }
        return false;
    }
}
