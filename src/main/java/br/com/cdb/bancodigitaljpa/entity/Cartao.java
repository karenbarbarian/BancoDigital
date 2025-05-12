package br.com.cdb.bancodigitaljpa.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Cartao {

    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private Conta conta;


        private String tipo; // "CREDITO" ou "DEBITO"
        private BigDecimal limite;
        private BigDecimal limiteDiario;
        private BigDecimal fatura = BigDecimal.ZERO;

        private String senha;
        private boolean ativo = true;


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Conta getConta() {
            return conta;
        }

        public void setConta(Conta conta) {
            this.conta = conta;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public BigDecimal getLimite() {
            return limite;
        }

        public void setLimite(BigDecimal limite) {
            this.limite = limite;
        }

        public BigDecimal getLimiteDiario() {
            return limiteDiario;
        }

        public void setLimiteDiario(BigDecimal limiteDiario) {
            this.limiteDiario = limiteDiario;
        }

        public BigDecimal getFatura() {
            return fatura;
        }

        public void setFatura(BigDecimal fatura) {
            this.fatura = fatura;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public boolean isAtivo() {
            return ativo;
        }

        public void setAtivo(boolean ativo) {
            this.ativo = ativo;
        }
    }


