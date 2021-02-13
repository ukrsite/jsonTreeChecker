package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

import static java.lang.String.format;

/**
 * Class contains methods for JsonParser.
 */
public class JsonParser {
    /**
     * instance of logger for current class.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(JsonParser.class.getName());
    /**
     * instance of mapper.
     */
    private static ObjectMapper mapper = new ObjectMapper();
    /**
     * instance of Scanner.
     */
    private static Scanner scan = new Scanner(System.in);


    /**
     * Method gets json file from disk.
     *
     * @param jsonFile name of file
     */
    static JsonNode getExampleRoot(final String jsonFile) throws IOException {
        LOG.debug("");
        String jsonString = readFileAsString(jsonFile);
        JsonNode rootNode = mapper.readTree(jsonString);
        return rootNode;
    }
    /**
     * Method puts json to Yaml.
     *
     * @param root of json
     */
    public static String toYaml(JsonNode root) {
        LOG.debug("");
        StringBuilder yaml = new StringBuilder();
        processNode(root, yaml, 0);
        return yaml.toString();
    }
    /**
     * Method makes iterations.
     *
     * @param jsonNode of json
     * @param yaml file
     * @param depth of iterations
     */
    private static void processNode(JsonNode jsonNode, StringBuilder yaml, int depth) {
        LOG.debug("");
        if (jsonNode.isValueNode()) {
            yaml.append(jsonNode.asText());
        } else if (jsonNode.isArray()) {
            for (JsonNode arrayItem : jsonNode) {
                appendNodeToYaml(arrayItem, yaml, depth, true);
            }
        } else if (jsonNode.isObject()) {
            appendNodeToYaml(jsonNode, yaml, depth, false);
        }
    }
    /**
     * Method appends node to Yaml.
     *
     * @param node of json
     * @param yaml file
     * @param depth of iterations
     * @param isArrayItem value
     */
    private static void appendNodeToYaml(JsonNode node, StringBuilder yaml,
                                         int depth, boolean isArrayItem) {
        LOG.debug("");
        Iterator<Entry<String, JsonNode>> fields = node.fields();
        boolean isFirst = true;
        while (fields.hasNext()) {
            Entry<String, JsonNode> jsonField = fields.next();
            addFieldNameToYaml(yaml, jsonField.getKey(), depth, isArrayItem && isFirst);
            processNode(jsonField.getValue(), yaml, depth + 1);
            isFirst = false;
        }
    }
    /**
     * Method adds field to Yaml.
     *
     * @param yaml file
     * @param fieldName of field
     * @param depth of iterations
     * @param isFirstInArray value
     */
    private static void addFieldNameToYaml(
            StringBuilder yaml, String fieldName, int depth, boolean isFirstInArray) {
        LOG.debug("");
        if (yaml.length() > 0) {
            yaml.append("\n");
        }
        yaml.append(fieldName);
        yaml.append(": ");
    }
    /**
     * Method prints Result.
     *
     * @param yaml file
     * @param key of file
     * @param value of file
     */
    public static Map<String, Integer> printResult(final String yaml,
                                          final String key, final String value) {
        LOG.debug("");
        int keyCounter = 0;
        int valueCounter = 0;
        if (yaml == null && yaml.length() == 0) {
            return Collections.emptyMap();
        }
        final Scanner scanner = new Scanner(yaml);
        if (key == null && key.length() == 0) {
            return Collections.emptyMap();
        }
        while (scanner.hasNext()) {
            final String s = scanner.nextLine();
            if (s.contains(format("%s:", key))) {
                keyCounter++;
            }
            if (s.equals(format("%s: %s", key, value))) {
                valueCounter++;
            }
        }
        final HashMap<String, Integer> map = new HashMap<>();
        if (key != null) {
            map.put(key, keyCounter);
            System.out.println(format(
                    "- found %d objects with field '%s'", keyCounter, key));
        }
        if (key != null) {
            map.put(value, valueCounter);
            System.out.println(format(
                    "- found %d objects where '%s' equals '%s'", valueCounter, key, value));
        }
        return map;
    }
    public static String readFileAsString(final String fileName) throws IOException {
        LOG.debug("");
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}

