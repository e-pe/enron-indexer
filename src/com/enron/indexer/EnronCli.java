package com.enron.indexer;

import com.enron.indexer.impl.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnronCli {
    private static final Logger logger =
            LoggerFactory.getLogger(EnronCli.class);

    public static void main(String[] args) {
        logger.info("Starting the enron indexer");

        String enronJsonFilePath =
                EnronCliUtility.extractInputArgFrom(args);

        EnronJsonLoader enronJsonLoader =
                new EnronJsonLoaderImpl(enronJsonFilePath);

        EnronContext enronContext = new EnronContextImpl(
                new EnronContextImplOptions()
                    .setHost("127.0.0.1")
                    .setPort(9300));

        EnronIndexer enronIndexer = new EnronIndexerImpl(
                new EnronIndexerImplOptions()
                    .setEnronContext(enronContext)
                    .setEnronJsonLoader(enronJsonLoader)
                    .setOnIndexSuccess((Object success /*should be replaced by a concrete class */) -> {
                        logger.info("Successfully indexed the data from " + enronJsonFilePath);
                    })
                    .setOnIndexFailure((Object failure /*should be replaced by a concrete class */) -> {
                        logger.error("Could not index the data.");
                    }));

        enronIndexer.buildIndex();
    }
}
