package com.github.andygo298.tictac.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessGame {

    private static Logger log = Logger.getLogger(ProcessGame.class.getName());
    private int FIELD_SIZE;
    private char[][] GAME_FIELD;
    private boolean isSecondPlayerAI;
    private boolean isCurrentX;
    private final char FREE_CELL = '█';
    private final Painter painter;

    public ProcessGame() {
        this.painter = new Painter(FREE_CELL);
    }

    public void startGame(BufferedReader reader) {
        System.out.println("Game is started!!!");
        while (true) {
            try {
                System.out.println("Input size of the board:");
                try {
                    FIELD_SIZE = Integer.parseInt(reader.readLine());
                    GAME_FIELD = new char[FIELD_SIZE][FIELD_SIZE];
                } catch (NumberFormatException ex) {
                    log.log(Level.WARNING, "incorrect input", ex);
                    System.out.println("WRONG INPUT");
                    continue;
                }
                System.out.println("Do you want to play with 'X'? \n(Y/N?)");
                String inputString = reader.readLine().toLowerCase();
                if (inputString.equals("y")) {
                    isCurrentX = false;
                } else if (inputString.equals("n")) {
                    isCurrentX = true;
                } else {
                    System.out.println("Wrong input! Please input Y or N.");
                    continue;
                }
                System.out.println("Do you want to play with AI? \n(Y/N?)");
                isSecondPlayerAI = reader.readLine().toLowerCase().equals("y");
                System.out.println("OK! Let's go! " + "You've chosen - " + (!isCurrentX ? "X " : "O ") + "Make your first step:");
                System.out.println("Choose the column and row, then input their numbers: ");
                painter.initGameField(GAME_FIELD);
                painter.printGameField(GAME_FIELD);
                startProcess();
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startProcess() {
        while (true) {
            movePlayer();
            if (isGameOver(isCurrentX)) {
                System.out.println("Player - " + (isCurrentX ? 'X' : 'O') + " won!!");
                break;
            }
            if (isDraw()) {
                System.out.println("DRAW!!");
                break;
            }
            if (isSecondPlayerAI) {
                moveAI();
                if (isGameOver(isCurrentX)) {
                    System.out.println("YOU lose!");
                    break;
                }
                if (isDraw()) {
                    System.out.println("DRAW!!");
                    break;
                }
            }

        }
    }

    private void movePlayer() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int column;
            int row;
            while (true) {
                try {
                    System.out.println("Input column:");
                    column = Integer.parseInt(reader.readLine()) - 1;
                    System.out.println("Input row:");
                    row = Integer.parseInt(reader.readLine()) - 1;

                    if (isCellValid(column, row)) {
                        System.out.println("CELL IS NOT FREE, input again");
                        continue;
                    }
                    GAME_FIELD[row][column] = !isCurrentX ? 'X' : 'O';
                    break;
                } catch (NumberFormatException ex) {
                    log.log(Level.WARNING, "move player - incorrect input!", ex);
                    System.out.println("Wrong input, try again!");
                }
            }
            isCurrentX = !isCurrentX;
            painter.printGameField(GAME_FIELD);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveAI() {
        final Random random = new Random();
        System.out.println("AI is Moving...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int column;
        int row;
        while (true) {
            column = random.nextInt(FIELD_SIZE);
            row = random.nextInt(FIELD_SIZE);
            if (isCellValid(column, row)) {
                continue;
            }
            GAME_FIELD[row][column] = !isCurrentX ? 'X' : 'O';
            break;
        }
        isCurrentX = !isCurrentX;
        painter.printGameField(GAME_FIELD);
    }

    private boolean isCellValid(int x, int y) {
        if ((x >= 0 && x < FIELD_SIZE) && (y >= 0 && y < FIELD_SIZE)) {
            if (GAME_FIELD[y][x] == FREE_CELL) {
                return false;
            }
        } else {
            System.out.println("WRONG INPUT. Field size is " + GAME_FIELD.length);
        }
        return true;
    }

    private boolean isDraw() {
        for (int row = 0; row < FIELD_SIZE; row++) {
            for (int column = 0; column < FIELD_SIZE; column++) {
                if (GAME_FIELD[row][column] == FREE_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isGameOver(boolean isCurrentX) {
        char X = 'X';
        char O = 'O';
        //Check rows:
        for (int i = 0; i < FIELD_SIZE; i++) {
            int tempResult = 0;
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (GAME_FIELD[i][j] == (isCurrentX ? X : O)) {
                    tempResult++;
                }
            }
            if (tempResult == FIELD_SIZE) {
                return true;
            }
        }
        //Check columns:
        for (int i = 0; i < FIELD_SIZE; i++) {
            int tempResult = 0;
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (GAME_FIELD[j][i] == (isCurrentX ? X : O)) {
                    tempResult++;
                }
            }
            if (tempResult == FIELD_SIZE) {
                return true;
            }
        }
        //Check diagonals:
        int first = 0;

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (j == i && GAME_FIELD[i][j] == (isCurrentX ? X : O)) {
                    first++;
                }
            }
        }
        if (first == FIELD_SIZE) {
            return true;
        }
        int second = 0;
        for (int i = 0, j = FIELD_SIZE - 1; i < FIELD_SIZE && j >= 0; i++, j--) {
            if (GAME_FIELD[i][j] == (isCurrentX ? X : O)) {
                second++;
            }
        }
        if (second == FIELD_SIZE) {
            return true;
        }
        return false;
    }
}
