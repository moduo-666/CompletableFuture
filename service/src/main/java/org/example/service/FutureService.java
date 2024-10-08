package org.example.service;

import java.util.concurrent.ExecutionException;

public interface FutureService {

    /**
     * CompletableFuture创建方式总结
     */
    void newFuture() throws ExecutionException, InterruptedException;
}
