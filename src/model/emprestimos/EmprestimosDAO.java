/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.emprestimos;

import exception.ValorInvalidoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.conexao.ConnectionFactory;
import model.entidades.Emprestimo;

/**
 *
 * @author rapwe
 */
public class EmprestimosDAO {

    ArrayList<Emprestimo> listar() throws SQLException {
        String sql = "select * from emprestimo";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ResultSet resultado = ps.executeQuery();
        ArrayList<Emprestimo> lista = new ArrayList<>();
        while (resultado.next()) {
            Emprestimo e = new Emprestimo(
                    resultado.getInt("idemprestimo"),
                    buscarLivro(resultado.getInt("livro_id")),
                    buscarCliente(resultado.getInt("cliente_id")),
                    LocalDate.parse(resultado.getDate("data_emprestimo").toString()),
                    LocalDate.parse(resultado.getDate("data_prevista_devolucao").toString())
            );
            lista.add(e);
        }
        return lista;
    }

    ObservableList<String> listarClientes() throws SQLException {
        String sql = "select nome from cliente";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ResultSet resultado = ps.executeQuery();
        ObservableList<String> temp = FXCollections.observableArrayList();
        while (resultado.next()) {
            temp.add(resultado.getString("nome"));
        }
        return temp;
    }

    ObservableList<String> listarLivros() throws SQLException {
        String sql = "select titulo from livro";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ResultSet resultado = ps.executeQuery();
        ObservableList<String> temp = FXCollections.observableArrayList();
        while (resultado.next()) {
            temp.add(resultado.getString("titulo"));
        }
        return temp;
    }

    void excluir(Emprestimo e) throws SQLException {
        String sql = "delete from emprestimo where idemprestimo = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, e.getId());
        ps.executeUpdate();
        ps.close();
    }

    Emprestimo buscarPeloId(int id) throws SQLException {
        String sql = "select * from emprestimo where idemprestimo = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, id);
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            Emprestimo e = new Emprestimo(
                    resultado.getInt("idemprestimo"),
                    buscarLivro(resultado.getInt("livro_id")),
                    buscarCliente(resultado.getInt("cliente_id")),
                    LocalDate.parse(resultado.getDate("data_emprestimo").toString()),
                    LocalDate.parse(resultado.getDate("data_prevista_devolucao").toString())
            );
            return e;
        }
        return null;
    }

    void editar(Emprestimo e) throws SQLException {
        String sql = "Update emprestimo set cliente_id = ?,livro_id = ? where idemprestimo = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, buscarCliente(e.getCliente()));
        ps.setInt(2, buscarLivro(e.getLivro()));
        ps.setInt(6, e.getId());
        ps.executeUpdate();
        ps.close();
    }

    void salvar(Emprestimo e) throws SQLException {
        String sql = "insert into emprestimo (cliente_id,livro_id,data_emprestimo,data_prevista_devolucao) value (?,?,?,?)";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, buscarCliente(e.getCliente()));
        ps.setInt(2, buscarLivro(e.getLivro()));
        ps.setString(3, e.getDataEmprestimo().toString());
        ps.setString(4, e.getDataDevolucao().toString());
        ps.executeUpdate();
        ps.close();
    }

    ArrayList<Emprestimo> filtrarPeloID(String id) throws SQLException, ValorInvalidoException {
        String sql = "select * from emprestimo where idemprestimo like ?";
        return filtrar(id, sql);
    }

    ArrayList<Emprestimo> filtrarPeloLivro(String livro) throws SQLException, ValorInvalidoException {
        String sql = "select e.* from emprestimo e,livro l where l.titulo like ? and e.livro_id = l.id";
        return filtrar(livro, sql);
    }

    ArrayList<Emprestimo> filtrarPeloCliente(String cliente) throws SQLException, ValorInvalidoException {
        String sql = "select e.* from emprestimo e,cliente c where c.id = e.nome like ? and e.cliente_id = c.id";
        return filtrar(cliente, sql);
    }

    private ArrayList<Emprestimo> filtrar(String pesquisar, String sql) throws SQLException, ValorInvalidoException {
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setString(1, "%" + pesquisar + "%");
        ResultSet resultado = ps.executeQuery();
        ArrayList<Emprestimo> lista = new ArrayList<>();
        while (resultado.next()) {
            Emprestimo e = new Emprestimo(
                    resultado.getInt("idemprestimo"),
                    buscarLivro(resultado.getInt("livro_id")),
                    buscarCliente(resultado.getInt("cliente_id")),
                    LocalDate.parse(resultado.getDate("data_emprestimo").toString()),
                    LocalDate.parse(resultado.getDate("data_prevista_devolucao").toString())
            );
            lista.add(e);
        }
        return lista;
    }

    boolean podeEmprestar(int id) throws SQLException {
        String sql = "select count(e.idemprestimo) as emprestados,l.quantidade as total from emprestimo e, livro l where l.id = e.livro_id and e.livro_id = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, id);
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            if (resultado.getInt("emprestados") < resultado.getInt("total")) {
                return true;
            }
        }
        return false;
    }

    int buscarLivro(String titulo) throws SQLException {
        String sql = "select id from livro where titulo like ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setString(1, titulo);
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            return resultado.getInt("id");
        }
        return -1;
    }

    int buscarCliente(String nome) throws SQLException {
        String sql = "select id from cliente where nome like ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setString(1, nome);
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            return resultado.getInt("id");
        }
        return -1;
    }

    String buscarLivro(int id) throws SQLException {
        String sql = "select titulo from livro where id = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, id);
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            return resultado.getString("titulo");
        }
        return null;
    }

    String buscarCliente(int id) throws SQLException {
        String sql = "select nome from cliente where id = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, id);
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            return resultado.getString("nome");
        }
        return null;
    }

    boolean buscarPendecias(String nome) throws SQLException {
        String sql = "select m.pago,d.iddevolucao,e.idemprestimo from emprestimo e,devolucao d,multa m where "
                + "d.emprestimo_cliente_id = ? and d.emprestimo_cliente_id = m.cliente_id and "
                + "e.idemprestimo = d.emprestimo_idemprestimo";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, buscarCliente(nome));
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            if (resultado.getBoolean("pago")) {
                return false;
            } 
        }
        return true;
    }
}
