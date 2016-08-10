package com.example.xieyangyi.framesdk.netloader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xieyangyi on 16/8/9.
 */
public class NetRequstParams {

    String url;
    Map<String, Object> params;
    byte[] body;
    String tag;
    int cacheTime;
    Map<String, String> header = new HashMap<>();
    Type type;
    NetRequestListener listener;
    EmptyView emptyView;

    public String getUrl() {
        return url;
    }

    public void recycle() {

        url = null;
        if (params != null) {
            params.clear();
        }
        params = null;
        body = null;
        tag = null;
        cacheTime = 0;
        if (header != null) {
            header.clear();
        }
        header = null;
        type = Type.GET;
        listener = null;
        emptyView = null;

    }

    public static enum Type {
        GET,
        POST,
        PUT,
        DELETE,
        UPLOAD;

        private Type() {
        }
    }
}
