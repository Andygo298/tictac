package com.github.andygo298.tictac.game;

public class Painter {

    private char FREE_CELL;

    public Painter(char FREE_CELL) {
        this.FREE_CELL = FREE_CELL;
    }

    public void initGameField(char[][] gameField) {
        for (int row = 0; row < gameField.length; row++) {
            for (int column = 0; column < gameField.length; column++) {
                gameField[row][column] = FREE_CELL;
            }
        }
    }

    public void printGameField(char[][] gameField) {
        System.out.print("      ");
        for (int i = 0; i < gameField.length; i++) {
            System.out.print(" col_" + (i + 1));
        }
        System.out.println();
        System.out.print("       ");
        for (int i = 0; i < gameField.length; i++) {
            System.out.print("  ▼   ");
        }
        System.out.println();
        for (int row = 0; row < gameField.length; row++) {
            System.out.print("row_" + (row + 1) + " ►  ");
            for (int column = 0; column < gameField.length; column++) {
                System.out.print(gameField[row][column] + "     ");
            }
            System.out.println();
        }
    }
}
