package com.example.xieyangyi.framesdk.netloader.engine;

import android.text.TextUtils;
import android.view.View;

import com.example.xieyangyi.framesdk.Json.JsonUtil;
import com.example.xieyangyi.framesdk.netloader.NetRequest;
import com.google.gson.JsonParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by xieyangyi on 16/8/15.
 */
public abstract class EngineRunnable<T> implements Runnable {

    private final NetRequest request;
    private final T response;
    private final Exception exception;

    public EngineRunnable(NetRequest request, T response, Exception exception) {
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

    protected abstract String getResponseString(T response);

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

        String responseString = null;

        request.setStatus(NetRequest.Status.COMPLETE);

        if(response != null) {
            responseString = getResponseString(response);
        }

        if (TextUtils.isEmpty(responseString)) {
            return;
        }

        try {
            // toJson first
            Object result = jsonParse(responseString);
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


        } catch (JsonParseException e) {
            handleError(e);
        }
    }

    private Object jsonParse(String jsonStr) {
        Type type = getType();
        if (type == String.class) {
            return jsonStr;
        }
        return JsonUtil.fromJson(jsonStr, getType());
    }

    private Type getType() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof Class) {
            throw new RuntimeException("type parameter can't be found");
        }
        ParameterizedType type = (ParameterizedType) superClass;
        return type.getActualTypeArguments()[0];
    }
}
