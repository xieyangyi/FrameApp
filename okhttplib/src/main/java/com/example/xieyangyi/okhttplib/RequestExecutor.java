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
        mExecutor.execute(new RequestRunnable(netRequest, null, error));
    }

    public void postResponse(NetRequest netRequest, Response response) {
        mExecutor.execute(new RequestRunnable(netRequest, response, null));
    }

    public void postCacheResponse(NetRequest netRequest, Response response, Exception error) {
        mExecutor.execute(new RequestRunnable(netRequest, response, error));
    }

    private class RequestRunnable extends EngineRunnable<Response> {

        public RequestRunnable(NetRequest request, Response response, Exception exception) {
            super(request, response, exception);
        }

        @Override
        protected String getResponseString(Response response) {
            if (response == null) {
                return null;
            }

            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
