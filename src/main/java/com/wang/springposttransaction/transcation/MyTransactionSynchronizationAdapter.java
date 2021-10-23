package com.wang.springposttransaction.transcation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;

/**
 * @version 1.0
 * @Aythor lucksoul 王吉慧
 * @date 2021/10/22 23:38
 * @description
 */
@Component
public class MyTransactionSynchronizationAdapter extends TransactionSynchronizationAdapter {
    @Override
    public void suspend() {
        super.suspend();
        System.out.println("suspend...");
    }

    @Override
    public void resume() {
        super.resume();
        System.out.println("resume...");
    }

    @Override
    public void flush() {
        super.flush();
        System.out.println("flush...");
    }

    @Override
    public void beforeCommit(boolean readOnly) {
        super.beforeCommit(readOnly);
        System.out.println("beforeCommit...");
    }

    @Override
    public void beforeCompletion() {
        super.beforeCompletion();
        System.out.println("beforeCompletion...");
    }

    @Override
    public void afterCommit() {
        super.afterCommit();
        System.out.println("afterCommit...");
    }

    @Override
    public void afterCompletion(int status) {
        super.afterCompletion(status);
        System.out.println("afterCompletion...");
    }
}
