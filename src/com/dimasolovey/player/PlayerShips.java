package com.dimasolovey.player;

import com.dimasolovey.gui.GraphicsInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dmitry.solovey on 02.07.2015.
 */
// Класс-слушатель событий от игрока (нажатие кнопок на поле игрока - расстановка кораблей)
public class PlayerShips implements ActionListener {
// Экземпляр класса PlayerShips
    public static PlayerShips instance;
// Метод получения экземпляра класс
    public static PlayerShips getInstance() {
        if (instance == null) {
            instance = new PlayerShips();
        }
        return instance;
    }
    private PlayerShips() {
    }
// Переменная, в которой хранится количество ячеек кораблей игрока
    private int countOfPlayerShips;
// Массив 10х10 для хранения состояния игорового поля игрока
    private static int[][] playerField = new int[10][10];
// Массив для хранения координат всех кораблей игрока
    private static int[] playerShips = new int[20];
// Переменная для тестирования правильности установки кораблей игрока
    private boolean playerTest = true;
// Массив для кнопок игрока
    private ArrayList<JButton> playerButtons = GraphicsInterface.getPlayerButtons();
// Массив для кнопок компьютера
    private ArrayList<JButton> computerButtons = GraphicsInterface.getComputerButtons();
// Кнопка конфигурации кораблей компьютера
    private JButton computerConfigurationButton = GraphicsInterface.getComputerConfigurationButton();

    public void setCountOfPlayerShips(int countOfPlayerShips) {
        this.countOfPlayerShips = countOfPlayerShips;
    }
    public void setPlayerField(int[][] playerField) {
        PlayerShips.playerField = playerField;
    }
    public void setPlayerShips(int[] playerShips) {
        PlayerShips.playerShips = playerShips;
    }
    public void setPlayerTest(boolean playerTest) {
        this.playerTest = playerTest;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
// Получаем координаты нажатой кнопки, заносим в массив кораблей пользователя
        if (countOfPlayerShips < 20) {
            JButton button = (JButton) event.getSource();
            int row = Integer.parseInt(event.getActionCommand().substring(0,1));
            int column = Integer.parseInt(event.getActionCommand().substring(1));
            if (countOfPlayerShips <= 3) {
                button.setBackground(new Color(65, 105, 225));
            }
            if (countOfPlayerShips > 3 && countOfPlayerShips < 10) {
                button.setBackground(new Color(30, 144, 255));
            }
            if (countOfPlayerShips >= 10 && countOfPlayerShips < 16){
                button.setBackground(new Color(0, 191, 255));
            }
            if (countOfPlayerShips >= 16){
                button.setBackground(new Color(135, 206, 235));
            }
            if (playerTest) {
                GraphicsInterface.clearHintOfGame();
                GraphicsInterface.appendHintOfGame("  Подсказки и ход игры:\n");
            }
            if (playerField[row][column] != 1) {
                playerField[row][column] = 1;
// Сохраняем координаты кораблей игрока в отдельный массив playerShips
                playerShips[countOfPlayerShips] = Integer.parseInt(event.getActionCommand());
                countOfPlayerShips++;
            } else {
                GraphicsInterface.appendHintOfGame("   Эта ячейка уже занята!\n");
                GraphicsInterface.appendHintOfGame("   Укажите другую координату!\n");
            }
// Проверяем правильность расстановки кораблей игрока на игровом поле
            switch (countOfPlayerShips) {
                case 4: {
                    testAndClear(0, countOfPlayerShips - 1, "Авианосец");
                    break;
                }
                case 7: {
                    testAndClear(4, countOfPlayerShips - 1, "Линкор");
                    break;
                }
                case 10: {
                    testAndClear(7, countOfPlayerShips - 1, "Линкор");
                    break;
                }
                case 12: {
                    testAndClear(10, countOfPlayerShips - 1, "Крейсер");
                    break;
                }
                case 14: {
                    testAndClear(12, countOfPlayerShips - 1, "Крейсер");
                    break;
                }
                case 16: {
                    testAndClear(14, countOfPlayerShips - 1, "Крейсер");
                    break;
                }
                case 17: {
                    testAndClear(16, countOfPlayerShips - 1, "Эсминец");
                    break;
                }
                case 18: {
                    testAndClear(17, countOfPlayerShips - 1, "Эсминец");
                    break;
                }
                case 19: {
                    testAndClear(18, countOfPlayerShips - 1, "Эсминец");
                    break;
                }
                case 20: {
                    testAndClear(19, countOfPlayerShips - 1, "Эсминец");
                    break;
                }
            }
        } else {
            GraphicsInterface.clearHintOfGame();
            GraphicsInterface.appendHintOfGame("  Подсказки и ход игры:\n   Вы уже установили все корабли!\n" +
                    "   Нажмите кнопку \"Вывести боевой флот \n компьютера\"!\n");
        }
// Если все корабли уже установлены, делаем доступной кнопку "Вывести боевой флот компьютера", делаем доступными
// кнопки игрового поля компьютера, и запрещаем кнопки игрового поля пользователя
        if (countOfPlayerShips == 20) {
            computerConfigurationButton.setEnabled(true);
            for (JButton button: computerButtons) {
                button.setEnabled(true);
            }
            for (JButton button: playerButtons) {
                button.setEnabled(false);
            }
            GraphicsInterface.clearHintOfGame();
            GraphicsInterface.appendHintOfGame("  Подсказки и ход игры:\n Нажмите кнопку ВЫВЕСТИ БОЕВОЙ\n" +
                    " ФЛОТ КОМПЬЮТЕРА\n");
        }
    }
// Метод очистки неправильно поставленного корабля
    private void clearShip(int start, int end) {
        for (int i = start; i <= end ; i++) {
            playerField[playerShips[i]/10][playerShips[i]%10] = 0;
            playerButtons.get(playerShips[i]).setBackground(Color.LIGHT_GRAY);
            playerButtons.get(playerShips[i]).setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
    }
// Метод проверки расстановки  и очистки корабля игрока
    private void testAndClear(int start, int end, String s) {
        playerTest = shipTest(start, end);
        if (!playerTest) {
            GraphicsInterface.clearHintOfGame();
            appHintOfGameIfIncorrect(s);
            clearShip(start, end);
            countOfPlayerShips = start;
            playerTest = true;
        }
    }
// Метод проверки правильности расстановки игроком своих кораблей (возвращает true, если корабль
// поставлен правильно, иначе - false)
// Параметры метода - начальный индекс координаты корабля, конечный индекс координаты корабля в массиве кораблей
    private boolean shipTest(int begin, int end) {
        int test;
        int[] ship = Arrays.copyOfRange(playerShips,begin, end + 1);
        Arrays.sort(ship);
// Проверка правильности установки корабля по горизонтали и вертикали
        if (ship.length != 1) {
        for (int i = 0; i < ship.length -1 ; i++) {
            if ((ship[i+1] - ship[i] != 1) && (ship[i+1] - ship[i] != 10)) return false;
        }
        }
// Проверка кораблей на совмещение (если корабль совмещается с другим - возвращается false)
        // Если корабль располагается по горизонтали
        if (ship[0]/10 == ship[ship.length-1]/10) {
            if ((ship[0] - 10) >= 0) {
                for (int aShip : ship) {
                    if (playerField[(aShip - 10) / 10][(aShip - 10) % 10] == 1) return false;
                }
            }
            if ((ship[0] + 10) <= 99) {
                for (int aShip : ship) {
                    if (playerField[(aShip + 10) / 10][(aShip + 10) % 10] == 1) return false;
                }
            }
            if (ship[0]%10 != 0) {
               test = (ship[0]-1)-10;
                for (int i = 0; i < 3 ; i++) {
                    if (test < 0 || test >= 100) {
                        test += 10;
                        continue;
                    }
                    if (playerField[test/10][test%10] == 1) return false;
                    test += 10;
                }
            }
            if (ship[ship.length-1]%10 != 9) {
                test = (ship[ship.length-1]+1)-10;
                for (int i = 0; i < 3; i++) {
                    if (test < 0 || test >= 100) {
                        test += 10;
                        continue;
                    }
                    if (playerField[test/10][test%10] == 1) return false;
                    test += 10;
                }
            }
        }
        // Если корабль располагается по вертикали
        if (ship[0]%10 == ship[ship.length-1]%10) {
            if (ship[0]%10 !=9) {
                for (int aShip : ship) {
                    if (playerField[(aShip + 1) / 10][(aShip + 1) % 10] == 1) return false;
                }
            }
            if (ship[0]%10 != 0) {
                for (int aShip : ship) {
                    if (playerField[(aShip - 1) / 10][(aShip - 1) % 10] == 1) return false;
                }
            }
            if (ship[0]/10 != 0) {
                test = (ship[0]-1)-10;
                for (int i = 0; i < 3; i++) {
                    if (test < 0 || Math.abs(test%10 - ship[0]%10) > 1 ) {
                        test += 1;
                        continue;
                    }
                    if (playerField[test/10][test%10] == 1) return false;
                    test += 1;
                }
            }
            if (ship[ship.length-1]/10 != 9) {
                test = (ship[ship.length-1]-1) + 10;
                for (int i = 0; i < 3; i++) {
                    if (test > 100 || Math.abs(test%10 - ship[ship.length-1]%10) > 1 ) {
                        test += 1;
                        continue;
                    }
                    if (playerField[test/10][test%10] == 1) return false;
                    test += 1;
                }
            }
        }
        return true;
    }
// Метод добавления текста в поле подсказки игры (hintOfGame), если неправильно поставлен корабль
    private void appHintOfGameIfIncorrect(String s) {
        GraphicsInterface.appendHintOfGame("  Подсказки и ход игры:\n Неправильно поставлен корабль:\n");
        GraphicsInterface.appendHintOfGame(" " + s + "!\n Еще раз расставьте корабль!\n");
    }
// Метод получения игрового поля игрока
    public static int[][] getPlayerField() {
        return playerField;
    }
// Метод получения координат кораблей игрока
    public static int[] getPlayerShips() {
        return playerShips;
    }
}

