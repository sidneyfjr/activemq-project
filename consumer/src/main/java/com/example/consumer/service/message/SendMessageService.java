package com.example.consumer.service.message;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;


public class SendMessageService {

    private final String brokerUrl = "tcp://activemq:61616";
    private final String queueName = "RESPONSE.FOO";

    public void sendMessage(String data) throws JMSException { 

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
        Connection      connection  = null;
        Session         session     = null;
        MessageProducer producer    = null;

        try {

            connection = factory.createConnection();
            connection.start();

            session     = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            producer    = session.createProducer(queue);
            TextMessage message = session.createTextMessage(data);
            producer.send(message);
            System.out.println("Sent message: " + message.getText());

        } finally {
          
            if (producer != null) {
                try {
                    producer.close();
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