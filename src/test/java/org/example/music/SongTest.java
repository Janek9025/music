package org.example.music;

import org.example.DatabaseConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SongTest {

    @BeforeAll
    public static void setUp(){
        DatabaseConnection.connect("C:\\Users\\Legion\\IdeaProjects\\Music\\songs.db", "songs");
    }

    @ParameterizedTest
    @MethodSource("provideIndexesAndExpectedSongs")
    public void testReadParameterized(int index, Song expectedSong) throws SQLException {
        Optional<Song> song = Song.Persistence.read(index);
        assertEquals(expectedSong, song.orElse(null));
    }

    @ParameterizedTest
    @CsvFileSource(files = "C:\\Users\\Legion\\IdeaProjects\\Music\\songs.csv", numLinesToSkip = 1)
    public void testReadParameterized(int index, String artist, String title, int duration) throws SQLException {
        Optional<Song> song = Song.Persistence.read(index);
        Song expectedSong = new Song(artist, title, duration);
        assertEquals(expectedSong, song.orElse(null));
    }


//    static Stream<Arguments> provideIndexesAndExpectedSongs() {
//        return Stream.of(
//                arguments(3, new Song("Led Zeppelin", "Stairway to Heaven", 482)),
//                arguments(47, new Song("The Doors", "Riders on the Storm", 434)),
//                arguments(-1, null),
//                arguments(100, null)
//        );
//    }




}
