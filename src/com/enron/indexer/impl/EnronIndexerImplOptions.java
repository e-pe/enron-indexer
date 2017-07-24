package com.enron.indexer.impl;

import com.enron.indexer.EnronContext;
import com.enron.indexer.EnronJsonLoader;

import java.util.function.Consumer;

public class EnronIndexerImplOptions {
    private EnronContext enronContext;
    private EnronJsonLoader enronJsonLoader;
    private Consumer<Object> onIndexSuccess;
    private Consumer<Object> onIndexFailure;

    public EnronContext getEnronContext() {
        return this.enronContext;
    }

    public EnronIndexerImplOptions setEnronContext(EnronContext enronContext) {
        this.enronContext = enronContext;

        return this;
    }

    public EnronJsonLoader getEnronJsonLoader() {
        return this.enronJsonLoader;
    }

    public EnronIndexerImplOptions setEnronJsonLoader(EnronJsonLoader enronJsonLoader) {
        this.enronJsonLoader = enronJsonLoader;

        return this;
    }

    public Consumer<Object> getOnIndexSuccess() {
        return this.onIndexSuccess;
    }

    public EnronIndexerImplOptions setOnIndexSuccess(Consumer<Object> onIndexSuccess) {
        this.onIndexSuccess = onIndexSuccess;

        return this;
    }

    public Consumer<Object> getOnIndexFailure() {
        return this.onIndexFailure;
    }

    public EnronIndexerImplOptions setOnIndexFailure(Consumer<Object> onIndexFailure) {
        this.onIndexFailure = onIndexFailure;

        return this;
    }
}
