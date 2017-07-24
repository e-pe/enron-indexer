package com.enron.indexer.impl;

import com.enron.indexer.EnronIndexer;
import com.fasterxml.jackson.databind.JsonNode;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;

import java.util.Map;
import java.util.Vector;

public class EnronIndexerImpl implements EnronIndexer {
    private EnronIndexerImplOptions indexerOptions;

    private static String ENRON_INDEX_NAME = "enron";
    private static String ENRON_MAIL_TYPE_NAME = "mail";

    public EnronIndexerImpl(EnronIndexerImplOptions options) {

        if (options.getEnronContext() == null) {
            throw new IllegalArgumentException("EnronContext cannot be null.");
        }

        if (options.getEnronJsonLoader() == null) {
            throw new IllegalArgumentException("EnronJsonLoader cannot be null.");
        }

        this.indexerOptions = options;
    }

    @Override
    public EnronIndexer buildIndex() {
        Client client = this.indexerOptions.getEnronContext()
                .connect()
                .getClient();

        Vector<JsonNode> nodes = this.indexerOptions.getEnronJsonLoader()
                .load();

        BulkRequestBuilder bulkRequest = client.prepareBulk();

        try {

            for(JsonNode node : nodes) {
                Map<String, String> nodeMap =
                        EnronIndexerImplUtility.normalizeNode(node);

                IndexRequestBuilder indexRequest =
                    client.prepareIndex(
                            ENRON_INDEX_NAME,
                            ENRON_MAIL_TYPE_NAME,
                            nodeMap.get("id"))
                    .setSource(nodeMap.get("content"), XContentType.JSON);

                bulkRequest.add(indexRequest);
            }

            BulkResponse bulkResponse = bulkRequest.get();

            if (bulkResponse.hasFailures()) {
                if (this.indexerOptions.getOnIndexFailure() != null) {
                    this.indexerOptions.getOnIndexFailure()
                        .accept(null);
                }

            } else {
                if (this.indexerOptions.getOnIndexSuccess() != null) {
                    this.indexerOptions.getOnIndexSuccess()
                            .accept(null);
                }
            }

        } catch (Exception e) {

            if (this.indexerOptions.getOnIndexFailure() != null) {
                this.indexerOptions.getOnIndexFailure()
                        .accept(null);
            }

        } finally {
            this.indexerOptions.getEnronContext()
                    .disconnect();
        }

        return this;
    }
}
