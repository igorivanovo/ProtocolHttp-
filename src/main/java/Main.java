import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;


public class Main {

    public static final String REMOTE_SERVIS_URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000) //МАКС. ОЖИДАНИЕ ПОДКЛЮЧЕНИЯ К СЕРВЕРУ
                        .setSocketTimeout(30000) //максю ожидание получения данных
                        .setRedirectsEnabled(false)  //возможность следовать редиректу в ответе - не хотим при 300 статусе
                        .build())
                .build(); //  в ручную формируем клиент , даем имя

        HttpGet request = new HttpGet(REMOTE_SERVIS_URI);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        CloseableHttpResponse response = httpClient.execute(request); // клиент передали в запрос
        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
        String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);

        httpClient.close();
        readAnswer(body);
    }

    static void readAnswer(String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Post> facts = mapper.readValue(body, new TypeReference<List<Post>>() {
        });
        facts.stream()
                .filter(fact -> fact.getUpvotes() != 0)
                .forEach(System.out::println);
    }
}