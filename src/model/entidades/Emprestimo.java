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

    public Emprestimo(Livro livro, Cliente cliente, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.setLivro(livro);
        this.setCliente(cliente);
        this.setDataDevolucao(dataDevolucao);
        this.setDataEmprestimo(dataEmprestimo);
    }
    
    private Livro livro;
    private Cliente cliente;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
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
