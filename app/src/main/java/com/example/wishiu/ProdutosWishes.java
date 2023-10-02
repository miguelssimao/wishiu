package com.example.wishiu;

public class ProdutosWishes {

    private byte[] imagem;
    private int idPw;
    private String preco,titulo,categoria;

    public ProdutosWishes(byte[] imagem, String preco, String titulo, String categoria, int idPw) {
        this.imagem = imagem;
        this.preco = preco;
        this.titulo = titulo;
        this.categoria = categoria;
        this.idPw = idPw;
    }

    public int getIdPw(){ return idPw;}

    public void setIdPw(int idPw){ this.idPw = idPw; }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
