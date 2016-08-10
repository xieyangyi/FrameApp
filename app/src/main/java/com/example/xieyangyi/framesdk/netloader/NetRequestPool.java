package com.example.xieyangyi.framesdk.netloader;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by xieyangyi on 16/8/9.
 */
public class NetRequestPool {

    private static NetRequestPool sPool;
    private Queue<NetRequest> mRequestPool;


    private NetRequestPool() {
        // 之后支持自由配置pool里面对象个数
        mRequestPool = new ArrayDeque<NetRequest>(0);
    }

    public static NetRequestPool getInstance() {
        if (sPool == null) {
            synchronized (NetRequestPool.class) {
                if (sPool == null) {
                    sPool = new NetRequestPool();
                }
            }
        }
        return sPool;
    }


    public NetRequest obtain() {

        if (mRequestPool == null) return null;

        return mRequestPool.poll();
    }

    public void recycle(NetRequest request) {

        if (mRequestPool == null) return;

        mRequestPool.offer(request);
    }
}
