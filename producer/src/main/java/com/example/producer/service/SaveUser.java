package com.example.producer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.producer.entity.UsuarioSSO;
import java.util.List;
import java.util.Map;
import com.example.producer.repository.UsuarioSSORepository;
import org.springframework.beans.factory.annotation.Autowired;
import javax.jms.JMSException;
import org.springframework.stereotype.Service;

@Service
public class SaveUser {

    @Autowired
    private UsuarioSSORepository usuarioSSORepository;

    public void saveUser(String data) throws JMSException {

        try {

        ObjectMapper    usuariosMapper      = new ObjectMapper();
        List<Map<String, Object>> usuarios  = usuariosMapper.readValue(data, new TypeReference<List<Map<String, Object>>>() {});             

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

    } catch (Exception e){
        e.printStackTrace();
    }

    }

}