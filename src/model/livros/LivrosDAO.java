/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.livros;

import exception.ValorInvalidoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.conexao.ConnectionFactory;
import model.entidades.Livro;

/**
 *
 * @author rapwe
 */
public class LivrosDAO {
    ArrayList<Livro> listar() throws SQLException {
        String sql = "select l.*,count(e.idemprestimo) as emprestados from livro l, emprestimo e where l.id = e.livro_id group by l.id";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ResultSet resultado = ps.executeQuery();
        ArrayList<Livro> lista = new ArrayList<>();
        while (resultado.next()) {
            Livro l = new Livro(
                    resultado.getInt("id"),
                    resultado.getString("titulo"),
                    resultado.getString("ano_lancamento"),
                    resultado.getString("autor"),
                    resultado.getString("categoria"),
                    resultado.getInt("quantidade"),
                    resultado.getInt("emprestados")
            );
            lista.add(l);
        }
        return lista;
    }

    void excluir(Livro l) throws SQLException {
        String sql = "delete from livro where id = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, l.getId());
        ps.executeUpdate();
        ps.close();
    }

    public Livro buscarPeloId(int id) throws SQLException {
        String sql = "select l.*,count(e.idemprestimo) as emprestados from livro l, emprestimo e where l.id = e.livro_id and id = ? group by l.id";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setInt(1, id);
        ResultSet resultado = ps.executeQuery();
        if (resultado.next()) {
            Livro l = new Livro(
                    resultado.getInt("id"),
                    resultado.getString("titulo"),
                    resultado.getString("ano_lancamento"),
                    resultado.getString("autor"),
                    resultado.getString("categoria"),
                    resultado.getInt("quantidade"),
                    resultado.getInt("emprestados")
            );
            return l;
        }
        return null;
    }

    void editar(Livro l) throws SQLException {
        String sql = "Update Livro set titulo = ?,ano_lancamento = ?,autor = ?,categoria = ?,quantidade = ? where id = ?";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setString(1, l.getTitulo());
        ps.setString(2, l.getAnoLancamento());
        ps.setString(3, l.getAutor());
        ps.setString(4, l.getCategoria());
        ps.setInt(5, l.getQuantidade());
        ps.setInt(6, l.getId());
        ps.executeUpdate();
        ps.close();
    }

    void salvar(Livro l) throws SQLException {
        String sql = "insert into livro (titulo,ano_lancamento,autor,categoria,quantidade) value (?,?,?,?,?)";
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setString(1, l.getTitulo());
        ps.setString(2, l.getAnoLancamento());
        ps.setString(3, l.getAutor());
        ps.setString(4, l.getCategoria());
        ps.setInt(5, l.getQuantidade());
        ps.executeUpdate();
        ps.close();
    }

    ArrayList<Livro> filtrarPeloID(String id) throws SQLException, ValorInvalidoException {
        String sql = "select l.*,count(e.idemprestimo) as emprestados from livro l, emprestimo e where l.id = e.livro_id and id like ? group by l.id";
        return filtrar(id, sql);
    }

    ArrayList<Livro> filtrarPeloTitulo(String titulo) throws SQLException, ValorInvalidoException {
        String sql = "select l.*,count(e.idemprestimo) as emprestados from livro l, emprestimo e where l.id = e.livro_id and titulo like ? group by l.id";
        return filtrar(titulo, sql);
    }
    ArrayList<Livro> filtrarPeloAutor(String autor) throws SQLException, ValorInvalidoException {
        String sql = "select l.*,count(e.idemprestimo) as emprestados from livro l, emprestimo e where l.id = e.livro_id and autor like ? group by l.id";
        return filtrar(autor, sql);
    }
    ArrayList<Livro> filtrarPelaCategoria(String categoria) throws SQLException, ValorInvalidoException {
        String sql = "select l.*,count(e.idemprestimo) as emprestados from livro l, emprestimo e where l.id = e.livro_id and categoria like ? group by l.id";
        return filtrar(categoria, sql);
    }

    private ArrayList<Livro> filtrar(String pesquisar, String sql) throws SQLException,ValorInvalidoException {
        PreparedStatement ps = ConnectionFactory.prepararSQL(sql);
        ps.setString(1, "%" + pesquisar + "%");
        ResultSet resultado = ps.executeQuery();
        ArrayList<Livro> lista = new ArrayList<>();
        while (resultado.next()) {
            Livro l = new Livro(
                    resultado.getInt("id"),
                    resultado.getString("titulo"),
                    resultado.getString("ano_lancamento"),
                    resultado.getString("autor"),
                    resultado.getString("categoria"),
                    resultado.getInt("quantidade"),
                    resultado.getInt("emprestados")
            );
            lista.add(l);
        }
        return lista;
    }
}
