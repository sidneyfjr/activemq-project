package com.example.producer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USUARIO_SSO_TESTE")

public class UsuarioSSO {

    @Id
    @Column(name = "USUARIO_SSO_ID")
    private Long id;

    @Column(name = "UUID")
    private String uuid;

    @Column(name = "PESSOA_ID")
    private Long pessoaId;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "USUARIO")
    private String usuario;

    @Column(name = "NOME_COMPLETO")
    private String nomeCompleto;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SENSIBILIZADO")
    private Integer sensibilizado;

    @Column(name = "SELO_ID")
    private Long seloId;

    @Column(name = "USUARIO_ATIVO_SSO")
    private Boolean usuarioAtivoSso;

    @Column(name = "EMAIL_VERIFICADO")
    private Boolean emailVerificado;

    @Column(name = "IMPORTADO")
    private Boolean importado;

    @Column(name = "SENHA")
    private String senha;

    @Column(name = "DT_INCLUSAO")
    private LocalDateTime dtInclusao;

    @Column(name = "DT_ALTERACAO")
    private LocalDateTime dtAlteracao;

    public Long getId() {
        return id;
    }
    public String getUuid() {
        return uuid;
    }
    public Long getPessoaId() {
        return pessoaId;
    }

    public String getNome() {
        return nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public Integer getSensibilizado() {
        return sensibilizado;
    }

    public void setSensibilizado(Integer sensibilizado) {
        this.sensibilizado = sensibilizado;
    }

    public Long getSeloId() {
        return seloId;
    }




}
