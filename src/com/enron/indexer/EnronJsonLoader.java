package com.enron.indexer;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Vector;

public interface EnronJsonLoader {
    Vector<JsonNode> load();
}
