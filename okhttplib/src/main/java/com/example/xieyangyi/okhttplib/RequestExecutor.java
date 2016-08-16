package com.example.xieyangyi.okhttplib;

import android.os.Handler;

import com.example.xieyangyi.framesdk.netloader.NetRequest;
import com.example.xieyangyi.framesdk.netloader.engine.EngineRunnable;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Created by xieyangyi on 16/8/14.
 */
public class RequestExecutor  {
    private final Executor mExecutor;

    public RequestExecutor(final Handler handler) {
        mExecutor = new Executor() {
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

    public void postError(NetRequest netRequest, Exception error) {
        mExecutor.execute(new EngineRunnable(netRequest, null, error));
    }

    public void postResponse(NetRequest netRequest, Response response) {
        try {
            mExecutor.execute(new EngineRunnable(netRequest, response.body().string(), null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postCacheResponse(NetRequest netRequest, Response response, Exception error) {
        try {
            mExecutor.execute(new EngineRunnable(netRequest, response.body().string(), error));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
