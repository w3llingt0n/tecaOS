/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.clientes;

import exception.ValorInvalidoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.conexao.ConnectionFactory;
import model.entidades.Cliente;

/**
 *
 * @author rapwe
 */
public class ClientesDAO {
    ArrayList<Cliente> listar() throws SQLException {
        String sql = "select * from cliente";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ResultSet resultado = ps.executeQuery();
        ArrayList<Cliente> lista = new ArrayList<>();
        while (resultado.next()) {
            Cliente p = new Cliente(
                    resultado.getInt("id"),
                    resultado.getString("nome"),
                    resultado.getString("cpf"),
                    resultado.getString("rg"),
                    LocalDate.parse(resultado.getDate("data_nascimento").toString()),
                    resultado.getString("email")
            );
            lista.add(p);
        }
        return lista;
    }

    void excluir(Cliente c) throws SQLException {
        String sql = "delete from cliente where id = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, c.getId());
        ps.executeUpdate();
        ps.close();
    }

    public Cliente buscarPeloId(int id) throws SQLException {
        String sql = "select * from cliente where id = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, id);
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            Cliente c = new Cliente(
                    resultado.getInt("id"),
                    resultado.getString("nome"),
                    resultado.getString("cpf"),
                    resultado.getString("rg"),
                    LocalDate.parse(resultado.getDate("data_nascimento").toString()),
                    resultado.getString("email")
            );
            return c;
        }
        return null;
    }

    void editar(Cliente c) throws SQLException {
        String sql = "Update cliente set nome = ?,cpf = ?,rg = ?,data_nascimento = ?,email = ? where id = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setString(1, c.getNome());
        ps.setString(2, c.getCpf());
        ps.setString(3, c.getRg());
        ps.setString(4, c.getDataNasc().toString());
        ps.setString(5, c.getEmail());
        ps.setInt(6, c.getId());
        ps.executeUpdate();
        ps.close();
    }

    void salvar(Cliente c) throws SQLException {
        String sql = "insert into cliente (nome,cpf,rg,data_nascimento,email) value (?,?,?,?,?)";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setString(1, c.getNome());
        ps.setString(2, c.getCpf());
        ps.setString(3, c.getRg());
        ps.setString(4, c.getDataNasc().toString());
        ps.setString(5, c.getEmail());
        ps.executeUpdate();
        ps.close();
    }

    ArrayList<Cliente> filtrarPeloID(String id) throws SQLException, ValorInvalidoException {
        String sql = "select * from cliente where id like ?";
        return filtrar(id, sql);
    }

    ArrayList<Cliente> filtrarPeloNome(String nome) throws SQLException, ValorInvalidoException {
        String sql = "select * from cliente where nome like ?";
        return filtrar(nome, sql);
    }

    private ArrayList<Cliente> filtrar(String pesquisar, String sql) throws SQLException,ValorInvalidoException {
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setString(1, "%" + pesquisar + "%");
        ResultSet resultado = ps.executeQuery();
        ArrayList<Cliente> lista = new ArrayList<>();
        while (resultado.next()) {
            Cliente c = new Cliente(
                    resultado.getInt("id"),
                    resultado.getString("nome"),
                    resultado.getString("cpf"),
                    resultado.getString("rg"),
                    LocalDate.parse(resultado.getDate("data_nascimento").toString()),
                    resultado.getString("email")
            );
            lista.add(c);
        }
        return lista;
    }
}
