package com.dimasolovey.game;

import com.dimasolovey.computer.ComputerPlayer;
import com.dimasolovey.computer.ComputerShips;
import com.dimasolovey.gui.GraphicsInterface;
import com.dimasolovey.player.UserPlayer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dmitry.solovey on 09.07.2015.
 */

// Класс для отслеживания рузультатов игры
public class GameViewer {
    private static ComputerPlayer computerPlayer = ComputerPlayer.getInstance();
    private static UserPlayer userPlayer = UserPlayer.getInstance();
    public static void viewGame() {
// Если игрок попал по всем кораблям - выводим подсказку о победе и диалоговое окно
        if (userPlayer.getCountOfPlayerHits() == 20) {
            GraphicsInterface.appendHintOfGame("  Поздравляем!!! Победа! Вы потопили\n флот компьютера!\n");
            JOptionPane.showMessageDialog(GraphicsInterface.getFrame(), "Поздравляем!!! Победа! Вы потопили флот компьютера!");
            for (JButton button : GraphicsInterface.getComputerButtons()) {
                button.setEnabled(false);
            }
        }
// Если компьютер попал по всем кораблям - выводим подсказку о победе и диалоговое окно
        if (computerPlayer.getCountOfComputerHits() == 20) {
            GraphicsInterface.appendHintOfGame("  Увы... На этот раз сильнее компьютер!\n Попробуйте еще раз!\n");
            JOptionPane.showMessageDialog(GraphicsInterface.getFrame(), "Увы... На этот раз сильнее компьютер! Попробуйте еще раз!");
            for (JButton button : GraphicsInterface.getComputerButtons()) {
                button.setEnabled(false);
                if (ComputerShips.getComputerField()[Integer.parseInt(button.getActionCommand().substring(0,1))]
                        [Integer.parseInt(button.getActionCommand().substring(1))] == 1 &&
                        button.getBackground() != Color.MAGENTA && button.getBackground() != Color.WHITE) {
                    button.setBackground(Color.BLUE);
                }
            }
        }
        if (userPlayer.getCountOfPlayerHits() < 20 && !userPlayer.getFlagOfPlayerHit()) {
            computerPlayer.computerMove();
        }
    }
}
