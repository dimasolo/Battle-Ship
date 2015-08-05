package com.dimasolovey.game;

import com.dimasolovey.computer.ComputerPlayer;
import com.dimasolovey.computer.ComputerShips;
import com.dimasolovey.gui.GraphicsInterface;
import com.dimasolovey.player.PlayerShips;
import com.dimasolovey.player.UserPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dmitry.solovey on 15.07.2015.
 */

public class StartAgain implements ActionListener {
// Экземпляры классов игрока и компьютера, кораблей игрока и компьютера
    ComputerShips computerShips = ComputerShips.getInstance();
    PlayerShips playerShips = PlayerShips.getInstance();
    UserPlayer userPlayer = UserPlayer.getInstance();
    ComputerPlayer computerPlayer = ComputerPlayer.getInstance();
    public void actionPerformed(ActionEvent event) {
// Очищаем текстовую область подсказки, переменные попаданий и ходов, массивы координат кораблей игрока и компьютера
        GraphicsInterface.clearHintOfGame();
        GraphicsInterface.appendHintOfGame("  Подсказки и ход игры:\n");
        GraphicsInterface.appendHintOfGame("  Расставьте свои корабли!\n");
        computerShips.setComputerField(new int[10][10]);
        computerShips.clearCoordsOfShip();
        computerShips.clearCoordsOfShips();
        computerShips.clearCoordsOfOccup();
        computerShips.setCountOfCoords(0);
        GraphicsInterface.getComputerConfigurationButton().setEnabled(false);
        for (JButton button: GraphicsInterface.getPlayerButtons()) {
            button.setText("");
            button.setEnabled(true);
            button.setBackground(Color.LIGHT_GRAY);
            button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
        for (JButton button: GraphicsInterface.getComputerButtons()) {
            button.setText("");
            button.setEnabled(false);
            button.setBackground(Color.LIGHT_GRAY);
            button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
        playerShips.setCountOfPlayerShips(0);
        playerShips.setPlayerField(new int[10][10]);
        playerShips.setPlayerShips(new int[20]);
        playerShips.setPlayerTest(true);
        userPlayer.setCountOfPlayerHits(0);
        userPlayer.setCountOfPlayerMoves(0);
        userPlayer.setFlagOfPlayerHit(false);
        userPlayer.setFlagsOfSankShips(new boolean[10]);
        userPlayer.clearCoordsForComputerShipsOc();
        userPlayer.clearCoordsOfComputerShips();
        computerPlayer.setFlagsOfSankShips(new boolean[10]);
        computerPlayer.setCoordOfComputerMove(0);
        computerPlayer.setCountOfComputerHits(0);
        computerPlayer.setCountOfComputerMoves(0);
        computerPlayer.setCount(0);
        computerPlayer.clearCoordsOfComputerHits();
        computerPlayer.clearCoordsOfComputerMoves();
        computerPlayer.clearRecomendation_pool();
    }
}
