package org.example.music;

import org.example.Account;
import org.example.DatabaseConnection;
import org.example.ListenerAccount;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ListenerAccountTest {

    @BeforeAll
    public static void setUp() throws SQLException {
        DatabaseConnection.connect("C:\\Users\\Legion\\IdeaProjects\\Music\\songs.db", "");
        Account.Persistence.init();
    }


    @Test
    public void RegisterAccountTest() throws SQLException {
            ListenerAccount.Persistence.register("user", "password");
            Account account = Account.Persistence.getAccount("user");
            assertNotNull(account);
            assertEquals("user", account.getUsername());
    }
}
