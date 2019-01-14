/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.clientes;

import exception.RegistroExistenteException;
import exception.ValorInvalidoException;
import java.sql.SQLException;
import java.util.ArrayList;
import model.entidades.Cliente;

/**
 *
 * @author rapwe
 */
public class ClientesBO {
    private ClientesDAO dao;
    
    public ClientesBO(){
        dao = new ClientesDAO();
    }
    
    public void salvar(Cliente c) throws RegistroExistenteException, SQLException{
        if(dao.buscarPeloId(c.getId()) != null){
            throw new RegistroExistenteException("Já existe cliente cadastrado com esse id.");
        }
        else{
            dao.salvar(c);
        }
    }
    
    public ArrayList<Cliente> listar() throws SQLException{
        return dao.listar();
    }
    public void excluir(Cliente c) throws SQLException {
        dao.excluir(c);
    }

    public void editar(Cliente c) throws SQLException, RegistroExistenteException {
        Cliente temp = dao.buscarPeloId(c.getId());
        if(temp != null && temp.getId() != c.getId()){
            throw new RegistroExistenteException("Já existe um produto cadastrado com esse id.");
        }else{
            dao.editar(c);
        }
    }
    public ArrayList<Cliente> filtrarPeloID(String id) throws SQLException, ValorInvalidoException{
        return dao.filtrarPeloID(id);
    }
    public ArrayList<Cliente> filtrarPeloNome(String nome) throws SQLException, ValorInvalidoException{
        return dao.filtrarPeloNome(nome);
    }
}
