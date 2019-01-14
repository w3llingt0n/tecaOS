package model.conexao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fábrica de conexão com o baco de dados
 *
 * @author Luciano
 */
public class ConnectionFactory {

    //Configura qual driver de conexao vai usar
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    //Nome do banco de dados
    private static final String BANCO = "tecaos";

    //String de conexao
    private static final String CONEXAO = "jdbc:mysql://127.0.0.1:3306/" + BANCO;

    //Usuário
    private static final String USUARIO = "root";

    //Senha
    private static final String SENHA = "";

    /**
     * Retorna uma conexão com o banco de dados
     *
     * @return Connection
     * @throws SQLException
     */
    public static Connection pegarConexao() throws SQLException {

        return DriverManager.getConnection(CONEXAO, USUARIO, SENHA);

    }

    /**
     * Prepara uma SQL pra ser executada como PreparedStatement
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public static PreparedStatement prepararSQL(String sql) throws SQLException {

        return pegarConexao().prepareStatement(sql);

    }

    /**
     * Colocado aqui só pra auxiliar no teste da conexao TODO fazer o teste de
     * conexao em outro lugar
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            prepararSQL("Select * from livro");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
