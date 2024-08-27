package com.example.producer.service;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
public class MessageReceiveService {

    private final String brokerUrl = "tcp://activemq:61616";
    private final String queueName = "RESPONSE.FOO";

    public void receiveMessage() throws JMSException {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
        Connection      connection  = null; 
        Session         session     = null;
        Destination     destination = null;
        MessageConsumer consumer    = null;

        try {

            connection = factory.createConnection();
            connection.start();

            session     = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(queueName);
            consumer    = session.createConsumer(destination);

            TextMessage message     = (TextMessage) consumer.receive();
            String      jsonMessage = ((TextMessage) message).getText();

            System.out.println("Mensagem recebida: " + message.getText());

        } finally {

            if (consumer != null) {
                try {
                    consumer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
