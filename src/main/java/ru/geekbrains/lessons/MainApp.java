package ru.geekbrains.lessons;

import java.util.Arrays;
import java.util.Scanner;

public class MainApp {
    public static char[][] map;
    public static final int SIZE = 5;
    public static final int DOT_COUNT = 4;
    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';

    /*Тесты:
    * 2-1 / 2-3 / 4-1 / 3-1 / 1-1 - выигрывают X
    * 1-1 / 3-3 / 3-1 / 2-4 / 5-1 / 1-3 / 5-3 / 5-5 / 2-1 - выигрывают O */
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        initMap();
        printMap();
        while (true) {
            int x, y;
            do {
                System.out.println("Введите координаты в формате X Y");
                x = sc.nextInt() - 1;
                y = sc.nextInt() - 1;
            } while (!isCellValid(x, y));
            map[x][y] = DOT_X;
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Вы выиграли!");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            aiBlockedTurn(x, y);
            printMap();
            if (checkWin(DOT_O)) {
                System.out.println("Выиграл Искуственный Интеллект!");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
        }
        System.out.println("Игра закончена");
    }

    static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

    static boolean checkWin(char symb)
    {
        String[] lines = new String[SIZE * 2 + 6];
        for (int i = 0; i < SIZE * 2; i += 2 )
        {
            lines[i] = new String(getHorizontalLine(i / 2));
            lines[i + 1] = new String(getVerticalLine(i / 2));
        }
        lines[SIZE * 2] = new String(getDiagonal1Line());
        lines[SIZE * 2 + 1] = new String(getDiagonal2Line());
        lines[SIZE * 2 + 2] = new String(getDiagonal1Line(1));
        lines[SIZE * 2 + 3] = new String(getDiagonal1Line(-1));
        lines[SIZE * 2 + 4] = new String(getDiagonal2Line(1));
        lines[SIZE * 2 + 5] = new String(getDiagonal2Line(-1));

        for (int i = 0; i < lines.length; i++) {
            if (checkArray(lines[i].toCharArray(), symb))
                return true;
        }
        return false;
    }

    static boolean checkArray(char[] arr, char symb)
    {
        for (int i = 0; i <= arr.length - DOT_COUNT; i++)
        {
            char[] part = Arrays.copyOfRange(arr, i, i + DOT_COUNT);
            boolean isCheck = true;
            for (int j = 0; j < part.length; j++)
            {
                if (part[j] != symb)
                {
                    isCheck = false;
                    break;
                }
            }
            if (isCheck)
                return true;
        }
        return false;
    }

    static char[] getHorizontalLine(int i)
    {
        char[] line = new char[SIZE];
        for (int j = 0; j < SIZE; j++)
            line[j] = map[i][j];
        return line;
    }

    static char[] getVerticalLine(int i)
    {
        char[] line = new char[SIZE];
        for (int j = 0; j < SIZE; j++)
            line[j] = map[j][i];
        return line;
    }

    static char[] getDiagonal1Line()
    {
        return getDiagonal1Line(0);
    }

    static char[] getDiagonal1Line(int n)
    {
        int absN = Math.abs(n);
        char[] line = new char[SIZE - absN];
        for (int i = 0; i < SIZE - absN; i++)
            line[i] = n >= 0 ? map[i + n][i] : map[i][i - n];

        return line;
    }

    static char[] getDiagonal2Line()
    {
        return getDiagonal2Line(0);
    }
    static char[] getDiagonal2Line(int n)
    {
        int absN = Math.abs(n);
        char[] line = new char[SIZE - absN];
        for (int i = 0; i < SIZE - absN; i++)
            line[i] = n >= 0 ? map[SIZE - 1 - i][i + n] : map[SIZE - 1 - i + n][i];
        return line;
    };

    static boolean isLineValid(char[] line)
    {
        for (int i = 0; i < line.length; i++)
        {
            if (line[i] == DOT_EMPTY)
                return true;
        }
        return false;
    }

    static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE)
            return false;
        if (map[x][y] == DOT_EMPTY)
            return true;
        return false;
    }

    static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void aiBlockedTurn(int x, int y)
    {
        String[] lines = new String[8];

        if (x == y)
            lines[0] = new String(getDiagonal1Line());
        if (x == y + 1)
            lines[1] = new String(getDiagonal1Line(1));
        if (x == y - 1)
            lines[2] = new String(getDiagonal1Line(-1));

        if (x == SIZE - y - 1)
            lines[3] = new String(getDiagonal2Line());
        if (x == SIZE - y)
            lines[4] = new String(getDiagonal2Line(1));
        if (x == SIZE - y - 2)
            lines[5] = new String(getDiagonal2Line(-1));

        lines[6] = new String(getHorizontalLine(x));
        lines[7] = new String(getVerticalLine(y));

        int turnedLineIndex = getMaxTurnedCountLine(lines);
        if (turnedLineIndex == -1)
            return;

        int blockIndex = 0;
        char[] line = lines[turnedLineIndex].toCharArray();
        switch (turnedLineIndex)
        {
            case 0:
                blockIndex = getBlockedIndex(line, x);
                map[blockIndex][blockIndex] = DOT_O;
                return;

            case 1:
                blockIndex = getBlockedIndex(line, x - 1);
                map[blockIndex + 1][blockIndex] = DOT_O;
                return;

            case 2:
                blockIndex = getBlockedIndex(line, x);
                map[blockIndex][blockIndex + 1] = DOT_O;
                return;

            case 3:
                blockIndex = getBlockedIndex(line, SIZE - x - 1);
                map[SIZE - blockIndex - 1][blockIndex] = DOT_O;
                return;

            case 4:
                blockIndex = getBlockedIndex(line, SIZE - x - 1);
                map[SIZE - blockIndex - 1][blockIndex + 1] = DOT_O;
                return;

            case 5:
                blockIndex = getBlockedIndex(line, SIZE - x - 2);
                map[SIZE - blockIndex - 2][blockIndex] = DOT_O;
                return;

            case 6:
                blockIndex = getBlockedIndex(line, y);
                map[x][blockIndex] = DOT_O;
                return;

            case 7:
                blockIndex = getBlockedIndex(line, x);
                map[blockIndex][y] = DOT_O;
                return;
        }
    }

    static int getMaxTurnedCountLine(String[] lines)
    {
        int max = 0;
        int index = -1;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i] != null)
            {
                char[] line = lines[i].toCharArray();
                if (isLineValid(line))
                {
                    int count = getHumanTurnedCellsCount(line);
                    if (count > max)
                    {
                        max = count;
                        index = i;
                    }
                }
            }
        }
        return index;
    }

    static int getBlockedIndex(char[] line, int index)
    {
        for (int i = index + 1; i < line.length; i++){
            if (line[i] == DOT_EMPTY)
                return i;
        }
        for (int i = index - 1; i >= 0; i--) {
            if (line[i] == DOT_EMPTY)
                return i;
        }
        return -1;
    }

    static int getHumanTurnedCellsCount(char[] line)
    {
        int count = 0;
        for (int i = 0; i < line.length; i++)
        {
            if (line[i] == DOT_X)
                count++;
        }
        return count;
    }
}