package client;

import client.dto.Request;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.function.Consumer;

public class ClientMain {

    public static void main(String[] args) {

        if (args.length == 0)
            throw new RuntimeException("Print message");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientConfig.class);

        Consumer starter = context.getBean(Consumer.class);

        Request request = context.getBean(Request.class);
        request.setMessage(args[0]);

        starter.accept(args[0]);
    }
}
