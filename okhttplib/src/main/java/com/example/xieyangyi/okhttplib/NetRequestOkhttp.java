package com.example.xieyangyi.okhttplib;

import android.content.Context;

import com.example.xieyangyi.framesdk.Utils;
import com.example.xieyangyi.framesdk.netloader.NetRequest;
import com.example.xieyangyi.framesdk.netloader.engine.NetEngineImpl;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by xieyangyi on 16/8/10.
 */
public class NetRequestOkhttp implements NetEngineImpl {

    public static final String TAG = "okhttp";

    private static NetRequestOkhttp sInstance;
    private static OkHttpClient sOkHttpClient = new OkHttpClient();

    public static NetRequestOkhttp getInstance() {
        if (sInstance == null) {
            synchronized (NetRequestOkhttp.class) {
                if (sInstance == null) {
                    sInstance = new NetRequestOkhttp();
                    init(Utils.getApplicationContext(), false);
                }
            }
        }
        return sInstance;
    }

    public static OkHttpClient getOkHttpClient() {
        return sOkHttpClient;
    }

    private static void init(Context context, boolean debug) {
        X509TrustManager x509m = new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        };

        try {
            SSLContext e = SSLContext.getInstance("SSL");
            e.init((KeyManager[])null, new TrustManager[]{x509m}, new SecureRandom());
            sOkHttpClient.setSslSocketFactory(e.getSocketFactory());
//            Internal.instance.setCache(sOkHttpClient, new DnsCache(new File(context.getExternalCacheDir(), "okhttp"), 524288000L));
            sOkHttpClient.setConnectTimeout(30L, TimeUnit.SECONDS);
            sOkHttpClient.setReadTimeout(30L, TimeUnit.SECONDS);
            sOkHttpClient.setWriteTimeout(30L, TimeUnit.SECONDS);
            sOkHttpClient.setRetryOnConnectionFailure(true);
//            if(debug) {
//                sOkHttpClient.setProxy(MyProxySelector.selectOneProxy());
//                sOkHttpClient.setProxySelector(new MyProxySelector());
//            }
        } catch (KeyManagementException | NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        }

    }

    @Override
    public void load(final NetRequest netRequest) {
        final Request request = RequestManager.build(netRequest);
        Call call = sOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                RequestManager.handleError(request, netRequest, e, true);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                RequestManager.handleResponse(request, netRequest, response, true);
            }
        });
    }


}
