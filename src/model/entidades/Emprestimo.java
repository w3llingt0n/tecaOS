/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entidades;

import java.time.LocalDate;

/**
 *
 * @author Wellington
 */
public class Emprestimo {

    public Emprestimo(String id, String livro, String cliente, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.setId(id);
        this.setLivro(livro);
        this.setCliente(cliente);
        this.setDataDevolucao(dataDevolucao);
        this.setDataEmprestimo(dataEmprestimo);
    }

    public Emprestimo(int id, String livro, String cliente, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.setId(id);
        this.setLivro(livro);
        this.setCliente(cliente);
        this.setDataDevolucao(dataDevolucao);
        this.setDataEmprestimo(dataEmprestimo);
    }

    private int id;
    private String livro;
    private String cliente;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLivro() {
        return livro;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
