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
    private Integer id;

    @Column(name = "UUID")
    private String uuid;

    @Column(name = "PESSOA_ID")
    private Integer pessoaId;

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
    private Integer seloId;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Integer pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSensibilizado() {
        return sensibilizado;
    }

    public void setSensibilizado(Integer sensibilizado) {
        this.sensibilizado = sensibilizado;
    }

    public Integer getSeloId() {
        return seloId;
    }

    public void setSeloId(Integer seloId) {
        this.seloId = seloId;
    }

}
