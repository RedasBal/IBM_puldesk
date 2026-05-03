package Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;;

@Service
public class HuggingFaceService {

    @Value("${huggingface.api.token}")
    private String apiToken;

    public String analyze(String commentText){
        try{
            String prompt = "Analyze this user comment. Reply in this exact format: " +
                    "yes | title | category | priority | summary " +
                    "OR just: no " +
                    "Categories: bug, feature, billing, account, other. " +
                    "Priority: low, medium, high. " +
                    "Only create a ticket if it is a real issue or request. " +
                    "Comment: " + commentText;
            String requestBody = "{"
                    + "\"model\": \"google/flan-t5-base\","
                    + "\"messages\": [{\"role\": \"user\", \"content\": \""
                    + prompt.replace("\"", "\\\"") + "\"}],"
                    + "\"max_tokens\": 200"
                    + "}";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://router.huggingface.co/v1/chat/completions"))
                    .header("Authorization", "Bearer " + apiToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response= client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
