package com.example.producer.repository;

import com.example.producer.entity.UsuarioSSO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface UsuarioSSORepository extends JpaRepository<UsuarioSSO, Long> {

    List<UsuarioSSO> findBySensibilizado(Integer sensibilizado);

    // @Modifying
    // @Transactional
    // @Query("UPDATE UsuarioSSO u SET u.sensibilizado = :resposta WHERE u.id = :usuarioSsoId")
    // void updateSensibilizado(Long usuarioSsoId, Integer resposta);

}

