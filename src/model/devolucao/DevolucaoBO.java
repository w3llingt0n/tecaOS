/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.devolucao;

import exception.ClienteInexistenteException;
import exception.EmprestimoInexistenteException;
import exception.RegistroExistenteException;
import exception.ValorInvalidoException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.entidades.Devolucao;

/**
 *
 * @author rapwe
 */
public class DevolucaoBO {
    
    private DevolucaoDAO dao;
    
    public DevolucaoBO(){
        dao = new DevolucaoDAO();
    }
    
    public void salvar(Devolucao d) throws SQLException, RegistroExistenteException, ClienteInexistenteException{
        if(dao.buscarPeloId(d.getId()) != null){
            throw new RegistroExistenteException("Já existe devolução cadastrada com esse id.");
        }
        else{
            dao.salvar(d);
        }
    }
    
    public ArrayList<Devolucao> listar() throws SQLException{
        return dao.listar();
    }
    public void excluir(Devolucao d) throws SQLException, ClienteInexistenteException {
        dao.excluir(d);
    }

    public void editar(Devolucao d) throws SQLException, RegistroExistenteException, ClienteInexistenteException{
        Devolucao temp = dao.buscarPeloId(d.getId());
        if(temp != null && temp.getId() != d.getId()){
            throw new RegistroExistenteException("Já existe devolução cadastrada com esse id.");
        }else{
            dao.editar(d);
        }
    }
    public ArrayList<Devolucao> filtrarPeloID(String id) throws SQLException, ValorInvalidoException{
        return dao.filtrarPeloID(id);
    }
    public ArrayList<Devolucao> filtrarPeloCliente(String cpf) throws SQLException, ValorInvalidoException{
        return dao.filtrarPeloCliente(cpf);
    }
    public ArrayList<Devolucao> filtrarPeloLivro(String id) throws SQLException, ValorInvalidoException{
        return dao.filtrarPeloLivro(id);
    }
    public int buscarEmprestimo(String cliente,String livro) throws SQLException, EmprestimoInexistenteException{
        int temp = dao.buscarEmprestimo(cliente, Integer.parseInt(livro));
        if(temp == -1){
            throw new EmprestimoInexistenteException("Não existe cliente ou livro cadastrados com esse empréstimo.");
        }
        return temp;
    }

    public LocalDate buscarDataDevolucaoPrevista(int id) throws EmprestimoInexistenteException, SQLException {
        LocalDate temp = dao.buscarDataDevolucaoPrevista(id);
        if(temp == null){
            throw new EmprestimoInexistenteException("Não existe cliente ou livro cadastrados com esse empréstimo.");
        }
        return temp;
    }
}
