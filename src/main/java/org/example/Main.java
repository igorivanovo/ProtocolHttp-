package org.example;

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

    public static final String REMOTE_SERVIS_URI = "https://jsonplaceholder.typicode.com/posts";

    public static void main(String[] args) throws IOException {
//        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
//        CloseableHttpResponse response = httpClient.execute(request);

        // нужно чтото отправить- стартовая строка- куда обращаемсяб определяет тип сообщения Starting line
        // заголовки - характеризует тело сообщения, параметры передачи и прочие характеристики  Headers
        //         Message Body тело сообщения, отделяется от заголовков пустой строкой
        //стартовая строка GET -метод  /index.html -путь к ресурсу(url)  HTTP/1.1 -версия протокола
        //
        //обязательным заголовок HOST-куда отправляется запрос

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                //     .setUserAgent("my test servis")
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000) //МАКС. ОЖИДАНИЕ ПОДКЛЮЧЕНИЯ К СЕРВЕРУ
                        .setSocketTimeout(30000) //максю ожидание получения данных
                        .setRedirectsEnabled(false)  //возможность следовать редиректу в ответе - не хотим при 300 статусе
                        .build())
                .build(); //  в ручную формируем клиент , даем имя

        HttpGet request = new HttpGet(REMOTE_SERVIS_URI);  // формируем запрос GET -запрос
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        // указываем хедер- входит в тип запроса json
        //  отравка запроса
        CloseableHttpResponse response = httpClient.execute(request); // клиент передали в запрос

        //вывод полученных заголовков
        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

        // чтение тела ответа
        String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println(body); // контент от удаленного сервера


        httpClient.close();

        // readAnswer(body);// читаем body по классу Post



    }

//    static void readAnswer(String body) {
//        ObjectMapper mapper = new ObjectMapper();
//    //    List<Post> posts =mapper.readValue(body, new TypeReference<List<Post>>() {
//        });
//     //   posts.forEach(System.out::println);
//    }
//}
}