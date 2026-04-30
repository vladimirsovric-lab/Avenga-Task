package config;

public class TestConfig {
    public static final String BASE_URL = getBaseUrl();

    private static String getBaseUrl() {

        String baseUrl = System.getenv("BASE_URL");

        if (baseUrl == null || baseUrl.isBlank()) {
            return "https://fakerestapi.azurewebsites.net";
        }

        return baseUrl;
    }
}
