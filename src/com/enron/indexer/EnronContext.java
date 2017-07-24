package com.enron.indexer;

import org.elasticsearch.client.Client;

public interface EnronContext {
    EnronContext connect();
    EnronContext disconnect();

    Client getClient();
}
