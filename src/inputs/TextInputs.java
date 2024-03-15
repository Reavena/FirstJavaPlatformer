package inputs;

//AVAILABLE COMMANDS:
//start_game
// move_left
//move_right
//jump
//escape_game
// for a b      , where for (int a, a < b; a++)
// nested loop works
//if a condition   ,where a is the a in for loop and
// where conditions can be
// == , < , > , <= , >= , %



import inputs.MouseInputs;
import inputs.KeyboardInputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.security.Key;

public class TextInputs {

    private java.awt.event.KeyEvent KeyEvent;

    int instruction;
    int instruction2;
    private KeyboardInputs keyboardInputs;

    boolean condition = false;


    String[] words;

    public TextInputs(String text, KeyboardInputs keyboardInputs) throws AWTException, InterruptedException {
        this.keyboardInputs = keyboardInputs;
        System.out.println("Text Input read " + text);

        words = text.split("\\s+");

        System.out.println("Individual words:");
        System.out.println("----------------------------");
        for (String word : words) {
            System.out.println(word);
        }
        System.out.println("----------------------------");


        simulateCommand(0);
    }

    private void simulateCommand(int currentIndex) throws InterruptedException {
        System.out.println(words[currentIndex]);
        switch (words[currentIndex]) {
            case "for":
                System.out.println("for loop");
                executeForLoop(currentIndex);
                break;
//  can change to immediately do the movements instead of calling keyboardInputs
            case "move_left":
                instruction = KeyEvent.VK_A;
                break;

            case "move_right":
                instruction = KeyEvent.VK_D;
                break;

            case "jump":
                instruction = KeyEvent.VK_SPACE;
                break;

            case "start_game":
                instruction = KeyEvent.VK_ENTER;
                break;

            case "pause_game":
                instruction = KeyEvent.VK_ESCAPE;
                break;

            default:
                break;
        }

        if (!words[currentIndex].equals("for") && !words[currentIndex].equals("while")) {
            // Create a Timer with a delay of 20 milliseconds
            Timer timer = new Timer(20, new ActionListener() {
                private boolean keyPressed = false;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!keyPressed) {
                        simulateKeyPress(instruction);
                        keyPressed = true;
                    } else {
                        simulateKeyRelease(instruction);
                        ((Timer) e.getSource()).stop();

                    }
                }
            });

            // Start the timer
            timer.start();
        }
    }


    private void simulate2Commands(String input1, String input2) throws InterruptedException {
        System.out.println(input1 + input2);
        switch (input1) {
            case "for":
                System.out.println("MISTAKE");
                break;

            case "move_left":
                instruction = KeyEvent.VK_A;
                break;

            case "move_right":
                instruction = KeyEvent.VK_D;
                break;

            case "jump":
                instruction = KeyEvent.VK_SPACE;
                break;

            case "start_game":
                instruction = KeyEvent.VK_ENTER;
                break;

            case "pause_game":
                instruction = KeyEvent.VK_ESCAPE;
                break;

            default:
                break;
        }
        switch (input2) {
            case "for":
                System.out.println("MISTAKE WITH INPUT2");
                break;

            case "move_left":
                instruction2 = KeyEvent.VK_A;
                break;

            case "move_right":
                instruction2 = KeyEvent.VK_D;
                break;

            case "jump":
                instruction2 = KeyEvent.VK_SPACE;
                break;

            case "start_game":
                instruction2 = KeyEvent.VK_ENTER;
                break;

            case "pause_game":
                instruction2 = KeyEvent.VK_ESCAPE;
                break;

            default:
                break;
        }

        if (!input1.equals("for")) {
            // Create a Timer with a delay of 20 milliseconds
            Timer timer = new Timer(20, new ActionListener() {
                private boolean keyPressed = false;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!keyPressed) {
                        simulateKeyPress(instruction);
                        simulateKeyPress(instruction2);
                        keyPressed = true;
                    } else {
                        simulateKeyRelease(instruction);
                        simulateKeyRelease(instruction2);
                        ((Timer) e.getSource()).stop();

                    }
                }
            });

            // Start the timer
            timer.start();
        }
    }

    private void executeForLoop(int startIndex) throws InterruptedException {
        if (words[startIndex].equals("for")) {
            startIndex++;
        }
        int i = Integer.parseInt(words[startIndex]);
        int j = Integer.parseInt(words[startIndex + 1]);

        if (words[startIndex + 2].equals("if")) {
            condition = true;
            startIndex = startIndex + 3;
        }

        for (int x = i; x < j; x++) {
            if ((evaluateCondition(x, words[startIndex], words[startIndex + 1]) && condition) || !condition) {
                // Condition is met, execute the nested command;
                System.out.println("Condition");

                for (int y = startIndex + 2; y < words.length; y++) {

                   if (y + 1 < words.length && !words[y].equals("for")) {
                    simulate2Commands(words[y], words[y + 1]);
                    System.out.println(x + " " + words[y] + " " + words[y + 1]);
                    y++;
                } else {
                    if (words[y].equals("for")) {
                        System.out.println("Nested for");
                        executeForLoop(y + 1); // Start from the index after "for"
                    } else {
                        simulateCommand(y);
                        System.out.println(x + " " + words[y]);
                    }
                }
            }
        }
    }

}

        // Evaluate a condition based on the comparison operator

            private boolean evaluateCondition(int variableValue, String operator, String conditionValue) {
                int valueToCompare = Integer.parseInt(conditionValue);
                switch (operator) {
                    case "==":
                        return variableValue == valueToCompare;

                    case ">":
                        return variableValue > valueToCompare;

                    case "<":
                        return variableValue < valueToCompare;

                    case ">=":
                        return variableValue >= valueToCompare;

                    case "<=":
                        return variableValue <= valueToCompare;

                    case "%":
                        return variableValue % valueToCompare == 0;

                    // Add more cases for other operators as needed
                    default:
                        return false;
                }
            }




    private void simulateKeyPress(int keyCode) {
        keyboardInputs.keyPressed(new KeyEvent(new Component() {}, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode, (char) 0));
    }

    private void simulateKeyRelease(int keyCode) {
        keyboardInputs.keyReleased(new KeyEvent(new Component() {}, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, keyCode, (char) 0));
    }
}
