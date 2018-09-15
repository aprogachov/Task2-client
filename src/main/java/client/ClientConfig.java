package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import client.dto.Request;
import client.dto.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class ClientConfig {

    private static final int PORT = 3333;
    private static final String HOST = "localhost";

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Bean
    public String name() {
        return "Tom";
    }

    @Bean
    public Request request() {
        return new Request(name());
    }

    @Bean
    public Consumer<String> executor(ObjectMapper mapper, Request request) {
        return (m) -> {
            try {
                InetAddress hostAddress = InetAddress.getByName(HOST);
                System.out.println("Client with HOST: " + HOST );
                Socket socket = new Socket(hostAddress, PORT);

                // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
                // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
                DataInputStream reader = new DataInputStream(socket.getInputStream());
                DataOutputStream writer = new DataOutputStream(socket.getOutputStream());

                String requestString = mapper.writeValueAsString(request);

                writer.writeUTF(requestString);
                writer.flush();

                String responseString = reader.readUTF();
                Response response = mapper.readValue(responseString, Response.class);

                System.out.println("Response from server: " + response.getMessage());

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
