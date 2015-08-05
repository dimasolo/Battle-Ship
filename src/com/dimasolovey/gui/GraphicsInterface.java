package com.dimasolovey.gui;

import com.dimasolovey.computer.ComputerShips;
import com.dimasolovey.game.StartAgain;
import com.dimasolovey.player.PlayerShips;
import com.dimasolovey.player.UserPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dmitry.solovey on 02.07.2015.
 */
public class GraphicsInterface {
// Фрейм, шрифты для кнопок, полей и текстовой области
    private static JFrame frame;
    private Font buttonFont = new Font("sanserif", Font.ITALIC, 21);
    private Font fieldsFont = new Font("sanserif", Font.BOLD, 18);
    private Font hintTextAreaFont = new Font("sanserif", Font.ITALIC, 11);
// Текстовые области "Игрок" и "Компьютер"
    private JTextField playerTextField = new JTextField("ИГРОК");
    private JTextField computerTextField = new JTextField("КОМПЬЮТЕР");
// Текстовые области подсказок и правил игры, кнопки "Начать заново" и "Вывести боевой флот компьютера"
    private JTextArea hintOfRules = new JTextArea(15,20);
    private static JTextArea hintOfGame = new JTextArea(5,10);
    private static JButton computerConfigurationButton = new JButton();
    private JButton startAgain = new JButton("НАЧАТЬ ЗАНОВО");
// Массивы для хранения кнопок игрока и компьютера
    private static ArrayList<JButton> playerButtons = new ArrayList<JButton>();
    private static ArrayList<JButton> computerButtons = new ArrayList<JButton>();

    public void biuldGUI() {
// Создаем фрейм, главную игровую панель, панели игрока и компьютера, "северную" и "южную" панели и остальные панели
// Добавляем кнопки, текстовые области, метки
        frame = new JFrame("Морской Бой");
        JPanel mainGamePanel = new JPanel(new GridLayout(1, 2));
        JPanel playerPanel = new JPanel(new GridLayout(10, 10));
        JPanel computerPanel = new JPanel(new GridLayout(10, 10));
        JPanel northPanel = new JPanel(new GridLayout(1, 2));
        playerTextField.setHorizontalAlignment(JTextField.CENTER);
        playerTextField.setFont(fieldsFont);
        computerTextField.setHorizontalAlignment(JTextField.CENTER);
        computerTextField.setFont(fieldsFont);
        northPanel.add(playerTextField);
        northPanel.add(computerTextField);
        JPanel southPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        computerConfigurationButton.setFont(buttonFont);
        computerConfigurationButton.setLayout(new GridLayout(4,1));
        computerConfigurationButton.addActionListener(ComputerShips.getInstance());
        JLabel northLabel = new JLabel("ВЫВЕСТИ БОЕВОЙ");
        northLabel.setFont(buttonFont);
        northLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel emptyLabel = new JLabel("");
        JLabel southLabel = new JLabel("ФЛОТ КОМПЬЮТЕРА");
        southLabel.setFont(buttonFont);
        southLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startAgain.setFont(buttonFont);
        startAgain.addActionListener(new StartAgain());
        computerConfigurationButton.add(emptyLabel);
        computerConfigurationButton.add(northLabel);
        computerConfigurationButton.add(southLabel);
        computerConfigurationButton.setEnabled(false);
        buttonPanel.add(computerConfigurationButton);
        buttonPanel.add(startAgain);
        JScrollPane scrollPaneOfRules = new JScrollPane(hintOfRules);
        hintOfRules.setFont(hintTextAreaFont);
        hintOfRules.setLineWrap(true);
        hintOfRules.setWrapStyleWord(true);
        hintOfRules.append("   Правила игры:\n\r");
        hintOfRules.append("   Расставьте в поле игрока свои корабли в такой последовательности:\n");
        hintOfRules.append("   - Один корабль на четыре клетки (авианосец);\n");
        hintOfRules.append("   - Два корабля на три клетки (линкоры);\n");
        hintOfRules.append("   - Три корабля на две клетки (крейсеры);\n");
        hintOfRules.append("   - Четыре корабля на одну клетку (эсминцы);\n");
        hintOfRules.append("   Корабли можно ставить по горизонтали и вертикали, но не по диагонали.\n");
        hintOfRules.append("   Корабли не могут совмещаться друг с другом.\n");
        hintOfRules.append("   После расстановки своих кораблей для начала игры нажмите кнопку" +
                " \"Вывести боевой флот компьютера\".\n");
        hintOfRules.append("   Дождитесь появления в окне подсказок \"Ваш ход!\". Желаем победы!");
        hintOfRules.setCaretPosition(0);
        JScrollPane scrollPaneOfGame = new JScrollPane(hintOfGame);
        scrollPaneOfGame.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        hintOfGame.setBackground(Color.WHITE);
        hintOfGame.append("  Подсказки и ход игры:\n Расставьте свои корабли!\n");
        southPanel.add(scrollPaneOfRules);
        southPanel.add(scrollPaneOfGame);
        southPanel.add(buttonPanel);
// Инициализируем кнопки для игрока и компьютера,добавляем слушателя событий,добавляем кнопки
// на панель и в массив кнопок игрока и компьютера
        for (int i = 0; i < 10 ; i++) {
            for (int j = 0; j < 10 ; j++) {
                JButton playerButton = new JButton();
                playerButton.setActionCommand(Integer.toString(i) + Integer.toString(j));
                playerButton.addActionListener(PlayerShips.getInstance());
                playerButton.setBackground(Color.LIGHT_GRAY);
                playerButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                playerButtons.add(playerButton);
                playerPanel.add(playerButton);
                JButton computerButton = new JButton();
                computerButton.setActionCommand(Integer.toString(i) + Integer.toString(j));
                computerButton.addActionListener(UserPlayer.getInstance());
                computerButton.setBackground(Color.LIGHT_GRAY);
                computerButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                computerButton.setEnabled(false);
                computerButtons.add(computerButton);
                computerPanel.add(computerButton);
            }
        }
// Добавляем на главную игровую панель панели игрока и компьютера, устанавливаем размер фрейма и его видимость
        mainGamePanel.add(playerPanel);
        mainGamePanel.add(computerPanel);
        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.CENTER, mainGamePanel);
        frame.getContentPane().add(BorderLayout.SOUTH, southPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setVisible(true);
    }
// Метод очистки поля подсказки игры (hintOfGame)
    public static void clearHintOfGame() {
        hintOfGame.setText("");
    }
// Метод добавления текста в поле подсказки игры (hintOfGame)
    public static void appendHintOfGame(String s) {
        hintOfGame.append(s);
    }
// Метод получения кнопок поля игрока
    public static ArrayList<JButton> getPlayerButtons() {
        return playerButtons;
    }
// Метод получения кнопок поля компьютера
    public static ArrayList<JButton> getComputerButtons() {
        return computerButtons;
    }
// Метод получения кнопки конфигурации компьютера
    public static JButton getComputerConfigurationButton() {
        return computerConfigurationButton;
    }
// Метод получения фрейма
    public static JFrame getFrame() {
        return frame;
    }
}
