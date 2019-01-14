/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.multas;

import exception.ClienteInexistenteException;
import exception.ValorInvalidoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.conexao.ConnectionFactory;
import model.entidades.Multa;

/**
 *
 * @author rapwe
 */
public class MultasDAO {

    ArrayList<Multa> listar() throws SQLException, ClienteInexistenteException {
        String sql = "select * from multa";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ResultSet resultado = ps.executeQuery();
        ArrayList<Multa> lista = new ArrayList<>();
        while (resultado.next()) {
            Multa m = new Multa(
                    resultado.getInt("idmulta"),
                    buscarCliente(resultado.getInt("cliente_id")),
                    resultado.getDouble("valor"),
                    status(resultado.getBoolean("pago"))
            );
            lista.add(m);
        }
        return lista;
    }

    void excluir(Multa m) throws SQLException {
        String sql = "delete from multa where idmulta = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, m.getId());
        ps.executeUpdate();
        ps.close();
    }

    Multa buscarPeloId(int id) throws SQLException, ClienteInexistenteException {
        String sql = "select * from multa where idmulta = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, id);
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            Multa m = new Multa(
                    resultado.getInt("idmulta"),
                    buscarCliente(resultado.getInt("cliente_id")),
                    resultado.getDouble("valor"),
                    status(resultado.getBoolean("pago"))
            );
        }
        return null;
    }

    void editar(Multa m) throws SQLException, ClienteInexistenteException {
        String sql = "Update multa set valor = ?,pago = ?,cliente_id = ? where idmulta = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setDouble(1, m.getValor());
        ps.setBoolean(2, status(m.getStatus()));
        ps.setInt(3, buscarCliente(m.getCliente()));
        ps.setInt(4, m.getId());
        ps.executeUpdate();
        ps.close();
    }

    void salvar(Multa m) throws SQLException, ClienteInexistenteException {
        String sql = "insert into multa (valor,pago,cliente_id) value (?,?,?)";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setDouble(1, m.getValor());
        ps.setBoolean(2, status(m.getStatus()));
        ps.setInt(3, buscarCliente(m.getCliente()));
        ps.executeUpdate();
        ps.close();
    }

    ArrayList<Multa> filtrarPeloID(String id) throws SQLException, ValorInvalidoException, ClienteInexistenteException {
        String sql = "select * from multa where idmulta = ?";
        return filtrar(id, sql);
    }

    ArrayList<Multa> filtrarPeloCliente(String cliente) throws SQLException, ValorInvalidoException, ClienteInexistenteException {
        String sql = "select m.* from multa m,cliente c where c.nome like ?";
        return filtrar(cliente, sql);
    }

    private ArrayList<Multa> filtrar(String pesquisar, String sql) throws SQLException, ValorInvalidoException, ClienteInexistenteException {
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setString(1, "%" + pesquisar + "%");
        ResultSet resultado = ps.executeQuery();
        ArrayList<Multa> lista = new ArrayList<>();
        while (resultado.next()) {
            Multa m = new Multa(
                    resultado.getInt("idmulta"),
                    buscarCliente(resultado.getInt("cliente_id")),
                    resultado.getDouble("valor"),
                    status(resultado.getBoolean("pago"))
            );
            lista.add(m);
        }
        return lista;
    }

    int buscarCliente(String nome) throws SQLException, ClienteInexistenteException {
        String sql = "select id from cliente where nome like ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setString(1, nome);
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            return resultado.getInt("id");
        }
        throw new ClienteInexistenteException("Não existe cliente com esse nome");
    }

    String buscarCliente(int id) throws SQLException, ClienteInexistenteException {
        String sql = "select nome from cliente where id = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, id);
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            return resultado.getString("nome");
        }
        throw new ClienteInexistenteException("Não existe cliente com esse id.");
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

    private String status(boolean b) {
        if(b){
            return "Pago";
        }
        return "Pendente";
    }
    private boolean status(String a){
        return a.equals("Pago");
    }

    ObservableList<String> listarClientes() throws SQLException {
       String sql = "select * from cliente";
       PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
       ResultSet resultado = ps.executeQuery();
       ObservableList<String> lista = FXCollections.observableArrayList();
       while(resultado.next()){
           lista.add(resultado.getString("nome"));
       }
       return lista;
    }

    
}
