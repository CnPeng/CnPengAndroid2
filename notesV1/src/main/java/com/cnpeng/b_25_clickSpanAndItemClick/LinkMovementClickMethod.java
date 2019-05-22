package com.cnpeng.b_25_clickSpanAndItemClick;

import android.graphics.Color;
import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 用来解决当textview的部分文字有点击功能并且整个textview有长点击的时候的两种事件的冲突的，点击用户名超过500ms就算长点击，不执行点击事件
 * 加上了点击的效果
 */

public class LinkMovementClickMethod extends LinkMovementMethod {
    private long lastClickTime;
    private static final long CLICK_DELAY = 500L;

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN
                || action == MotionEvent.ACTION_MOVE ||
                action == MotionEvent.ACTION_CANCEL) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();
            x += widget.getScrollX();
            y += widget.getScrollY();
//            widget.getline
            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);
            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    if (System.currentTimeMillis() - lastClickTime < CLICK_DELAY) {
                        link[0].onClick(widget);
                    }
                    buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT),
                            buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else if (action == MotionEvent.ACTION_DOWN) {
//                    Selection.setSelection(buffer,
//                            buffer.getSpanStart(link[0]),
//                            buffer.getSpanEnd(link[0]));
                    buffer.setSpan(new BackgroundColorSpan(Color.GRAY),
                            buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    lastClickTime = System.currentTimeMillis();
                } else {
                    buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT),
                            buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                widget.scrollTo(0, 0);
                return true;
            } else {
                buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT),
                        0, buffer.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        boolean touchEvent = super.onTouchEvent(widget, buffer, event);
        widget.scrollTo(0, 0);
        return touchEvent;
    }

    public static LinkMovementClickMethod getInstance() {
        if (null == sInstance) {
            sInstance = new LinkMovementClickMethod();
        }
        return sInstance;
    }

    private static LinkMovementClickMethod sInstance;
}