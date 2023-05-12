package br.com.fiap.models;

public class Assessor extends Usuario {
    public Assessor(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public Assessor(String nome, String email, String senha, String fotopath) {
        super(nome, email, senha, fotopath);
    }

}
