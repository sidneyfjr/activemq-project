package com.example.consumer;

import com.example.consumer.service.message.SendMessageService;
import com.example.consumer.service.message.MessageReceivedService;
import com.example.consumer.service.SendUser;

public class MessageConsumerApp { 
    
    public static void main(String[] args) {        
        
        try {
            MessageConsumerApp messageConsumerApp = new MessageConsumerApp();
            messageConsumerApp.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void run() {
        while (true) {
            MessageReceivedService messageReceivedService = new MessageReceivedService(); 
            SendUser               usuario                = new SendUser(); 
            SendMessageService     sendMessageService     = new SendMessageService();
            try {
                String messageReceived  = messageReceivedService.messageReceived();
                String sendMessage      = usuario.sendUser(messageReceived);            
                sendMessageService.sendMessage(sendMessage);
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
