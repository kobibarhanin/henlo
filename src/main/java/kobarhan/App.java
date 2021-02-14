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
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    // This method sends picture to a specific chat
    private static void sendPicture(String picURL, String caption, String chatId) throws IOException, URISyntaxException {
        HttpGet request = new HttpGet(String.format("%s/%s", baseUrl, "sendPhoto"));
        URI uri = new URIBuilder(request.getURI())
                .addParameter("photo",picURL)
                .addParameter("chat_id",chatId)
                .addParameter("caption",caption)
                .build();
        request.setURI(uri);
        callApi(request);
    }

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        String pic ="https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=1.00xw:0.669xh;0,0.190xh&resize=1200:*";
        String chatId = "1208420970";
        sendPicture(pic,"am dogo",chatId);

//        String chatId = "1208420970";
//        sendMessage("test",chatId);

//        JsonObject rv = getUpdates();
//        System.out.println(rv);

//        float sum = 0;
//        for (int k=0; k<100; k++){
//            Random rand = new Random();
//            int i = 0;
//            while (true){
//                i++;
//                TimeUnit.MILLISECONDS.sleep(1);
//                int rand_int1 = rand.nextInt(20);
////                System.out.println(rand_int1);
//                if (rand_int1 == 14){
////                    System.out.println("took " + i);
//                    sum +=i;
//                    break;
//                }
//            }
//        }
//        System.out.println("average = " + sum/100);

//        while (true){
            // check for updates to support reply
            // if no reply needed generate random number
            // check if random number is a hit
            // (1/3600 for 1 sec. interval => 1 hit per hour)
            // if hit -> send random remark
//        }
    }

}

//    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//    builder.addTextBody("field1", "yes", ContentType.TEXT_PLAIN);
//
//// This attaches the file to the POST:

//        File f = new File("[/path/to/upload]");
//        builder.addBinaryBody(
//        "file",
//        new FileInputStream(f),
//        ContentType.APPLICATION_OCTET_STREAM,
//        f.getName()
//        );
//
//        HttpEntity multipart = builder.build();
//        uploadFile.setEntity(multipart);
//        CloseableHttpResponse response = httpClient.execute(uploadFile);
//        HttpEntity responseEntity = response.getEntity();