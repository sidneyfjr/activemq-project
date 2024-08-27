package com.example.producer;

import com.example.producer.service.DataFetcher;
import com.example.producer.service.MessageSenderService;
import com.example.producer.service.MessageReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MessageProducerApp implements CommandLineRunner {

    private final DataFetcher dataFetcher;
    private final MessageSenderService messageSenderService;
    private final MessageReceiveService messageReceiveService;

    @Autowired
    public MessageProducerApp(
            DataFetcher dataFetcher, 
            MessageSenderService messageSenderService, 
            MessageReceiveService messageReceiveService) {

        this.dataFetcher            = dataFetcher;
        this.messageSenderService   = messageSenderService;
        this.messageReceiveService  = messageReceiveService;

    }

    @Override
    public void run(String... args) {
        try {
            String data = dataFetcher.fetchData();
            messageSenderService.sendMessage(data);
            messageReceiveService.receiveMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
