/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.devolucao;

import exception.ClienteInexistenteException;
import exception.ValorInvalidoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.conexao.ConnectionFactory;
import model.entidades.Devolucao;

/**
 *
 * @author rapwe
 */
public class DevolucaoDAO {

    ArrayList<Devolucao> listar() throws SQLException {
        String sql = "select m.valor as multa,c.cpf as cpf,l.id as id_livro,d.* from multa m, devolucao d,cliente c,"
                + "livro l where c.id = m.cliente_id and c.id = d.emprestimo_cliente_id and l.id = d.emprestimo_livro_id";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ResultSet resultado = ps.executeQuery();
        ArrayList<Devolucao> lista = new ArrayList<>();
        while (resultado.next()) {
            Devolucao d = new Devolucao(
                    resultado.getInt("iddevolucao"),
                    resultado.getInt("emprestimo_idemprestimo"),
                    resultado.getString("cpf"),
                    resultado.getInt("id_livro"),
                    LocalDate.parse(resultado.getDate("data_devolucao").toString()),
                    resultado.getDouble("multa")
            );
            lista.add(d);
        }
        return lista;
    }

    void excluir(Devolucao d) throws SQLException, ClienteInexistenteException {
        excluirMulta(d);
        String sql = "delete from devolucao where iddevolucao = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, d.getId());
        ps.executeUpdate();
        ps.close();
    }

    Devolucao buscarPeloId(int id) throws SQLException {
        String sql = "select m.valor as multa,c.cpf,l.id as id_livro,d.* from multa m, devolucao d,cliente c,"
                + "livro l where c.id = m.cliente_id and c.id = d.emprestimo_cliente_id and l.id = d.emprestimo_livro_id"
                + " and d.iddevolucao = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, id);
        ResultSet resultado = ps.executeQuery();
        if(resultado.next()){
            Devolucao d = new Devolucao(
                    resultado.getInt("iddevolucao"),
                    resultado.getInt("emprestimo_idemprestimo"),
                    resultado.getString("cpf"),
                    resultado.getInt("id_livro"),
                    LocalDate.parse(resultado.getDate("data_devolucao").toString()),
                    resultado.getDouble("multa")
            );
        }
        return null;
    }

    void editar(Devolucao d) throws SQLException, ClienteInexistenteException {
        String sql = "Update devolucao set emprestimo_cliente_id = ?,emprestimo_livro_id = ? where iddevolucao = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, buscarCliente(d.getCliente()));
        ps.setInt(2, d.getLivro());
        ps.setInt(3, d.getId());
        ps.executeUpdate();
        ps.close();
    }

    void salvar(Devolucao d) throws SQLException, ClienteInexistenteException {
        String sql = "insert into devolucao (emprestimo_cliente_id,emprestimo_livro_id,emprestimo_idemprestimo,data_devolucao) value (?,?,?,?)";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, buscarCliente(d.getCliente()));
        ps.setInt(2, d.getLivro());
        ps.setInt(3, buscarEmprestimo(d.getCliente(), d.getLivro()));
        ps.setString(4, d.getDataDevolucao().toString());
        ps.executeUpdate();
        ps.close();
    }

    ArrayList<Devolucao> filtrarPeloID(String id) throws SQLException, ValorInvalidoException {
        String sql = "select m.valor as multa,c.cpf,l.id as id_livro,d.* from multa m, devolucao d,cliente c,"
                + "livro l where c.id = m.cliente_id and c.id = d.emprestimo_cliente_id and l.id = d.emprestimo_livro_id"
                + " and d.iddevolucao = ?";
        return filtrar(id, sql);
    }

    ArrayList<Devolucao> filtrarPeloLivro(String livro) throws SQLException, ValorInvalidoException {
        String sql = "select m.valor as multa,c.cpf,l.id as id_livro,d.* from multa m, devolucao d,cliente c,"
                + "livro l where c.id = m.cliente_id and c.id = d.emprestimo_cliente_id and l.id = d.emprestimo_livro_id"
                + " and l.id = ?";
        return filtrar(livro, sql);
    }

    ArrayList<Devolucao> filtrarPeloCliente(String cliente) throws SQLException, ValorInvalidoException {
        String sql = "select m.valor as multa,c.cpf,l.id as id_livro,d.* from multa m, devolucao d,cliente c,"
                + "livro l where c.id = m.cliente_id and c.id = d.emprestimo_cliente_id and l.id = d.emprestimo_livro_id"
                + " and c.cpf = ?";
        return filtrar(cliente, sql);
    }

    private ArrayList<Devolucao> filtrar(String pesquisar, String sql) throws SQLException, ValorInvalidoException {
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setString(1, "%" + pesquisar + "%");
        ResultSet resultado = ps.executeQuery();
        ArrayList<Devolucao> lista = new ArrayList<>();
        while (resultado.next()) {
            Devolucao d = new Devolucao(
                    resultado.getInt("iddevolucao"),
                    resultado.getInt("emprestimo_idemprestimo"),
                    resultado.getString("cpf"),
                    resultado.getInt("id_livro"),
                    LocalDate.parse(resultado.getDate("data_devolucao").toString()),
                    resultado.getDouble("multa")
            );
            lista.add(d);
        }
        return lista;
    }

    int buscarCliente(String cpf) throws SQLException, ClienteInexistenteException {
        String sql = "select id from cliente where cpf like ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setString(1, cpf);
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            return resultado.getInt("id");
        }
        throw new ClienteInexistenteException("Não existe cliente com esse CPF");
    }

    int buscarEmprestimo(String cliente, int livro) throws SQLException {
        String sql = "select idemprestimo from emprestimo e,cliente c where c.cpf like ? and e.cliente_id = c.id and livro_id = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setString(1, cliente);
        ps.setInt(2, livro);
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            return resultado.getInt("idemprestimo");
        }
        return -1;
    }

    private void excluirMulta(Devolucao d) throws SQLException, ClienteInexistenteException {
        String sql = "delete from multa where cliente_id = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1,buscarCliente(d.getCliente()));
        ps.executeUpdate();
        ps.close();
    }

    LocalDate buscarDataDevolucaoPrevista(int id) throws SQLException {
        String sql = "select data_prevista_devolucao from emprestimo where idemprestimo = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, id);
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            return LocalDate.parse(resultado.getDate("data_prevista_devolucao").toString());
        }
        return null;
    }
}
