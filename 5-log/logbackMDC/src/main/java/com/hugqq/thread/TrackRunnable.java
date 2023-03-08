package com.hugqq.thread;

import com.hugqq.interceptor.LogHandlerInterceptor;

// 计算机科学领域的任何问题都可以通过增加一个间接的中间层来解决
// 委托模式
public abstract class TrackRunnable implements Runnable {

    public abstract void trackRun();

    private final String track = LogHandlerInterceptor.getTrack();

    @Override
    public void run() {
        try {
            LogHandlerInterceptor.setTrace(track);
            trackRun();
        } finally {
            LogHandlerInterceptor.remove();
        }
    }
}
