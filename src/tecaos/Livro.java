/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecaos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author rapwe
 */
public class Livro {

    private final SimpleIntegerProperty isbn;
    private final SimpleStringProperty titulo;
    private final SimpleStringProperty autor;
    private final SimpleIntegerProperty edicao;
    private final SimpleStringProperty local;
    private final SimpleIntegerProperty disponivel;

    public int getIsbn() {
        return isbn.get();
    }

    public SimpleIntegerProperty isbnProperty() {
        return isbn;
    }

    public String getTitulo() {
        return titulo.get();
    }

    public SimpleStringProperty tituloProperty() {
        return titulo;
    }

    public String getAutor() {
        return autor.get();
    }

    public SimpleStringProperty autorProperty() {
        return autor;
    }

    public int getEdicao() {
        return edicao.get();
    }

    public SimpleIntegerProperty edicaoProperty() {
        return edicao;
    }

    public String getLocal() {
        return local.get();
    }

    public SimpleStringProperty localProperty() {
        return local;
    }

    public Integer getDisponivel() {
        return disponivel.get();
    }

    public SimpleIntegerProperty disponivelProperty() {
        return disponivel;
    }

    public void setISBN(int isbn) {
        this.isbn.set(isbn);
    }

    public void setTitulo(String titulo) {
        this.titulo.set(titulo);
    }

    public void setAutor(String autor) {
        this.autor.set(autor);
    }

    public void setEdicao(int edicao) {
        this.edicao.set(edicao);
    }

    public void setLocal(String local) {
        this.local.set(local);
    }

    public void setDisponivel(int paginas) {
        this.disponivel.set(paginas);
    }

    public Livro(int isbn, String titulo, String autor, int edicao, String local, Integer disponivel) {
        this.isbn = new SimpleIntegerProperty(isbn);
        this.titulo = new SimpleStringProperty(titulo);
        this.autor = new SimpleStringProperty(autor);
        this.edicao = new SimpleIntegerProperty(edicao);
        this.local = new SimpleStringProperty(local);
        this.disponivel = new SimpleIntegerProperty(disponivel);
    }

}
