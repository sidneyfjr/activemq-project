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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.producer.entity.UsuarioSSO;
import java.util.List;
import java.util.Map;
import com.example.producer.repository.UsuarioSSORepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MessageReceiveService {

    @Autowired
    private UsuarioSSORepository usuarioSSORepository;
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

            // Alterar tabela TB_USUARIO_SSO_TESTE
            ObjectMapper    usuariosMapper      = new ObjectMapper();
            List<Map<String, Object>> usuarios  = usuariosMapper.readValue(jsonMessage, new TypeReference<List<Map<String, Object>>>() {});             
            
            for (Map<String, Object> usuario : usuarios) {

                UsuarioSSO usuarioSSO = new UsuarioSSO();

                Integer usuarioId       = (Integer) usuario.get("USUARIO_SSO_ID");
                String  uuid            = usuario.get("UUID").toString();
                Integer pessoaId        = (Integer) usuario.get("PESSOA_ID");
                String  nome            = usuario.get("NOME").toString();
                String  usuario1        = usuario.get("USUARIO").toString();
                String  nomeCompleto    = usuario.get("NOME_COMPLETO").toString();
                String  email           = usuario.get("EMAIL").toString();
                Integer sensibilizado   = (Integer) usuario.get("SENSIBILIZADO");
                Integer seloId          = (Integer) usuario.get("SELO_ID");

                usuarioSSO.setId(usuarioId);
                usuarioSSO.setUuid(uuid);
                usuarioSSO.setPessoaId(pessoaId);
                usuarioSSO.setNome(nome);
                usuarioSSO.setUsuario(usuario1);
                usuarioSSO.setNomeCompleto(nomeCompleto);
                usuarioSSO.setEmail(email);
                usuarioSSO.setSensibilizado(sensibilizado);
                usuarioSSO.setSeloId(seloId);

                usuarioSSORepository.save(usuarioSSO);

            }
              
        } catch (Exception e) {
            e.printStackTrace();
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
