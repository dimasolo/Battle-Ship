package com.dimasolovey.player;

import com.dimasolovey.computer.ComputerShips;
import com.dimasolovey.game.GameViewer;
import com.dimasolovey.gui.GraphicsInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by dmitry.solovey on 10.07.2015.
 */

// Класс-слушатель кнопок игрового поля компьютера
public class UserPlayer implements ActionListener{
    // Экземпляр класса UserPlayer
    public static UserPlayer instance;
    // Метод получения экземпляра класс UserPlayer
    public static UserPlayer getInstance() {
        if (instance == null) {
            instance = new UserPlayer();
        }
        return instance;
    }
    private UserPlayer() {
    }
    // Переменная для хранения количества ходов игрока
    private int countOfPlayerMoves;
    // Переменная для хранения количества попаданий игрока
    private int countOfPlayerHits;
    // Флаг попадания по кораблю
    boolean flagOfPlayerHit;
    // Флаги потопления кораблей
    private boolean[] flagsOfSankShips = new boolean[10];
    // Координаты кораблей компьютера для определения потоплен ли корабль
    private  ArrayList<Integer> coordsOfComputerShips = ComputerShips.getCoordsOfShips();
    // Координаты кораблей компьютера для установки окружения
    private ArrayList<Integer> coordsForComputerShipsOc = new ArrayList<Integer>();
    // Игровое поле компьютера
    private ArrayList<JButton> computerButtons = GraphicsInterface.getComputerButtons();

    public void setCountOfPlayerMoves(int countOfPlayerMoves) {
        this.countOfPlayerMoves = countOfPlayerMoves;
    }
    public void setCountOfPlayerHits(int countOfPlayerHits) {
        this.countOfPlayerHits = countOfPlayerHits;
    }
    public void setFlagOfPlayerHit(boolean flagOfPlayerHit) {
        this.flagOfPlayerHit = flagOfPlayerHit;
    }
    public void setFlagsOfSankShips(boolean[] flagsOfSankShips) {
        this.flagsOfSankShips = flagsOfSankShips;
    }
    public void clearCoordsOfComputerShips() {
        this.coordsOfComputerShips.clear();
    }
    public void clearCoordsForComputerShipsOc() {
        this.coordsForComputerShipsOc.clear();
    }

// Метод вызываемый при потоплении корабля компьютера и выводящий информацию какой корабль потоплен
    private void userSankShip() {
        if (coordsOfComputerShips.get(0) == -1 && coordsOfComputerShips.get(1) == -1 &&
                coordsOfComputerShips.get(2) == -1 && coordsOfComputerShips.get(3) == -1 && !flagsOfSankShips[0]) {
            setComputerShipsOc(0,3, "Авианосец");
            flagsOfSankShips[0] = true;
        }
        if (coordsOfComputerShips.get(4) == -1 && coordsOfComputerShips.get(5) == -1 &&
                coordsOfComputerShips.get(6) == -1 && !flagsOfSankShips[1]) {
            setComputerShipsOc(4,6, "Линкор");
            flagsOfSankShips[1] = true;
        }
        if (coordsOfComputerShips.get(7) == -1 && coordsOfComputerShips.get(8) == -1 &&
                coordsOfComputerShips.get(9) == -1 && !flagsOfSankShips[2]) {
            setComputerShipsOc(7,9, "Линкор");
            flagsOfSankShips[2] = true;
        }
        if (coordsOfComputerShips.get(10) == -1 && coordsOfComputerShips.get(11) == -1 && !flagsOfSankShips[3]) {
            setComputerShipsOc(10,11, "Крейсер");
            flagsOfSankShips[3] = true;
        }
        if (coordsOfComputerShips.get(12) == -1 && coordsOfComputerShips.get(13) == -1 && !flagsOfSankShips[4]) {
            setComputerShipsOc(12,13, "Крейсер");
            flagsOfSankShips[4] = true;
        }
        if (coordsOfComputerShips.get(14) == -1 && coordsOfComputerShips.get(15) == -1 && !flagsOfSankShips[5]) {
            setComputerShipsOc(14,15, "Крейсер");
            flagsOfSankShips[5] = true;
        }
        if (coordsOfComputerShips.get(16) == -1 && !flagsOfSankShips[6]) {
            setComputerShipsOc(16,16, "Эсминец");
            flagsOfSankShips[6] = true;
        }
        if (coordsOfComputerShips.get(17) == -1 && !flagsOfSankShips[7]) {
            setComputerShipsOc(17,17, "Эсминец");
            flagsOfSankShips[7] = true;
        }
        if (coordsOfComputerShips.get(18) == -1 && !flagsOfSankShips[8]) {
            setComputerShipsOc(18,18, "Эсминец");
            flagsOfSankShips[8] = true;
        }
        if (coordsOfComputerShips.get(19) == -1 && !flagsOfSankShips[9]) {
            setComputerShipsOc(19,19, "Эсминец");
            flagsOfSankShips[9] = true;
        }
    }
// Метод установки аттрибутов кнопок игрового поля компьютера
    private void setAttrOfComputerButtons(String text, Color color, int index) {
        computerButtons.get(index).setText(text);
        computerButtons.get(index).setBackground(color);
        computerButtons.get(index).setEnabled(false);
    }
// Метод установки "окружения" кораблей компьютера
    private void setComputerShipsOc(int start, int end, String s) {
        GraphicsInterface.appendHintOfGame(" " + s + " компьютера потоплен!\n");
        int test;
// Если корабль находится по горизонтали
        if (coordsForComputerShipsOc.get(start)/10 == coordsForComputerShipsOc.get(end)/10) {
            if (coordsForComputerShipsOc.get(start) / 10 != 0) {
                for (int i = start; i <= end; i++) {
                    setAttrOfComputerButtons("\u00B7", Color.WHITE, coordsForComputerShipsOc.get(i) - 10);
                }
            }
            if (coordsForComputerShipsOc.get(start) / 10 != 9) {
                for (int i = start; i <= end; i++) {
                    setAttrOfComputerButtons("\u00B7", Color.WHITE, coordsForComputerShipsOc.get(i) + 10);
                }
            }
            if (coordsForComputerShipsOc.get(start) % 10 != 0) {
                test = (coordsForComputerShipsOc.get(start) - 1) - 10;
                for (int i = 0; i < 3; i++) {
                    if (test < 0 || test >= 100) {
                        test += 10;
                        continue;
                    }
                    setAttrOfComputerButtons("\u00B7", Color.WHITE, test);
                    test += 10;
                }
            }
            if (coordsForComputerShipsOc.get(end) % 10 != 9) {
                test = (coordsForComputerShipsOc.get(end) + 1) - 10;
                for (int i = 0; i < 3; i++) {
                    if (test < 0 || test >= 100) {
                        test += 10;
                        continue;
                    }
                    setAttrOfComputerButtons("\u00B7", Color.WHITE, test);
                    test += 10;
                }
            }
        }
// Если корабль располагается по вертикали
            if (coordsForComputerShipsOc.get(start)%10 == coordsForComputerShipsOc.get(end)%10) {
                if (coordsForComputerShipsOc.get(start)%10 != 9) {
                    for (int i = start; i <= end ; i++) {
                        setAttrOfComputerButtons("\u00B7", Color.WHITE, coordsForComputerShipsOc.get(i)+1);
                    }
                }
                if (coordsForComputerShipsOc.get(start)%10 != 0) {
                    for (int i = start; i <= end ; i++) {
                        setAttrOfComputerButtons("\u00B7", Color.WHITE, coordsForComputerShipsOc.get(i)-1);
                    }
                }
                if (coordsForComputerShipsOc.get(start)/10 != 0) {
                    test = (coordsForComputerShipsOc.get(start)-1) -10;
                    for (int i = 0; i < 3; i++) {
                        if (test < 0 || Math.abs(test%10 - coordsForComputerShipsOc.get(start)%10) > 1) {
                            test += 1;
                            continue;
                        }
                        setAttrOfComputerButtons("\u00B7", Color.WHITE, test);
                        test += 1;
                    }
                }
                if (coordsForComputerShipsOc.get(end)/10 != 9) {
                    test = (coordsForComputerShipsOc.get(end)-1) + 10;
                    for (int i = 0; i < 3; i++) {
                        if (test > 100 || Math.abs(test%10 - coordsForComputerShipsOc.get(end)%10) > 1) {
                            test += 1;
                            continue;
                        }
                        setAttrOfComputerButtons("\u00B7", Color.WHITE, test);
                        test += 1;
                    }
                }
            }
    }
    public void actionPerformed(ActionEvent event) {
// Получаем игровое поле компьютера и координаты кораблей компьютера
        int[][] computerField = ComputerShips.getComputerField();
        coordsOfComputerShips = ComputerShips.getCoordsOfShips();
// Получаем координату нажатой кнопки
        JButton button = (JButton) event.getSource();
        int row = Integer.parseInt(event.getActionCommand().substring(0,1));
        int column = Integer.parseInt(event.getActionCommand().substring(1));
// Заполняем массив координат для формирования окружения корабля
     if (coordsForComputerShipsOc.size() == 0) {
         for (Integer i: coordsOfComputerShips) {
             coordsForComputerShipsOc.add(i);
         }
     }
// Увеличиваем количество ходов игрока
        countOfPlayerMoves++;
// Если попали - увеличиваем количество попаданий игрока, устанавливаем флаг попадания, закрашиваем кнопку
// выводим в область подсказки информацию о попадании
        if (computerField[row][column] == 1) {
            countOfPlayerHits++;
            flagOfPlayerHit = true;
            for (int i = 0; i < coordsOfComputerShips.size(); i++) {
                if (coordsOfComputerShips.get(i) == Integer.parseInt(event.getActionCommand())) {
                    coordsOfComputerShips.set(i, -1);
                }
            }
            GraphicsInterface.appendHintOfGame("  Вы: Ход " + countOfPlayerMoves + " - Попадание!\n");
            userSankShip();
            if (countOfPlayerHits < 20)GraphicsInterface.appendHintOfGame("  Ваш ход!\n");
            button.setEnabled(false);
            button.setBackground(Color.MAGENTA);
// Вызываем метод viewGame для отслеживания хода игры
            GameViewer.viewGame();
// Если мимо - деактивируем соответствующую кнопку, выводим подсказку и устанавливаем флаг попадания игрока в false
        } else {
            flagOfPlayerHit = false;
            button.setEnabled(false);
            button.setBackground(Color.WHITE);
            button.setText("\u00B7");
            GraphicsInterface.appendHintOfGame("  Вы: Ход " + countOfPlayerMoves + " - Мимо!\n");
            GameViewer.viewGame();
        }
    }
// Метод, возвращающий количество попаданий пользователя
    public int getCountOfPlayerHits() {
        return countOfPlayerHits;
    }
    public boolean getFlagOfPlayerHit() {
        return flagOfPlayerHit;
    }
}
