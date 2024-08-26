package com.example.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.JMSException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MessageConsumerApp {

    public static void main(String[] args) {
        
        try {

            // Cria uma conexão com o ActiveMQ
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://activemq:61616");
            Connection connection             = factory.createConnection();
            connection.start();

            // Cria uma sessão sem transações
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Define a fila de destino
            Destination destination = session.createQueue("TEST.FOO");

            // Cria um consumidor de mensagens
            MessageConsumer consumer = session.createConsumer(destination);

            // Recebe a mensagem
            TextMessage message = (TextMessage) consumer.receive();
            String jsonMessage = ((TextMessage) message).getText();

            ObjectMapper objectMapper           = new ObjectMapper();
            List<Map<String, Object>> usuarios  = objectMapper.readValue(jsonMessage, new TypeReference<List<Map<String, Object>>>() {});
            int resposta;

            for (Map<String, Object> usuario : usuarios) {

                String  cpf       = usuario.get("USUARIO").toString();
                Integer usuarioId = (Integer) usuario.get("USUARIO_SSO_ID");

                System.out.println("Usuário: " + cpf);

                if (!isValidCpf(cpf)) {
                    resposta    =   3;
                } else {
                    resposta    =   2;
                }
                
                sendResponseMessage(session, usuarioId, cpf, resposta);
            }

            System.out.println("Mensagem recebida: " + message.getText());

            session.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static boolean isValidCpf(String cpf) {              
        return cpf != null && !cpf.equals("63717672088");
    }

    private static void sendResponseMessage(Session session, Integer usuarioId, String cpf, int resposta) throws JMSException {

        Queue           responseQueue       = session.createQueue("RESPONSE.FOO");
        MessageProducer producer            = session.createProducer(responseQueue);
        String          responseJson        = "";

        // String responseJson = String.format("{\"USUARIO_SSO_ID\": %d, \"USUARIO\": \"%s\", \"RESPOSTA\": %d}", usuarioId, cpf, resposta);
        
        try {
            responseJson = createResponseJson(usuarioId, cpf, resposta);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextMessage responseMsg = session.createTextMessage(responseJson);
        producer.send(responseMsg);

        System.out.println("Sent response message: " + responseMsg.getText());

        producer.close();

    }

    private static String createResponseJson(Integer usuarioId, String cpf, int resposta) throws Exception {

        Map<String, Object> responseMap = new HashMap<>();

        responseMap.put("USUARIO_SSO_ID", usuarioId);
        responseMap.put("USUARIO", cpf);
        responseMap.put("RESPOSTA", resposta);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(responseMap);

    }

}
