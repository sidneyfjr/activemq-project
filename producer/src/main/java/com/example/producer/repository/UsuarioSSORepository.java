package com.example.producer.repository;

import com.example.producer.entity.UsuarioSSO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UsuarioSSORepository extends JpaRepository<UsuarioSSO, Long> {
    // Métodos de consulta personalizados podem ser adicionados aqui, se necessário
}
