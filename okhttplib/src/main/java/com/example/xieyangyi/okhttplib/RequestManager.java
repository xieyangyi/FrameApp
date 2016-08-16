package com.example.xieyangyi.okhttplib;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.example.xieyangyi.framesdk.netloader.NetRequest;
import com.example.xieyangyi.framesdk.netloader.NetRequstParams;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.http.RealResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okio.GzipSource;
import okio.Okio;

/**
 * Created by xieyangyi on 16/8/14.
 */
public class RequestManager {

    private static RequestExecutor sExecutor = new RequestExecutor(new Handler(Looper.getMainLooper()));

    public static Request build(NetRequest netRequest) {
        if (TextUtils.isEmpty(netRequest.getUrl())) {
            throw new IllegalArgumentException("url can't be empty");
        } else {
            // url
            Request.Builder builder = new Request.Builder();
            builder.url(netRequest.getUrl());
            // tag
            if (TextUtils.isEmpty(netRequest.getTag())) {
                builder.tag(netRequest.getUrl());
            } else {
                builder.tag(netRequest.getTag());
            }
            // header
            for (String key : netRequest.getHeader().keySet()) {
                builder.addHeader(key, netRequest.getHeader().get(key));
            }
            // type
            if (netRequest.getType() == NetRequstParams.Type.GET) {
                builder.get();
            } else if (netRequest.getType() == NetRequstParams.Type.POST) {
                builder.post(RequestBody.create((MediaType) null, getParams(netRequest)));
            } else if (netRequest.getType() == NetRequstParams.Type.PUT) {
                builder.post(RequestBody.create((MediaType)null, getParams(netRequest)));
            } else if (netRequest.getType() == NetRequstParams.Type.DELETE) {
                builder.delete(RequestBody.create((MediaType)null, getParams(netRequest)));
            } else if (netRequest.getType() == NetRequstParams.Type.UPLOAD) {
                MultipartBuilder multipartBuilder = new MultipartBuilder();
                multipartBuilder.type(MultipartBuilder.FORM);
                if (netRequest.getParams() != null) {
                    for (String key : netRequest.getParams().keySet()) {
                        Object value = netRequest.getParams().get(key);
                        if (value instanceof File) {
                            File file = (File) value;
                            multipartBuilder.addFormDataPart(key, file.getName(), RequestBody.create((MediaType)null, file));
                        } else if (value instanceof String) {
                            multipartBuilder.addFormDataPart(key, (String) value);
                        }
                    }
                }
                builder.post(multipartBuilder.build());
            }
            // cache
            builder.cacheControl(generateCacheControl(netRequest));


            Request request = builder.build();
            return request;
        }
    }

    private static CacheControl generateCacheControl(NetRequest netRequest) {
        if(netRequest.getCacheTime() != 0) {
            CacheControl.Builder builder = new CacheControl.Builder();
            builder.maxStale(netRequest.getCacheTime(), TimeUnit.SECONDS);
            return builder.build();
        } else {
            return CacheControl.FORCE_NETWORK;
        }
    }

    private static byte[] getParams(NetRequest netRequest) {
        return netRequest.getBody() == null ? encodeParameters(netRequest.getParams(), "UTF-8") : netRequest.getBody();
    }

    private static byte[] encodeParameters(Map<String, Object> params, String paramsEncoding) {
        if(params == null) {
            return new byte[0];
        } else {
            StringBuilder encodedParams = new StringBuilder();

            try {
                Iterator var6 = params.entrySet().iterator();

                while(var6.hasNext()) {
                    Map.Entry entry = (Map.Entry)var6.next();
                    encodedParams.append(URLEncoder.encode((String)entry.getKey(), paramsEncoding));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), paramsEncoding));
                    encodedParams.append('&');
                }

                int var62 = encodedParams.length();
                return var62 > 0?encodedParams.substring(0, var62 - 1).getBytes(paramsEncoding):new byte[0];
            } catch (UnsupportedEncodingException var61) {
                return new byte[0];
            }
        }
    }

    public static Response handleError(Request request, NetRequest netRequest, IOException exception, boolean async) {
        Response res = loadCache(request, netRequest, exception, async);
        if (async && res == null) {
            sExecutor.postError(netRequest, exception);
        }
        return res;
    }

    public static Response handleResponse(Request request, NetRequest netRequest, Response response, boolean async)
            throws IOException {
        if (!response.isSuccessful()) {
            IOException exception;
            if (response.isRedirect()) {
                exception = new IOException("当前请求被劫持");
            } else {
                switch (response.code()) {
                    case 400:
                    case 403:
                    case 404:
                    case 405:
                    case 406:
                        exception = new IOException("请求资源失败");
                        break;
                    case 408:
                    case 504:
                        exception = new IOException("网络超时");
                        break;
                    case 500:
                    case 501:
                    case 502:
                    case 503:
                        exception = new IOException("服务器异常");
                        break;
                    default:
                        exception = new IOException("网络服务繁忙");
                }
            }

            return handleError(request, netRequest, exception, async);

        } else {
            if (async) {
                sExecutor.postResponse(netRequest, response);
            }
            return response;
        }
    }

    private static Response loadCache(Request request, NetRequest netRequest, Exception exception, boolean async) {
        if (!netRequest.isLoadCacheIfNetError()) {
            return null;
        }

        InternalCache cache = Internal.instance.internalCache(NetRequestOkhttp.getOkHttpClient());
        try {
            Response cacheCandidate = cache != null ? cache.get(request) : null;
            if (cacheCandidate != null) {
                Response response = unzip(cacheCandidate);
                if (async && response != null) {
                    sExecutor.postCacheResponse(netRequest, response, exception);
                }
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Response unzip(Response response) throws IOException {
        if(!"gzip".equalsIgnoreCase(response.header("Content-Encoding"))) {
            return response;
        } else if(response.body() == null) {
            return response;
        } else {
            GzipSource responseBody = new GzipSource(response.body().source());
            Headers strippedHeaders = response.headers().newBuilder().removeAll("Content-Encoding").removeAll("Content-Length").build();
            return response.newBuilder().headers(strippedHeaders).body(new RealResponseBody(strippedHeaders, Okio.buffer(responseBody))).build();
        }
    }

}
