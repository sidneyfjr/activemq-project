package com.example.producer;

import com.example.producer.service.DataFetcher;
import com.example.producer.service.message.SendMessageService;
import com.example.producer.service.message.MessageReceivedService;
import com.example.producer.service.SaveUser;
import com.example.producer.service.HaveDataToSend;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MessageProducerApp implements CommandLineRunner {

    private final DataFetcher dataFetcher;
    private final SendMessageService sendMessageService;
    private final MessageReceivedService messageReceivedService;
    private final SaveUser saveUser;

    public MessageProducerApp(
            DataFetcher dataFetcher, 
            SendMessageService  sendMessageService, 
            MessageReceivedService messageReceivedService,
            SaveUser saveUser
            ) {

        this.dataFetcher            = dataFetcher;
        this.sendMessageService     = sendMessageService;
        this.messageReceivedService = messageReceivedService; 
        this.saveUser               = saveUser; 

    }

    @Override
    public void run(String... args) {
        while (true) {
            try {
                String  data = dataFetcher.fetchData();
                Boolean send = HaveDataToSend.send(data);
                if (send) {
                    sendMessageService.sendMessage(data);
                    String messageReceived = messageReceivedService.messageReceived();
                    saveUser.saveUser(messageReceived);
                }
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
