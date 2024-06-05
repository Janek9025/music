package org.example;

public class NotEnoughCreditsException extends Exception{

    public NotEnoughCreditsException() {
        super("Not enough Credits to buy a song");
    }
}
