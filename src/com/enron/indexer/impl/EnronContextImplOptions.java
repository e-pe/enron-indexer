package com.enron.indexer.impl;

public class EnronContextImplOptions {
    private String host;
    private Integer port;

    public String getHost() {
        return this.host;
    }

    public EnronContextImplOptions setHost(String host) {
        this.host = host;

        return this;
    }

    public Integer getPort() {
        return this.port;
    }

    public EnronContextImplOptions setPort(Integer port) {
        this.port = port;

        return this;
    }
}
