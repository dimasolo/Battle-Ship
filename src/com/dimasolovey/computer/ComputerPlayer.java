package com.dimasolovey.computer;

import com.dimasolovey.game.GameViewer;
import com.dimasolovey.gui.GraphicsInterface;
import com.dimasolovey.player.PlayerShips;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by dmitry.solovey on 09.07.2015.
 */
// Класс для организации хода компьютера
public class ComputerPlayer {
// Экземпляр класса ComputerPlayer
    public static ComputerPlayer instance;
// Метод получения экземпляра класса ComputerPlayer
    public static ComputerPlayer getInstance() {
        if (instance == null) {
            instance = new ComputerPlayer();
        }
        return instance;
    }
    private ComputerPlayer() {
    }
// Координата хода компьютера
    private  int coordOfComputerMove;
// Переменная для хранения количества ходов компьютера
    private  int countOfComputerMoves;
// Переменная для хранения количества попаданий компьютера
    private  int countOfComputerHits;
// Кнопки игрового поля игрока
    private  ArrayList<JButton> playerButtons = GraphicsInterface.getPlayerButtons();
// Массив хранения координат ходов компьютера
    private  ArrayList<Integer> coordsOfComputerMoves = new ArrayList<Integer>();
// Массив хранения координат попаданий компьютера
    private  LinkedList<Integer> coordsOfComputerHits = new LinkedList<Integer>();
// Связной список для координат обстрела, если компьютер попал по кораблю игрока
    private  LinkedList<Integer> recomendation_pool = new LinkedList<Integer>();
// Координаты кораблей игрока
    private  int[] playerShips;
// Флаги потоплений кораблей компьютера
    private boolean[] flagsOfSankShips = new boolean[10];
// Переменная для подсчета попаданий компьютера до 2-х
    private  int count;

    public void setCoordOfComputerMove(int coordOfComputerMove) {
        this.coordOfComputerMove = coordOfComputerMove;
    }
    public void setCountOfComputerMoves(int countOfComputerMoves) {
        this.countOfComputerMoves = countOfComputerMoves;
    }
    public void setCountOfComputerHits(int countOfComputerHits) {
        this.countOfComputerHits = countOfComputerHits;
    }
    public void clearCoordsOfComputerMoves() {
        this.coordsOfComputerMoves.clear();
    }
    public void clearCoordsOfComputerHits() {
        this.coordsOfComputerHits.clear();
    }
    public void clearRecomendation_pool() {
        this.recomendation_pool.clear();
    }
    public void setFlagsOfSankShips(boolean[] flagsOfSankShips) {
        this.flagsOfSankShips = flagsOfSankShips;
    }
    public void setCount(int count) {
        this.count = count;
    }

// Метод сортировки координат попаданий компьютера
    private  void sortCoordsOfComputerHits() {
        for (int i = 0; i < coordsOfComputerHits.size() ; i++) {
            for (int j = i+1; j < coordsOfComputerHits.size(); j++) {
                int tmp =  coordsOfComputerHits.get(i);
                if (coordsOfComputerHits.get(j) < coordsOfComputerHits.get(i)) {
                    coordsOfComputerHits.set(i, coordsOfComputerHits.get(j));
                    coordsOfComputerHits.set(j, tmp);
                }
            }
        }
    }
// Метод вызываемый при потоплении корабля игрока и выводящий информацию какой корабль потоплен
    private void computerSankShip() {
       if (playerShips[0] == -1 && playerShips[1] == - 1 && playerShips[2] == - 1 && playerShips[3]== - 1 &&
               !flagsOfSankShips[0]) {
           setAttrIfSank("авианосец");
           flagsOfSankShips[0] = true;
       }
        else if (playerShips[4] == -1 && playerShips[5] == - 1 && playerShips[6] == - 1 && !flagsOfSankShips[1]){
           setAttrIfSank("линкор");
           flagsOfSankShips[1] = true;
       }
        else if (playerShips[7] == -1 && playerShips[8] == - 1 && playerShips[9] == - 1 && !flagsOfSankShips[2]){
           setAttrIfSank("линкор");
           flagsOfSankShips[2] = true;
       }
        else if (playerShips[10] == -1 && playerShips[11] == - 1 && !flagsOfSankShips[3]) {
           setAttrIfSank("крейсер");
           flagsOfSankShips[3] = true;
       }
       else if (playerShips[12] == -1 && playerShips[13] == - 1 && !flagsOfSankShips[4]) {
           setAttrIfSank("крейсер");
           flagsOfSankShips[4] = true;
       }
       else if (playerShips[14] == -1 && playerShips[15] == - 1 && !flagsOfSankShips[5]) {
           setAttrIfSank("крейсер");
           flagsOfSankShips[5] = true;
       }
        else if (playerShips[16] == -1 && !flagsOfSankShips[6]) {
           setAttrIfSank("эсминец");
           flagsOfSankShips[6] = true;
       }
        else if (playerShips[17] == -1 && !flagsOfSankShips[7]) {
           setAttrIfSank("эсминец");
           flagsOfSankShips[7] = true;
       }
       else if (playerShips[18] == -1 && !flagsOfSankShips[8]) {
           setAttrIfSank("эсминец");
           flagsOfSankShips[8] = true;
       }
       else if (playerShips[19] == -1 && !flagsOfSankShips[9]) {
           setAttrIfSank("эсминец");
           flagsOfSankShips[9] = true;
       }
    }
// Метод установки атрибутов, если корабль игрока потоплен
    private  void setAttrIfSank(String s) {
        GraphicsInterface.appendHintOfGame("  Ваш " + s + " потоплен!\n");
        setUserShipsOc(coordsOfComputerHits);
        coordsOfComputerHits.clear();
        count = 0;
    }
// Метод установки "окружения" кораблей игрока
    private  void setUserShipsOc (LinkedList<Integer> list) {
        int test;
        sortCoordsOfComputerHits();
// Если корабль расположен по горизонтали
        if (list.getFirst()/10 == list.getLast()/10) {
            if (list.getFirst()/10 !=0) {
                for (Integer aList : list) {
                    if (coordsOfComputerMoves.contains(aList - 10)) continue;
                    coordsOfComputerMoves.add(aList - 10);
                    setBackgroundAndText(aList - 10, Color.WHITE, "\u00B7");
                }
            }
            if (list.getFirst()/10 != 9) {
                for (Integer aList : list) {
                    if (coordsOfComputerMoves.contains(aList + 10)) continue;
                    coordsOfComputerMoves.add(aList + 10);
                    setBackgroundAndText(aList + 10, Color.WHITE, "\u00B7");
                }
            }
            if (list.getFirst()%10 != 0) {
                test = (list.getFirst()-1)-10;
                for (int i = 0; i < 3; i++) {
                    if (test < 0 || test >= 100 || coordsOfComputerMoves.contains(test)) {
                        test += 10;
                        continue;
                    }
                    coordsOfComputerMoves.add(test);
                    setBackgroundAndText(test, Color.WHITE, "\u00B7");
                    test += 10;
                }
            }
            if (list.getLast()%10 != 9) {
                test = (list.getLast()+1)-10;
                for (int i = 0; i < 3; i++) {
                    if (test < 0 || test >= 100 || coordsOfComputerMoves.contains(test)) {
                        test += 10;
                        continue;
                    }
                    coordsOfComputerMoves.add(test);
                    setBackgroundAndText(test, Color.WHITE, "\u00B7");
                    test += 10;
                }
            }
        }
// Если корабль расположен по вертикали
        if (list.getFirst()%10 == list.getLast()%10) {
            if (list.getFirst()%10 !=9) {
                for (Integer aList : list) {
                    if (coordsOfComputerMoves.contains(aList + 1)) continue;
                    coordsOfComputerMoves.add(aList + 1);
                    setBackgroundAndText(aList + 1, Color.WHITE, "\u00B7");
                }
            }
            if (list.getFirst()%10 != 0) {
                for (Integer aList : list) {
                    if (coordsOfComputerMoves.contains(aList - 1)) continue;
                    coordsOfComputerMoves.add(aList - 1);
                    setBackgroundAndText(aList - 1, Color.WHITE, "\u00B7");
                }
            }
            if (list.getFirst()/10 != 0) {
                test = (list.getFirst()-1)-10;
                for (int i = 0; i < 3; i++) {
                    if (test < 0 || Math.abs(test%10 - list.getFirst()%10) > 1 || coordsOfComputerMoves.contains(test)) {
                        test += 1;
                        continue;
                    }
                    coordsOfComputerMoves.add(test);
                    setBackgroundAndText(test, Color.WHITE, "\u00B7");
                    test += 1;
                }
            }
            if (list.getLast()/10 != 9) {
                test = (list.getLast() - 1) + 10;
                for (int i = 0; i < 3 ; i++) {
                    if (test > 100 || Math.abs(test%10 - list.getLast()%10) > 1 || coordsOfComputerMoves.contains(test)) {
                        test += 1;
                        continue;
                    }
                    coordsOfComputerMoves.add(test);
                    setBackgroundAndText(test, Color.WHITE, "\u00B7");
                    test += 1;
                }
            }
        }
    }
// Метод установки цвета и текста кнопок игрового поля игрока
    private  void setBackgroundAndText(int index, Color color, String text) {
        playerButtons.get(index).setBackground(color);
        playerButtons.get(index).setText(text);
    }
// Метод установки атрибутов и переменных, если компьютер попал
    private  void setAttrIfHit() {
        count++;
        countOfComputerHits++;
        coordsOfComputerHits.add(coordOfComputerMove);
        for (int i = 0; i < playerShips.length; i++) {
            if (coordOfComputerMove == playerShips[i]) {
               playerShips[i] = -1;
            }
        }
        GraphicsInterface.appendHintOfGame(" Компьютер: Ход " + countOfComputerMoves + " - Попадание!\n");
        setBackgroundAndText(coordOfComputerMove, Color.RED, "");
        computerSankShip();
    }
// Метод установки атрибутов и переменных, если компьютер не попал
    private  void setAttrIfFail() {
        setBackgroundAndText(coordOfComputerMove, Color.WHITE, "\u00B7");
        GraphicsInterface.appendHintOfGame(" Компьютер: Ход " + countOfComputerMoves + " - Мимо!\n");
        GraphicsInterface.appendHintOfGame("  Ваш ход!\n");
    }
// Метод организации хода компьютера
   public void computerMove() {
       int[][] playerField = PlayerShips.getPlayerField();
       playerShips = PlayerShips.getPlayerShips();
        countOfComputerMoves++;
// Если компьютер попал по кораблю игрока более 2-х раз
        if (count >= 2) {
// Если корабль расположен по горизонтали
            if (coordsOfComputerHits.getFirst()/10 == coordsOfComputerHits.getLast()/10) {
                sortCoordsOfComputerHits();
                if (!coordsOfComputerMoves.contains(coordsOfComputerHits.getFirst()-1) &&
                        coordsOfComputerHits.getFirst()/10 == (coordsOfComputerHits.getFirst()-1)/10 &&
                        (coordsOfComputerHits.getFirst()-1) >= 0) {
                    coordOfComputerMove = coordsOfComputerHits.getFirst() - 1;
                } else {
                    coordOfComputerMove = coordsOfComputerHits.getLast() + 1;
                }
                // Если компьютер попал
                if (playerField[coordOfComputerMove/10][coordOfComputerMove%10] == 1) {
                    coordsOfComputerMoves.add(coordOfComputerMove);
                    setAttrIfHit();
                    GameViewer.viewGame();
                    return;
                } else {
                    coordsOfComputerMoves.add(coordOfComputerMove);
                    setAttrIfFail();
                    return;
                }
            }
            // Если корабль расположен по вертикали
            if (coordsOfComputerHits.getFirst()%10 == coordsOfComputerHits.getLast()%10) {
                sortCoordsOfComputerHits();
                if (!coordsOfComputerMoves.contains(coordsOfComputerHits.getFirst()-10) &&
                        coordsOfComputerHits.getFirst()-10 >= 0) {
                    coordOfComputerMove = coordsOfComputerHits.getFirst() - 10;
                } else {
                    coordOfComputerMove = coordsOfComputerHits.getLast() + 10;
                }
                if (playerField[coordOfComputerMove/10][coordOfComputerMove%10] == 1) {
                    coordsOfComputerMoves.add(coordOfComputerMove);
                    setAttrIfHit();
                    GameViewer.viewGame();
                    return;
                } else {
                    coordsOfComputerMoves.add(coordOfComputerMove);
                    setAttrIfFail();
                    return;
                }
            }
        }
// Случайным образом определяем координату хода компьютера
        if (countOfComputerHits != 20) {
            if (count < 1) {
                coordOfComputerMove = (int) (Math.random()*100);
                while(coordsOfComputerMoves.contains(coordOfComputerMove)) {
                    coordOfComputerMove = (int) (Math.random()*100);
                }
                coordsOfComputerMoves.add(coordOfComputerMove);
                if (playerField[coordOfComputerMove/10][coordOfComputerMove%10] == 1) {
                    setAttrIfHit();
                    GameViewer.viewGame();
                    return;
                } else {
                   setAttrIfFail();
                    return;
                }
            }
        }
// После первого попадания по кораблю игрока формируем список координат дальнейшего
// обстрела кораблей игрока в зависимости от расстановки корабля
        if (count == 1) {
            if (!coordsOfComputerMoves.contains(coordsOfComputerHits.getLast()+1) &&
                    coordsOfComputerHits.getLast()/10 == (coordsOfComputerHits.getLast()+1)/10) {
                recomendation_pool.add(coordsOfComputerHits.getLast()+1);
            }
            if (!coordsOfComputerMoves.contains(coordsOfComputerHits.getLast()-1) &&
                    coordsOfComputerHits.getLast()/10 == (coordsOfComputerHits.getLast()-1)/10 &&
                    coordsOfComputerHits.getLast()-1 > 0) {
                recomendation_pool.add(coordsOfComputerHits.getLast()-1);
            }
            if (!coordsOfComputerMoves.contains(coordsOfComputerHits.getLast()+10) &&
                    coordsOfComputerHits.getLast()+10 < 99) {
                recomendation_pool.add(coordsOfComputerHits.getLast() + 10);
            }
            if (!coordsOfComputerMoves.contains(coordsOfComputerHits.getLast()-10) &&
                    coordsOfComputerHits.getLast()-10 > 0) {
                recomendation_pool.add(coordsOfComputerHits.getLast() - 10);
            }
// Случайным образом определяем стратегию дальнейшего хода компьютера, если попал по кораблю игрока
            int random = (int) (Math.random() * recomendation_pool.size());
            switch (random) {
                case 0: {
                    coordOfComputerMove = recomendation_pool.get(0);
                    break;
                }
                case 1: {
                    coordOfComputerMove = recomendation_pool.get(1);
                    break;
                }
                case 2: {
                    coordOfComputerMove = recomendation_pool.get(2);
                    break;
                }
                case 3: {
                    coordOfComputerMove = recomendation_pool.get(3);
                    break;
                }
                default: {
                    coordOfComputerMove = recomendation_pool.getLast();
                }
            }
            if (playerField[coordOfComputerMove/10][coordOfComputerMove%10] == 1) {
                coordsOfComputerMoves.add(coordOfComputerMove);
                setAttrIfHit();
                recomendation_pool.clear();
                GameViewer.viewGame();
            } else {
                coordsOfComputerMoves.add(coordOfComputerMove);
                setAttrIfFail();
                recomendation_pool.clear();
            }
        }
    }
// Метод получения количества попаданий компьютера
    public int getCountOfComputerHits() {
        return countOfComputerHits;
    }
}
