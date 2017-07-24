package com.enron.indexer.impl;

import com.enron.indexer.EnronJsonLoader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

public class EnronJsonLoaderImpl implements EnronJsonLoader {
    private static final Logger logger =
            LoggerFactory.getLogger(EnronJsonLoaderImpl.class);

    private String enronJsonFilePath;

    public EnronJsonLoaderImpl(String enronJsonFilePath) {
        if (enronJsonFilePath == null || enronJsonFilePath.isEmpty()) {
            throw new IllegalArgumentException("Path to enron json cannot be unset.");
        }

        this.enronJsonFilePath = enronJsonFilePath;
    }

    @Override
    public Vector<JsonNode> load(){
        Vector<JsonNode> nodes = new Vector<>();

        try {
            ObjectMapper mapper = new ObjectMapper();

            BufferedReader fileReader = new BufferedReader(
                    new FileReader(this.enronJsonFilePath));

            String readLine = null;

            while((readLine = fileReader.readLine()) != null){
                nodes.add(mapper.readTree(readLine));
            }

            logger.info("Found " + nodes.size() + " for import.");

            fileReader.close();

            return nodes;

        } catch (Exception e) {
            logger.error("Could not parse the enron json file " + this.enronJsonFilePath);
        }

        return nodes;
    }
}
