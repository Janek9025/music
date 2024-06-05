package org.example;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.example.DatabaseConnection;

import javax.naming.AuthenticationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    protected final int id;
    protected final String username;

    public Account(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    public static class Persistence {
        public static void init() {
            try {
                String createSQLTable = "CREATE TABLE IF NOT EXISTS account( " +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT NOT NULL," +
                        "password TEXT NOT NULL)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(createSQLTable);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public static int register(String username, String password) {
            String hashedPassword =  BCrypt.withDefaults().hashToString(12, password.toCharArray());
            try {
                String insertSQL = "INSERT INTO account(username, password) VALUES (?, ?)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(insertSQL);

                statement.setString(1, username);
                statement.setString(2, hashedPassword);
                statement.executeUpdate();

                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next())
                    return resultSet.getInt(1);
                else throw new SQLException();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public static Account authenticate(String username, String password) throws AuthenticationException {
            try {
                String sql = "SELECT id, username, password FROM account WHERE username = ?";

                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.setString(1, username);

                if (!statement.execute()) throw new RuntimeException("SELECT failed");

                ResultSet result = statement.getResultSet();

                if (!result.next()) {
                    throw new AuthenticationException("No such user");
                }
                String hashedPassword = result.getString(3);
                boolean okay = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword.toCharArray()).verified;

                if (!okay) {
                    throw new AuthenticationException("Wrong password");
                }

                return new Account(
                        result.getInt(1),
                        result.getString(2)
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public static Account getAccount(String username) throws SQLException {
            int id;
            String usernameAccount;
            String url = "SELECT * FROM account WHERE username=?";
            PreparedStatement statement = DatabaseConnection.getConnection().
                    prepareStatement(url);
            statement.setString(1,username);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                id = resultSet.getInt(1);
                usernameAccount = resultSet.getString(2);
                return new Account(id,usernameAccount);
            } else return null;
        }

        public static Account getAccount(int userID) throws SQLException {
            int idAccount;
            String usernameAccount;
            PreparedStatement statement = DatabaseConnection.getConnection().
                    prepareStatement("SELECT * FROM account WHERE ID=?");
            statement.setInt(1,userID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                idAccount = resultSet.getInt(1);
                usernameAccount = resultSet.getString(2);
                return new Account(idAccount,usernameAccount);
            } else return null;
        }
    }

}
