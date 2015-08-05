package com.dimasolovey.computer;

import com.dimasolovey.gui.GraphicsInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by dmitry.solovey on 08.07.2015.
 */
public class ComputerShips implements ActionListener {
// Экземпляр класс ComputerShips
    public static ComputerShips instance;
// Метод получения экземпляра класса
    public static ComputerShips getInstance() {
        if (instance == null) {
            instance = new ComputerShips();
        }
        return instance;
    }
    private ComputerShips() {
    }
// Начальная координата корабля компьютера
    private int startCoordOfShip;
// Массив 10х10 для хранения состояния игорового поля компьютера
    private static int[][] computerField = new int[10][10];
// Координаты отдельного корабля компьютера
    private ArrayList<Integer> coordsOfShip = new ArrayList<Integer>();
// Координаты всех кораблей компьютера
    private static ArrayList<Integer> coordsOfShips = new ArrayList<Integer>();
// Координаты "окружений" кораблей компьютера
    private ArrayList<Integer> coordsOfOc = new ArrayList<Integer>();
// Флаг разрешения установки корабля компьютера
    private boolean flag;
// Количество координат кораблей компьютера
    private int countOfCoords;
// Кнопка "Вывести боевой флот компьютера"
    private JButton computerConfigurationButton = GraphicsInterface.getComputerConfigurationButton();

    public void setComputerField(int[][] computerField) {
        ComputerShips.computerField = computerField;
    }
    public void clearCoordsOfShip() {
        this.coordsOfShip.clear();
    }
    public void clearCoordsOfShips() {
        ComputerShips.coordsOfShips.clear();
    }
    public void setCountOfCoords(int countOfCoords) {
        this.countOfCoords = countOfCoords;
    }
    public void clearCoordsOfOccup() {
        this.coordsOfOc.clear();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        computerConfigurationButton.setEnabled(false);
// Установка всех кораблей компьютера
        for (int i = 0; i < 10; i++) {
            flag = false;
            if (i == 0) setComputerShip(4);
            else if (i == 1 || i == 2) setComputerShip(3);
            else if (i == 3 || i == 4 || i == 5) setComputerShip(2);
            else if (i == 6 || i == 7 || i == 8 || i == 9) setComputerShip(1);
        }
// Проверка все ли корабли установлены компьютером
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (computerField[i][j] == 1) countOfCoords++;
            }
        }
        if (countOfCoords == 20) {
            GraphicsInterface.clearHintOfGame();
            GraphicsInterface.appendHintOfGame("  Подсказки и ход игры:\n   ВАШ ХОД!\n");
        } else {
            GraphicsInterface.appendHintOfGame("  Что-то пошло не так!\n Нажмите на кнопку:\n НАЧАТЬ ЗАНОВО!\n" +
                    " И попробуйте снова!\n");
        }
    }
// Метод установки корабля компьютера (параметр length - длина корабля)
    private void setComputerShip(int length) {
        while(!flag) {
// Переменная расположения корабля компьютера (0 - вертикальное расположение; 1 - горизонтальное расположение)
            int vertOrHoriz;
            if (Math.random() <= 0.5) vertOrHoriz = 0;
            else vertOrHoriz = 1;
// Генерируем начальную координату корабля
            startCoordOfShip = (int) (Math.random() * 100);
            switch (vertOrHoriz) {
                case 0: {
                    if ((startCoordOfShip + length*10) > 99) {
                        flag = false;
                        break;
                    }
                    fillComputerField(10, length);
                    break;
                }
                case 1: {
                    if (startCoordOfShip/10 != (startCoordOfShip + length)/10) {
                        flag = false;
                        break;
                    }
                    fillComputerField(1, length);
                    break;
                }
            }
        }
    }
// Метод заполнения игрового поля компьютера и тестирования установки корабля
        private void fillComputerField(int n, int length) {
        for (int i = 0; i < length; i++) {
            coordsOfShip.add(startCoordOfShip);
            startCoordOfShip += n;
        }
        if (!testOfShip(coordsOfShip)) {
            coordsOfShip.clear();
            flag = false;
            return;
        }
        for (int coord: coordsOfShip) {
            computerField[coord/10][coord%10] = 1;
            coordsOfShips.add(coord);
        }
        setCoordsOfOc(coordsOfShip);
        coordsOfShip.clear();
        flag = true;
    }
// Метод проверки правильности установки корабля компьютера
    private boolean testOfShip(ArrayList<Integer> coordsOfShip) {
        for (int coord: coordsOfShip) {
            if (computerField[coord/10][coord%10] == 1 || coordsOfOc.contains(coord)) {
                return false;
            }
        }
        return true;
    }
// Метод установки "окружения" корабля компьютера
    private void setCoordsOfOc(ArrayList<Integer> coordsOfShip) {
        int test;
// Если корабль располагается вертикально
        if (coordsOfShip.get(0)%10 == coordsOfShip.get(coordsOfShip.size()-1)%10) {
            if (coordsOfShip.get(0)%10 != 9) {
                for (Integer aCoordsOfShip : coordsOfShip) {
                    coordsOfOc.add(aCoordsOfShip + 1);
                }
            }
            if (coordsOfShip.get(0)%10 != 0) {
                for (Integer aCoordsOfShip : coordsOfShip) {
                    coordsOfOc.add(aCoordsOfShip - 1);
                }
            }
            if (coordsOfShip.get(0)/10 != 0) {
                test = (coordsOfShip.get(0)-1) - 10;
                for (int i = 0; i < 3; i++) {
                    if (test < 0 || Math.abs(test%10 - coordsOfShip.get(0)%10) > 1) {
                        test += 1;
                        continue;
                    }
                    coordsOfOc.add(test);
                    test += 1;
                }
            }
            if (coordsOfShip.get(coordsOfShip.size()-1)/10 != 9) {
                test = ((coordsOfShip.get(coordsOfShip.size()-1)-1) + 10);
                for (int i = 0; i < 3; i++) {
                    if (test > 100 || Math.abs(test%10 - coordsOfShip.get(coordsOfShip.size()-1)%10)> 1) {
                        test += 1;
                        continue;
                    }
                    coordsOfOc.add(test);
                    test += 1;
                }
            }
        }
// Если корабль располагается горизонтально
        if (coordsOfShip.get(0)/10 == coordsOfShip.get(coordsOfShip.size()-1)/10) {
            if (coordsOfShip.get(0)/10 != 0) {
                for (Integer aCoordsOfShip : coordsOfShip) {
                    coordsOfOc.add(aCoordsOfShip - 10);
                }
            }
            if (coordsOfShip.get(0)/10 != 9) {
                for (Integer aCoordsOfShip : coordsOfShip) {
                    coordsOfOc.add(aCoordsOfShip + 10);
                }
            }
            if (coordsOfShip.get(0)%10 != 0) {
                test = (coordsOfShip.get(0) - 1) - 10;
                for (int i = 0; i < 3; i++) {
                    if (test < 0 || test >= 100) {
                        test += 10;
                        continue;
                    }
                    coordsOfOc.add(test);
                    test += 10;
                }
            }
            if (coordsOfShip.get(coordsOfShip.size()-1) != 9) {
                test = (coordsOfShip.get(coordsOfShip.size()-1)+1) - 10;
                for (int i = 0; i < 3; i++) {
                    if (test < 0 || test >= 100) {
                        test += 10;
                        continue;
                    }
                    coordsOfOc.add(test);
                    test += 10;
                }
            }
        }
    }
// Метод возвращения игрового поля компьютера
    public static int[][] getComputerField() {
        return computerField;
    }
// Метод получения координат кораблей компьютера
    public static ArrayList<Integer> getCoordsOfShips() {
        return coordsOfShips;
    }
}
