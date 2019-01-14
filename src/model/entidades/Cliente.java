package model.entidades;

import java.time.LocalDate;

/**
 *
 * @author Wellington
 */
public class Cliente {

    public Cliente(String id,String nome, String cpf, String rg, LocalDate dataNasc, String email) {
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setRg(rg);
        this.setDataNasc(dataNasc);
        this.setEmail(email);
    }
    
    public Cliente(int id, String nome, String cpf, String rg, LocalDate dataNasc, String email) {
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setRg(rg);
        this.setDataNasc(dataNasc);
        this.setEmail(email);
    }
    
    private int id;
    private String nome;
    private String cpf;
    private String rg;
    private LocalDate dataNasc;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setId(String id){
        this.id = Integer.parseInt(id);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
