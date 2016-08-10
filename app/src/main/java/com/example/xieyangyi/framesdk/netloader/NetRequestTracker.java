package com.example.xieyangyi.framesdk.netloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieyangyi on 16/8/9.
 */
public class NetRequestTracker {

    private final List<Request> requests = new ArrayList<>();
    private final List<Request> pendingRequests = new ArrayList<>();
    private boolean isPuased = false;

    public void runRequest(Request request) {
        requests.add(request);

        if (isPuased) {
            pendingRequests.add(request);
        } else {
            request.begin();
        }
    }

    public void addRequest(Request request) {
        requests.add(request);
    }

    public void removeRequest(Request request) {
        requests.remove(request);
        pendingRequests.remove(request);
    }

    public void pauseRequests() {
        isPuased = true;
        for (Request request : requests) {
            if (request.isRunning()) {
                request.pause();
                pendingRequests.add(request);
            }
        }
    }

    public void resumeRequests() {
        isPuased = false;
        for (Request request : requests) {
            if (!request.isComplete() && !request.isCancelled() && !request.isRunning()) {
                request.begin();
            }
        }
        pendingRequests.clear();
    }

    public void clearRequests() {
        for (Request request : requests) {
            request.clear();
        }
        requests.clear();
        pendingRequests.clear();
    }

    public void restartRequests() {
        for (Request request : requests) {
            if (!request.isComplete() && !request.isCancelled()) {
                // pause first, then begin, called restart
                request.pause();
                if (!isPuased) {
                    request.begin();
                } else {
                    pendingRequests.add(request);
                }
            }
        }
    }

    public boolean isPuased() {
        return isPuased;
    }
}
