package com.treinamento.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.treinamento.model.Produto;

public class ProdutoDAO {
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/demo?allowPublicKeyRetrieval=true&useSSL=false&useTimezone=true&serverTimezone=UTC";
	private String jdbcUsername = "pedro";
	private String jdbcPassword = "java123";
	
	private static final String INSERT_produtoS_SQL = "INSERT INTO produtos" + "  (nome, tipo) VALUES " +
	        " (?, ?);";

	    private static final String SELECT_produtoS_BY_ID = "select id,nome,tipo from produtos where id =?";
	    private static final String SELECT_ALL_produtoS = "select * from produtos";
	    private static final String DELETE_produtoS_SQL = "delete from produtos where id = ?;";
	    private static final String UPDATE_produtoS_SQL = "update produtos set nome = ?,tipo= ? where id = ?;";



protected Connection getConnection() {
    Connection connection = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return connection;
}

public void insertProduto(Produto produtos) throws SQLException {
    System.out.println(INSERT_produtoS_SQL);
    // try-with-resource statement will auto close the connection.
    try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_produtoS_SQL)) {
        preparedStatement.setString(1, produtos.getNome());
        preparedStatement.setString(2, produtos.getTipo());
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        printSQLException(e);
    }
}

public Produto selectProduto(int id) {
	Produto produto = null;
    // Step 1: Establishing a Connection
    try (Connection connection = getConnection();
        // Step 2:Create a statement using connection object
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_produtoS_BY_ID);) {
        preparedStatement.setInt(1, id);
        System.out.println(preparedStatement);
        // Step 3: Execute the query or update query
        ResultSet rs = preparedStatement.executeQuery();

        // Step 4: Process the ResultSet object.
        while (rs.next()) {
            String nome = rs.getString("nome");
            String tipo = rs.getString("tipo");
            produto = new Produto(id, nome, tipo);
        }
    } catch (SQLException e) {
        printSQLException(e);
    }
    return produto;
}

public List < Produto > selectAllUsers() {

    // using try-with-resources to avoid closing resources (boiler plate code)
    List < Produto > produtos = new ArrayList < > ();
    // Step 1: Establishing a Connection
    try (Connection connection = getConnection();

        // Step 2:Create a statement using connection object
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_produtoS);) {
        System.out.println(preparedStatement);
        // Step 3: Execute the query or update query
        ResultSet rs = preparedStatement.executeQuery();

        // Step 4: Process the ResultSet object.
        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            String tipo = rs.getString("tipo");
            produtos.add(new Produto(id, nome, tipo));
        }
    } catch (SQLException e) {
        printSQLException(e);
    }
    return produtos;
}

public boolean updateProduto(Produto produto) throws SQLException {
    boolean rowUpdated;
    try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_produtoS_SQL);) {
        statement.setString(1, produto.getNome());
        statement.setString(2, produto.getTipo());
        statement.setInt(3, produto.getId());

        rowUpdated = statement.executeUpdate() > 0;
    }
    return rowUpdated;
}

public boolean deleteProduto(int id) throws SQLException {
    boolean rowDeleted;
    try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_produtoS_SQL);) {
        statement.setInt(1, id);
        rowDeleted = statement.executeUpdate() > 0;
    }
    return rowDeleted;
}

private void printSQLException(SQLException ex) {
    for (Throwable e: ex) {
        if (e instanceof SQLException) {
            e.printStackTrace(System.err);
            System.err.println("SQLState: " + ((SQLException) e).getSQLState());
            System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
            System.err.println("Message: " + e.getMessage());
            Throwable t = ex.getCause();
            while (t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
        }
    }
}

}