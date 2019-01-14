/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.emprestimos;

import exception.ClienteComPendenciasException;
import exception.RegistroExistenteException;
import exception.ValorInvalidoException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import model.entidades.Emprestimo;

/**
 *
 * @author rapwe
 */
public class EmprestimosBO {

    private EmprestimosDAO dao;

    public EmprestimosBO() {
        dao = new EmprestimosDAO();
    }

    public void salvar(Emprestimo e) throws RegistroExistenteException, SQLException, ClienteComPendenciasException {
        if (dao.buscarPeloId(e.getId()) != null) {
            throw new RegistroExistenteException("Já existe um empréstimo cadastrado com esse id.");
        }else if(dao.buscarPendecias(e.getCliente())){
            throw new ClienteComPendenciasException("Esse cliente possui empréstimos pendentes.");
        }
        else  {
            dao.salvar(e);
        }
    }

    public ArrayList<Emprestimo> listar() throws SQLException {
        return dao.listar();
    }

    public void excluir(Emprestimo e) throws SQLException {
        dao.excluir(e);
    }

    public void editar(Emprestimo e) throws SQLException, RegistroExistenteException {
        Emprestimo temp = dao.buscarPeloId(e.getId());
        if (temp != null && temp.getId() != e.getId()) {
            throw new RegistroExistenteException("Já existe empréstimo cadastrado com esse id.");
        } else {
            dao.editar(e);
        }
    }

    public ArrayList<Emprestimo> filtrarPeloID(String id) throws SQLException, ValorInvalidoException {
        return dao.filtrarPeloID(id);
    }

    public ArrayList<Emprestimo> filtrarPeloLivro(String livro) throws SQLException, ValorInvalidoException {
        return dao.filtrarPeloLivro(livro);
    }

    public ArrayList<Emprestimo> filtrarPeloCliente(String cliente) throws SQLException, ValorInvalidoException {
        return dao.filtrarPeloCliente(cliente);
    }

    public boolean podeEmprestar(int id) throws SQLException {
        return dao.podeEmprestar(id);
    }

    public ObservableList<String> listarClientes() throws SQLException {
        return dao.listarClientes();
    }

    public ObservableList<String> listarLivros() throws SQLException {
        return dao.listarLivros();
    }
}
