package com.github.lzyzsd.jsbridge;

import android.webkit.JavascriptInterface;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * @author dengyuanting
 * @date 2019-12-19
 */
public class JsSdkApi {
    private BridgeWebView mWebView;

    public JsSdkApi(BridgeWebView webView) {
        this.mWebView = webView;
    }

    @JavascriptInterface
    public String call(String param) {
        String scheme = "";
        try {
            scheme = URLDecoder.decode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final String finalScheme = scheme;
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                if (finalScheme.startsWith(BridgeUtil.YY_RETURN_DATA)) {
                    // 如果是返回数据
                    mWebView.handlerReturnData(finalScheme);
                } else if (finalScheme.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) {
                    mWebView.flushMessageQueue();
                }
            }
        });
        return "";
    }

}
