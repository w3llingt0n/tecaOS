/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.livros;

import exception.RegistroExistenteException;
import exception.ValorInvalidoException;
import java.sql.SQLException;
import java.util.ArrayList;
import model.entidades.Livro;

/**
 *
 * @author rapwe
 */
public class LivrosBO {
    private LivrosDAO dao;
    
    public LivrosBO(){
        dao = new LivrosDAO();
    }
    
    public void salvar(Livro l) throws RegistroExistenteException, SQLException{
        if(dao.buscarPeloId(l.getId()) != null){
            throw new RegistroExistenteException("Já existe um livro cadastrado com esse id.");
        }
        else{
            dao.salvar(l);
        }
    }
    
    public ArrayList<Livro> listar() throws SQLException{
        return dao.listar();
    }
    public void excluir(Livro l) throws SQLException {
        dao.excluir(l);
    }

    public void editar(Livro l) throws SQLException, RegistroExistenteException {
        Livro temp = dao.buscarPeloId(l.getId());
        if(temp != null && temp.getId() != l.getId()){
            throw new RegistroExistenteException("Já existe livro cadastrado com esse id.");
        }else{
            dao.editar(l);
        }
    }
    public ArrayList<Livro> filtrarPeloID(String id) throws SQLException, ValorInvalidoException{
        return dao.filtrarPeloID(id);
    }
    public ArrayList<Livro> filtrarPeloTitulo(String titulo) throws SQLException, ValorInvalidoException{
        return dao.filtrarPeloTitulo(titulo);
    }
    public ArrayList<Livro> filtrarPeloAutor(String autor) throws SQLException, ValorInvalidoException{
        return dao.filtrarPeloAutor(autor);
    }
    public ArrayList<Livro> filtrarPelaCategoria(String categoria) throws SQLException, ValorInvalidoException{
        return dao.filtrarPeloAutor(categoria);
    }
}
