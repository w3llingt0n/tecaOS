/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entidades;

/**
 *
 * @author rapwe
 */
public class Multa {

    public Multa(int id, String cliente, double valor, String status) {
        this.setId(id);
        this.setCliente(cliente);
        this.setValor(valor);
        this.setStatus(status);
    }

    public Multa(String id, String cliente, double valor, String status) {
        this.setId(id);
        this.setCliente(cliente);
        this.setValor(valor);
        this.setStatus(status);
    }

    private int id;
    private String cliente;
    private double valor;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
