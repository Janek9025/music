package org.example.music;

import org.example.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class DatabaseTest {
    @BeforeAll
    static void connectDatabase() {
        DatabaseConnection.connect("songs.db", "songs.db");
    }

    @AfterAll
    static void disconnectDatabase() {
        DatabaseConnection.disconnect("songs.db");
    }

    @Test
    public void testReadInvalid() throws SQLException {
        Optional<Song> song = Song.Persistence.read(-1);
        assertFalse(song.isPresent());
    }

}