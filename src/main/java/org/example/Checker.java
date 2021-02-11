package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import static org.example.JsonParser.toYaml;

/**
 * Class contains methods for Checkers.
 */
public class Checker {
    /**
     * instance of logger for current class.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(Checker.class.getName());
    /**
     * instance of json file.
     */
    private static String  JSON_FILE ="src/test/java/resources/firstcase.json";
    /**
     * instance of mapper.
     */
    private static ObjectMapper mapper = new ObjectMapper();
    /**
     * instance of Scanner.
     */
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        if (args == null || args.length == 0) {
            args = readNextLine(scan);
        }
        checkConditon(args);
    }

    /**
     * Method checks Conditon of json.
     *
     * @param args array of params
     */
    public static Map<String, Integer> checkConditon(String[] args) {
        LOG.debug("");
        JsonNode rootNode = null;
        try {
            rootNode = JsonParser.getExampleRoot(
                    args != null && args.length != 0 || " ".equals(args) ? args[0] : JSON_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String yaml = toYaml(rootNode);
        System.out.println(yaml);

        return JsonParser.printResult(
                yaml,
                args.length > 1 ? args[1] : "first",
                args.length > 2 ? args[2] : "Tatu");
    }
    /**
     * Method reads Next Line.
     *
     * @param scan scanner
     */
    private static String[] readNextLine(final Scanner scan) {
        LOG.debug("");
        System.out.println(
                "Input params in a following format \n"
                 + "'src/test/java/resources/firstcase.json' first Tatu :\n"
                 + "or press Enter");
        final String[] list = scan.nextLine().split("\\s+");
        return list;
    }
}

