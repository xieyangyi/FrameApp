package com.example.xieyangyi.framesdk.netloader.engine;

import android.text.TextUtils;
import android.view.View;

import com.example.xieyangyi.framesdk.Json.JsonUtil;
import com.example.xieyangyi.framesdk.netloader.NetRequest;
import com.google.gson.JsonParseException;

import java.util.List;

/**
 * Created by xieyangyi on 16/8/15.
 */
public class EngineRunnable implements Runnable {

    protected final NetRequest request;
    protected final String response;
    protected final Exception exception;

    public EngineRunnable(NetRequest request, String response, Exception exception) {
        this.request = request;
        this.response = response;
        this.exception = exception;
    }

    @Override
    public void run() {
        if (request.isRunning()) {
            if (exception != null) {
                handleError(exception);
                if (response != null) {
                    handleResponse();
                }
            } else if (response != null) {
                handleResponse();
            }
            request.clear();
        }
    }

    private void handleError(Exception exception) {
        request.setStatus(NetRequest.Status.FAILED);

        if (request.getListener() != null) {
            request.getListener().onFailed(exception);
        }
        if (request.getEmptyView() != null) {
            request.getEmptyView().setVisibility(View.VISIBLE);
            request.getEmptyView().resetAsFailed();
        }
    }

    private void handleResponse() {

        request.setStatus(NetRequest.Status.COMPLETE);

        if (TextUtils.isEmpty(response)) {
            return;
        }

        Object result = null;
        if (request.getJsonClass() == null) {
            result = response;
        } else {
            // toJson first
            try {
                result = jsonParse(response, request.getJsonClass());
            } catch (JsonParseException e) {
                handleError(e);
            }
        }

        if (result == null) {
            throw new IllegalArgumentException("json result is null");
        }

        // listener
        if (request.getListener() != null) {
            request.getListener().onSucceed(result);
        }
        if (request.getEmptyView() != null) {
            if (result == null || (result instanceof List && ((List) result).size() == 0)) {
                // todo, need to make this logic more correct
                request.getEmptyView().setVisibility(View.VISIBLE);
                request.getEmptyView().resetAsEmpty();
            } else {
                request.getEmptyView().setVisibility(View.GONE);
            }
        }
    }

    private Object jsonParse(String jsonStr, Class clazz) {
//        Type type = getType();
//        if (type == String.class) {
//            return jsonStr;
//        }
//        return JsonUtil.fromJson(jsonStr, getType());
        // todo, make result in listener not as object, so no need to do class cast when using it
        return JsonUtil.fromJson(jsonStr, clazz);
    }

//    private Type getType() {
//        Type superClass = getClass().getGenericSuperclass();
//        if (superClass instanceof Class) {
//            throw new RuntimeException("type parameter can't be found");
//        }
//        ParameterizedType type = (ParameterizedType) superClass;
//        return type.getActualTypeArguments()[0];
//    }
}
