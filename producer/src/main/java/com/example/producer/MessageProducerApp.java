package com.example.producer;

import com.example.producer.service.DataFetcher;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;



@Component
public class MessageProducerApp implements CommandLineRunner {

    @Autowired
    private DataFetcher dataFetcher;

    @Override
    public void run(String... args) throws Exception {

        String data = dataFetcher.fetchData();

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://activemq:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("TEST.FOO");

        MessageProducer producer = session.createProducer(queue);
        TextMessage message = session.createTextMessage(data);

        producer.send(message);

        System.out.println("Sent message: " + message.getText());

        producer.close();
        session.close();
        connection.close();
    }
}

