package utils;

import java.util.HashMap;
import java.util.Map;

public class TestData {

    public static Map<String, Object> validBookPayload() {
        Map<String, Object> body = new HashMap<>();

        body.put("id", 101);
        body.put("title", "Avenga Task Vladimir Sovric");
        body.put("description", "Created Book For API automation task");
        body.put("pageCount", 555);
        body.put("excerpt", "some small excerpt for the book REST API");
        body.put("publishDate", "2026-04-29T18:06:01.549Z");

        return body;
    }

    public static Map<String, Object> invalidBookPayload() {
        Map<String, Object> body = new HashMap<>();

        body.put("id", "invalid_id");
        body.put("title", 1234);
        body.put("pageCount", "invalidInput");

        return body;
    }
}
