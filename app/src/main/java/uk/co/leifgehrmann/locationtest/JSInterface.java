package uk.co.leifgehrmann.locationtest;

import android.webkit.JavascriptInterface;

public class JSInterface {
    @JavascriptInterface
    public void onError(String error){
        throw new IllegalArgumentException(error);
    }
}