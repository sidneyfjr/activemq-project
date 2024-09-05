package com.example.consumer.service;

import javax.jms.JMSException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class SendUser {

    public String sendUser(String data) throws JMSException {

        try {

        ObjectMapper    usuariosMapper      = new ObjectMapper();
        List<Map<String, Object>> usuarios  = usuariosMapper.readValue(data, new TypeReference<List<Map<String, Object>>>() {});  
        List<Map<String, Object>> rows      = new ArrayList<>();
        Integer sensibilizado;

        for (Map<String, Object> usuario : usuarios) {                

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
        
        ObjectMapper rowsMapper  = new ObjectMapper();
        
        return rowsMapper.writeValueAsString(rows);

    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

    private static boolean isValidCpf(String cpf) {              
        return cpf != null && !cpf.equals("63717672088");
    }

}