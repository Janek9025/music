package org.example.music;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {
    public Song atSecond(int seconds){
        int secondsSong=0;
        if (seconds < 0){
            throw new IndexOutOfBoundsException("Negative value: " + seconds);
        }
        for(Song song : this){
            secondsSong += song.getDuration();
            if(secondsSong>=seconds){
                return  song;
            }
        }

        throw new IndexOutOfBoundsException("Seconds out of bounds: " + seconds);
    }
}
