/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.multas;

import exception.ClienteInexistenteException;
import exception.RegistroExistenteException;
import exception.ValorInvalidoException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import model.entidades.Multa;

/**
 *
 * @author rapwe
 */
public class MultasBO {

    private MultasDAO dao;

    public MultasBO() {
        dao = new MultasDAO();
    }

    public void salvar(Multa m) throws SQLException, RegistroExistenteException, ClienteInexistenteException {
        if (dao.buscarPeloId(m.getId()) != null) {
            throw new RegistroExistenteException("Já existe multa cadastrada com esse id.");
        } else {
            dao.salvar(m);
        }
    }

    public ArrayList<Multa> listar() throws SQLException, ClienteInexistenteException {
        return dao.listar();
    }

    public void excluir(Multa m) throws SQLException, ClienteInexistenteException {
        dao.excluir(m);
    }

    public void editar(Multa m) throws SQLException, RegistroExistenteException, ClienteInexistenteException {
        Multa temp = dao.buscarPeloId(m.getId());
        if (temp != null && temp.getId() != m.getId()) {
            throw new RegistroExistenteException("Já existe multa cadastrada com esse id.");
        } else {
            dao.editar(m);
        }
    }

    public ArrayList<Multa> filtrarPeloID(String id) throws SQLException, ValorInvalidoException, ClienteInexistenteException {
        return dao.filtrarPeloID(id);
    }

    public ArrayList<Multa> filtrarPeloCliente(String cpf) throws SQLException, ValorInvalidoException, ClienteInexistenteException {
        return dao.filtrarPeloCliente(cpf);
    }

    public ObservableList<String> listarClientes() throws SQLException {
        return dao.listarClientes();
    }
}
