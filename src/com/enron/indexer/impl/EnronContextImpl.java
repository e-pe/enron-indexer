package com.enron.indexer.impl;

import com.enron.indexer.EnronContext;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class EnronContextImpl implements EnronContext {
    private static final Logger logger =
        LoggerFactory.getLogger(EnronContextImpl.class);

    private TransportClient client = null;
    private EnronContextImplOptions contextOptions;


    public EnronContextImpl(EnronContextImplOptions options) {
        this.contextOptions = options;
    }

    @Override
    public EnronContext connect() {
        TransportClient currentClient = this.getTransportClient();

        if (currentClient.connectedNodes().size() == 0) {
            logger.error("No connected nodes found.");
        }

        return this;
    }

    @Override
    public EnronContext disconnect() {
        TransportClient currentClient = this.getTransportClient();

        if (currentClient != null) {
            currentClient.close();

            this.client = null;
        }

        return this;
    }

    @Override
    public Client getClient() {
       return this.getTransportClient();
    }

    private TransportClient getTransportClient() {
        if (this.client == null) {
            this.client = this.createClient();
        }

        return this.client;
    }

    private TransportClient createClient() {
        TransportClient transportClient = null;

        try {
            transportClient = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(
                        new InetSocketTransportAddress(
                                InetAddress.getByName(this.contextOptions.getHost()),
                                this.contextOptions.getPort()));

        } catch (UnknownHostException e) {
            logger.error("Could not create the elasticsearch client");

            e.printStackTrace();
        }

        return transportClient;
    }
}
