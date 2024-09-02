package com.example.producer.service;

import com.example.producer.entity.UsuarioSSO;
import com.example.producer.repository.UsuarioSSORepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataFetcher {

    @Autowired
    private UsuarioSSORepository usuarioSSORepository;

    public String fetchData() {

        List<UsuarioSSO> usuarios = usuarioSSORepository.findBySensibilizado(0);
        List<Map<String, Object>> rows         = new ArrayList<>();
        ObjectMapper              objectMapper = new ObjectMapper();
        String                    jsonData     = "";

        try {

            for (UsuarioSSO usuario : usuarios) {

                     usuario.setSensibilizado(1);
                     usuarioSSORepository.save(usuario);

                     Map<String, Object> row = new HashMap<>();
                     row.put("USUARIO_SSO_ID", usuario.getId());
                     row.put("UUID", usuario.getUuid());
                     row.put("PESSOA_ID", usuario.getPessoaId());
                     row.put("NOME", usuario.getNome());
                     row.put("USUARIO", usuario.getUsuario());
                     row.put("NOME_COMPLETO", usuario.getNomeCompleto());
                     row.put("EMAIL", usuario.getEmail());
                     row.put("SENSIBILIZADO", usuario.getSensibilizado());
                     row.put("SELO_ID", usuario.getSeloId());
                     rows.add(row);    

               }
       
               jsonData = objectMapper.writeValueAsString(rows);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonData;
    }
}


// package com.example.producer.service;

// import com.example.producer.entity.UsuarioSSO;
// import com.example.producer.repository.UsuarioSSORepository;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class DataFetcher {

//     @Autowired
//     private UsuarioSSORepository usuarioSSORepository;

//     @Autowired
//     private ObjectMapper objectMapper;

//     public String fetchData() throws Exception {

//         List<UsuarioSSO> usuarios = usuarioSSORepository.findAll();

//         return objectMapper.writeValueAsString(usuarios);

//     }
// }


// import com.example.producer.db.OracleConnectionManager;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import java.sql.Connection;
// import java.sql.ResultSet;
// import java.sql.Statement;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// public class DataFetcher {

//     public String fetchData() {

//         List<Map<String, Object>> rows         = new ArrayList<>();
//         ObjectMapper              objectMapper = new ObjectMapper();
//         String                    jsonData     = "";


//         try (Connection connection = OracleConnectionManager.getConnection();
//              Statement stmt = connection.createStatement()) {
            
//             String query = "SELECT * FROM TB_USUARIO_SSO_TESTE WHERE SENSIBILIZADO = 0";
//             ResultSet rs = stmt.executeQuery(query);
            
//              while (rs.next()) {

//                 Map<String, Object> row = new HashMap<>();
//                 row.put("USUARIO_SSO_ID", rs.getLong("USUARIO_SSO_ID"));
//                 row.put("UUID", rs.getString("UUID"));
//                 row.put("PESSOA_ID", rs.getLong("PESSOA_ID"));
//                 row.put("NOME", rs.getString("NOME"));
//                 row.put("USUARIO", rs.getString("USUARIO"));
//                 row.put("NOME_COMPLETO", rs.getString("NOME_COMPLETO"));
//                 row.put("EMAIL", rs.getString("EMAIL"));
//                 row.put("SENSIBILIZADO", rs.getInt("SENSIBILIZADO"));
//                 row.put("SELO_ID", rs.getLong("SELO_ID"));
//                 row.put("USUARIO_ATIVO_SSO", rs.getBoolean("USUARIO_ATIVO_SSO"));
//                 row.put("EMAIL_VERIFICADO", rs.getBoolean("EMAIL_VERIFICADO"));
//                 row.put("IMPORTADO", rs.getBoolean("IMPORTADO"));
//                 row.put("SENHA", rs.getString("SENHA"));

//                 // Verifica se o valor não é nulo antes de converter para LocalDateTime
//                 if (rs.getTimestamp("DT_INCLUSAO") != null) {
//                     row.put("DT_INCLUSAO", rs.getTimestamp("DT_INCLUSAO").toLocalDateTime());
//                 } else {
//                     row.put("DT_INCLUSAO", null); // Ou um valor padrão, se preferir
//                 }

//                 if (rs.getTimestamp("DT_ALTERACAO") != null) {
//                     row.put("DT_ALTERACAO", rs.getTimestamp("DT_ALTERACAO").toLocalDateTime());
//                 } else {
//                     row.put("DT_ALTERACAO", null); // Ou um valor padrão, se preferir
//                 }

//                 rows.add(row);
//             }

//              // Convertendo a lista de mapas para JSON
//              jsonData = objectMapper.writeValueAsString(rows);

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
       
//          return jsonData;
//     }
// }
