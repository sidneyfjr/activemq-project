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
import java.util.ArrayList;

public class MessageConsumerApp {

    public static void main(String[] args) {
        
        try {

            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://activemq:61616");
            Connection connection             = factory.createConnection();
            connection.start();

            Session         session       = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination     destination   = session.createQueue("TEST.FOO");
            MessageConsumer consumer      = session.createConsumer(destination);
            TextMessage     message       = (TextMessage) consumer.receive();
            String          jsonMessage   = ((TextMessage) message).getText();

            ObjectMapper    usuariosMapper      = new ObjectMapper();
            List<Map<String, Object>> usuarios  = usuariosMapper.readValue(jsonMessage, new TypeReference<List<Map<String, Object>>>() {});  
            List<Map<String, Object>> rows      = new ArrayList<>();
            Integer sensibilizado;

            for (Map<String, Object> usuario : usuarios) {
                
                // String  cpf             = usuario.get("USUARIO").toString();
                // Integer usuarioId       = (Integer) usuario.get("USUARIO_SSO_ID");

                Integer usuarioId       = (Integer) usuario.get("USUARIO_SSO_ID");
                String  uuid            = usuario.get("UUID").toString();
                Integer pessoaId        = (Integer) usuario.get("PESSOA_ID");
                String  nome            = usuario.get("NOME").toString();
                String  usuario1        = usuario.get("USUARIO").toString();
                String  nomeCompleto    = usuario.get("NOME_COMPLETO").toString();
                String  email           = usuario.get("EMAIL").toString();
                Integer seloId          = (Integer) usuario.get("SELO_ID");

                Map<String, Object> row = new HashMap<>();

                System.out.println("Usuário: " + usuario1);

                if (!isValidCpf(usuario1)) {
                    sensibilizado    =   3;
                } else {
                    sensibilizado    =   2;
                }

                row.put("USUARIO_SSO_ID", usuarioId);
                row.put("UUID", uuid);
                row.put("PESSOA_ID", pessoaId);
                row.put("NOME", nome);
                row.put("USUARIO", usuario1);
                row.put("NOME_COMPLETO", nomeCompleto);
                row.put("EMAIL", email);
                row.put("SENSIBILIZADO", sensibilizado);
                row.put("SELO_ID", seloId);

                rows.add(row);

            }
            
            ObjectMapper rowsMapper     = new ObjectMapper();
            String       responseJson   = "";

            responseJson = rowsMapper.writeValueAsString(rows);

            sendResponseMessage2(session, responseJson);

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

    private static void sendResponseMessage2(Session session, String usuarios) throws JMSException {

        Queue           responseQueue   = session.createQueue("RESPONSE.FOO");
        MessageProducer producer        = session.createProducer(responseQueue);        
        TextMessage     responseMsg     = session.createTextMessage(usuarios);

        producer.send(responseMsg);

        System.out.println("Sent response message: " + responseMsg.getText());

        producer.close();

    }


}
