package org.example;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.example.Checker.checkConditon;
import static org.testng.Assert.assertEquals;

/**
 * Class contains test for JsonParser.
 */
public class JsonParserTest
{
    /**
     * instance of logger for current class.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(JsonParserTest.class.getName());
    /**
     * name of json file.
     */
    private final static String  JSON_FILE ="src/test/java/resources/firstcase.json";
    /**
     * name of json file.
     */
    private final static String  JSON_SECOND ="src/test/java/resources/second.json";
    /**
     * name of json file.
     */
    private final static String  JSON_THIRD ="src/test/java/resources/third.json";
    /**
     * name of json String.
     */
    private String json;
    /**
     * Method runs before Class
     */
    @BeforeClass
    public void beforeJsonParserTest() throws IOException {
        LOG.debug("");
        json = readFileAsString(JSON_FILE);
    }
    /**
     * Method Counts Keys of json
     */
    @Test(dataProvider = "CountOfKeys")
    public void shouldMatchCountOfKeys(String[] args,
                                       String keyCount, String valueCount) {
        LOG.debug("");
        Map<String, Integer> map = checkConditon(args);
        assertEquals(map.get(args[1]).toString(), keyCount,
                "There is a problem with сount Of keys");
    }
    /**
     * Method Counts Keys And Value of json
     */
    @Test(dataProvider = "CountOfKeysAndValue")
    public void shouldMatchCountOfKeysAndValue(String[] args,
                                               String keyCount, String valueCount) {
        LOG.debug("");
        Map<String, Integer> map = checkConditon(args);
        assertEquals(map.get(args[1]).toString(), keyCount,
                "There is a problem with сount Of keys");
        assertEquals(map.get(args[2]).toString(), valueCount,
                "There is a problem with сount Of keys/values");
    }
    /**
     * Method checks json String
     */
    @Test
    public void shouldMatchCountOfObjects() {
        LOG.debug("");
        Map<String, String> objectMap = JsonPath.read(json, "$");
        assertEquals(4, objectMap.keySet().size(),
                "There is a problem with сount Of Objects");
    }
    /**
     * Method checks json String
     */
    @Test
    public void shouldMatchCountOfArrays() {
        LOG.debug("");
        JSONArray jsonArray = JsonPath.read(json, "$.pets[*]");
        assertEquals(2, jsonArray.size(),
                "There is a problem with сount Of Arrays");
    }
    /**
     * Method checks json String
     */
    @Test
    public void shouldCheckValueOfKeys() {
        LOG.debug("");
        String jsonpathCreatorNamePath = "$['name']['last']['first']";
        DocumentContext jsonContext = JsonPath.parse(json);
        String jsonpathCreatorName = jsonContext.read(jsonpathCreatorNamePath);
        assertEquals("Tatu", jsonpathCreatorName,
                "Could not found value Of Keys");
    }
    /**
     * Method reads file from disk.
     *
     * @param fileName name of file
     */
    public String readFileAsString(final String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
    @DataProvider(name = "CountOfKeys")
    public Object[][] dataProviderCountOfKeys() {
        return new Object[][]{
                {
                        new String[]{JSON_FILE, "first", ""},
                        "2",
                        ""
                },
                {
                        new String[]{JSON_FILE, "test", ""},
                        "0",
                        ""
                },
                {
                        new String[]{JSON_SECOND, "title", ""},
                        "1",
                        ""
                },
                {
                        new String[]{JSON_SECOND, "test", ""},
                        "0",
                        ""
                },
                {
                        new String[]{JSON_THIRD, "price", ""},
                        "8",
                        ""
                },
                {
                        new String[]{JSON_THIRD, "test", ""},
                        "0",
                        ""
                },
        };
    }
    @DataProvider(name = "CountOfKeysAndValue")
    public Object[][] dataProviderCountOfKeysAndValue() {
        return new Object[][]{
                {
                        new String[]{JSON_FILE, "first", "Tatu"},
                        "2",
                        "2"
                },
                {
                        new String[]{JSON_FILE, "test", "Tatu"},
                        "0",
                        "0"
                },
                {
                        new String[]{JSON_FILE, "first", "text"},
                        "2",
                        "0"
                },
                {
                        new String[]{JSON_SECOND, "title", "JSON at Work"},
                        "3",
                        "2"
                },
                {
                        new String[]{JSON_SECOND, "test", "JSON at Work"},
                        "0",
                        "0"
                },
                {
                        new String[]{JSON_SECOND, "", "text"},
                        "0",
                        "0"
                },
                {
                        new String[]{JSON_THIRD, "author", "Acodemy"},
                        "8",
                        "2"
                },
                {
                        new String[]{JSON_THIRD, "author", "test"},
                        "8",
                        "0"
                },
                {
                        new String[]{JSON_THIRD, "", "text"},
                        "0",
                        "0"
                }
        };
    }
}
