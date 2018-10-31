package model.entidades;

import java.time.Year;

/**
 *
 * @author Wellington
 */
public class Livro {

    public Livro(String isbn, String nome, Year lancamento, String autor, String categoria) {
        this.setIsbn(isbn);
        this.setNome(nome);
        this.setLancamento(lancamento);
        this.setAutor(autor);
        this.setCategoria(categoria);
    }

    public Livro(String isbn, String nome, Year lancamento, String autor, String categoria, int quantidade) {
        this(isbn, nome, lancamento, autor, categoria);
        this.setQuantidade(quantidade);
    }

    private String isbn;
    private String nome;
    private Year lancamento;
    private String autor;
    private String categoria;
    private int quantidade;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Year getLancamento() {
        return lancamento;
    }

    public void setLancamento(Year lancamento) {
        this.lancamento = lancamento;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
