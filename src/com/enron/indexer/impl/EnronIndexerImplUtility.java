package com.enron.indexer.impl;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EnronIndexerImplUtility {
    public static Map<String, String> normalizeNode(JsonNode node) {
        String nodeId = node.get("_id").get("$oid")
                .asText();

        String nodeContent = node.toString()
                .replaceAll("\"_id(.*?)},", "");

        Map<String, String> nodeMap = new HashMap<>();

        nodeMap.put("id", nodeId);
        nodeMap.put("content", nodeContent);

        return nodeMap;
    }
}
