package uk.co.leifgehrmann.locationtest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;
import android.view.View;
import android.webkit.WebView;

public class ScrollViewWeb extends ScrollView {

    public ScrollViewWeb(Context context) { super(context); }
    public ScrollViewWeb( Context context, AttributeSet attributeSet) { super(context, attributeSet); }
    public ScrollViewWeb( Context context, AttributeSet attributeSet, int defStyle) { super(context, attributeSet, defStyle); }

    @Override
    public void requestChildFocus(View child, View focused) {
        if (focused instanceof WebView )
            return;
        super.requestChildFocus(child, focused);
    }
}