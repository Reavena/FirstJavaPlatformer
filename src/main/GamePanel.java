package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;


import javax.imageio.ImageIO;
import javax.swing.*;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import inputs.TextInputs;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;
    private Game game;
    private JTextField inputField; // Added text input field !!

    //
    int move_left = KeyEvent.VK_A;
    Robot r = new Robot();


    public GamePanel(Game game) throws AWTException {
        mouseInputs = new MouseInputs(this);
        keyboardInputs = new KeyboardInputs(this);
        this.game = game;

        setPanelSize();
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

        // Initialize and add text input field
        inputField = new JTextField();
        inputField.setBounds(10, GAME_HEIGHT - 40, GAME_WIDTH - 20, 40);
        setLayout(null);
        add(inputField);

        // Add an ActionListener to handle user input
        inputField.addActionListener(e -> {
            try {
                handleUserInput();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (AWTException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("size :" + GAME_WIDTH + " : " + GAME_HEIGHT);
    }

    public void updateGame() {
        // Update game logic here
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.white);
        for (int i = 0; i < 64; i++)
            for (int j = 0; j < 40; j++)
                g.fillRect(i * 20, j * 20, 20, 20);

        game.render(g);
    }

    public Game getGame() {
        return game;
    }

    private void handleUserInput() throws InterruptedException, AWTException {
        // Get the text entered by the user
        String userInput = inputField.getText();
        // Process or use the userInput as needed
        System.out.println("User entered: " + userInput);
        //
/*
        if(userInput =="move_left")
        {
            System.out.println("Moving left...");
            r.keyPress(move_left);
            wait(1000);
            r.keyRelease(move_left);
        }
*/
        new TextInputs(userInput, keyboardInputs);
        // Clear the input field after processing
        inputField.setText("");
    }
}
