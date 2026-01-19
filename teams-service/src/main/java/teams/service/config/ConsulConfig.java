package teams.service.config;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.Properties;
import java.util.Random;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;

@Component
public class ConsulConfig {

    private String address = "";
    private String port = "";
    String consulUrl = "http://localhost:8500/v1/agent/service/register";

    @PostConstruct
    public void init() throws SocketException {
        // readProperties();
        System.out.println("HERE: " + address + " " + port);
        Random rand = new Random();
        WebClient webClient = WebClient.builder()
                .baseUrl(consulUrl)
                .build();
        webClient.put()
                .uri(consulUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(
                        "{\"ID\": \"" + rand.nextInt(1000) + "\", \r\n" +
                                "    \"Name\": \"teams\",\r\n" +
                                "    \"Address\": \"" + "172.20.0.1" + "\",\r\n" + // nvm
                                "    \"Port\": " + "18066" + "\n}")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    private void readProperties() {
        Properties prop = new Properties();
        String fileName = "./config/service.properties";

        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
            address = prop.getProperty("server.address");
            port = prop.getProperty("server.port");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
