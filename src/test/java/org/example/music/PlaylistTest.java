package org.example.music;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {
    @Test
    public  void  testPlaylistIsEmpty(){
        Playlist playList = new Playlist();
        assertTrue(playList.isEmpty());
    }
    @Test
    public void testPlaylistAddSong(){
        Playlist playList = new Playlist();
        Song song = new Song("Heavy Young Heathens", "Being Evil Has a Price", 194);
        playList.add(song);
        assertEquals(1,playList.size());
    }
    @Test
    public void testPlaylistSameSong(){
        Playlist playList = new Playlist();
        Song song = new Song("Heavy Young Heathens", "Being Evil Has a Price", 194);
        playList.add(song);
        assertEquals(song,playList.get(0));
    }
    @Test
    public void testPlaylistSameSong2(){
        Playlist playList = new Playlist();
        Song song = new Song("Heavy Young Heathens", "Being Evil Has a Price", 194);
        playList.add(new Song("Heavy Young Heathens", "Being Evil Has a Price", 194));
        assertEquals(song,playList.get(0));
    }
    @Test
    public void testMethodAtSecond(){
        Playlist playList = new Playlist();
        Song song1 = new Song("Heavy Young Heathens", "Being Evil Has a Price", 203);
        Song song2 = new Song("The Forest Rangers", "John The Revelator", 334);
        Song song3 = new Song("Lee DeWyze", "Blackbird Song", 254);
        playList.add(song1);
        playList.add(song2);
        playList.add(song3);
        assertEquals(song3,playList.atSecond(700));
        assertEquals(song2,playList.atSecond(500));
        assertEquals(song1,playList.atSecond(100));
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            playList.atSecond(-50);
        });
        assertEquals("Negative value: -50", exception.getMessage());

        IndexOutOfBoundsException exception2 = assertThrows(IndexOutOfBoundsException.class, () -> {
            playList.atSecond(800);
        });
        assertEquals("Seconds out of bounds: 800", exception2.getMessage());
    }
}