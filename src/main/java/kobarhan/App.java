package kobarhan;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class App {

    private static String domain = "https://api.telegram.org";
    private static String botId = "1618563181";

    private static String token = System.getenv("TOKEN");
    private static String baseUrl = String.format("%s/bot%s:%s", domain, botId, token);

    static CloseableHttpClient httpClient = HttpClients.createDefault();

    static JsonObject callApi(HttpRequestBase request) throws IOException {
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                // string to json object
                return new Gson().fromJson(result, JsonObject.class);
            }
        }
        return null;
    }

    // This method gets bot level updates (messages received etc.)
    private static JsonObject getUpdates() throws IOException, URISyntaxException {
        HttpGet request = new HttpGet(String.format("%s/%s", baseUrl, "getUpdates"));
        URI uri = new URIBuilder(request.getURI()).build();
        request.setURI(uri);
        return callApi(request);
    }

    // This method sends message to a specific chat
    private static void sendMessage(String message, String chatId) throws IOException, URISyntaxException {
        HttpGet request = new HttpGet(String.format("%s/%s", baseUrl, "sendMessage"));
        URI uri = new URIBuilder(request.getURI())
                .addParameter("text",message)
                .addParameter("chat_id",chatId)
                .build();
        request.setURI(uri);
        callApi(request);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {

        String chatId = "1208420970";
        sendMessage("test",chatId);

        JsonObject rv = getUpdates();
        System.out.println(rv);

    }

}