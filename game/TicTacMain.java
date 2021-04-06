package com.github.andygo298.tictac.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.LogManager;


public class TicTacMain {

    public static void main(String[] args) {
        ProcessGame processGame = new ProcessGame();

        try {
            LogManager.getLogManager().readConfiguration(
                    TicTacMain.class.getClassLoader().getResourceAsStream("com/github/andygo298/tictac/logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            while (true){
                processGame.startGame(reader);
                System.out.println("Do you want to start a new game?");
                System.out.println("Y/N?");
                String afterEndGame = reader.readLine().toLowerCase();
                if (afterEndGame.equals("y")) {
                    processGame.startGame(reader);
                    continue;
                } else if (afterEndGame.equals("n")) {
                    break;
                } else {
                    System.out.println("Wrong input! Please input Y or N.");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Game closed.");
    }
}
