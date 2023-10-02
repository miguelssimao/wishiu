package com.example.wishiu;

public class ProdutosSavings {

    private int circulo;
    private String mensagem, data;

    public ProdutosSavings(int circulo, String mensagem, String data) {
        this.circulo = circulo;
        this.mensagem = mensagem;
        this.data = data;
    }

    public int getCirculo() {
        return circulo;
    }

    public void setCirculo(int circulo) {
        this.circulo = circulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
