/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entidades;

import java.time.LocalDate;

/**
 *
 * @author rapwe
 */
public class Devolucao {

    public Devolucao(String id, int emprestimo, String cliente, int livro, LocalDate dataDevolucao, double multa) {
        this.setId(id);
        this.setEmprestimo(emprestimo);
        this.setCliente(cliente);
        this.setLivro(livro);
        this.setDataDevolucao(dataDevolucao);
        this.setMulta(multa);
    }

    public Devolucao(int id, int emprestimo, String cliente, int livro, LocalDate dataDevolucao, double multa) {
        this.setId(id);
        this.setEmprestimo(emprestimo);
        this.setCliente(cliente);
        this.setLivro(livro);
        this.setDataDevolucao(dataDevolucao);
        this.setMulta(multa);
    }

    private int id;
    private int emprestimo;
    private String cliente;
    private int livro;
    private LocalDate dataDevolucao;
    private double multa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public int getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(int emprestimo) {
        this.emprestimo = emprestimo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getLivro() {
        return livro;
    }

    public void setLivro(int livro) {
        this.livro = livro;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }
}
