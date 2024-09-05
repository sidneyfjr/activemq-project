package com.example.producer.service;


public class HaveDataToSend {

    public static Boolean send(String data) {

        if (data == null || data.isEmpty() || "[]".equals(data.trim())) {
            System.out.println("Sem dados para enviar!");
            return false;
        } 

        return true;
       
    }
}