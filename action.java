package javaFinal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;

public class action extends JFrame implements ActionListener {
    static String inputText;
    JFrame frame = new JFrame("Sci-Fi Movie Suggester");
    JPanel buttons = new JPanel();
    JPanel inputBox = new JPanel(new BorderLayout());
    JPanel inputLine = new JPanel();
    static JPanel info = new JPanel();
    
    JButton clear = new JButton("Clear");
    JButton submit = new JButton("Submit");
    
    static JLabel questionLabel = new JLabel("");
    static JLabel extra = new JLabel("");
    static JTextArea infoText = new JTextArea("");
    static JTextField input = new JTextField("", 5);
    static ImageIcon imageIcon = new ImageIcon("");
    static JLabel imageLabel = new JLabel();

    public action(){
        frame.setSize(550, 300);
        inputBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buttons.add(clear);
        buttons.add(submit);
        frame.getContentPane().add(BorderLayout.CENTER, buttons);
        
        infoText.setEditable(false);
        infoText.setVisible(false);
        extra.setHorizontalAlignment(JLabel.CENTER);
        
        info.add(BorderLayout.NORTH, imageLabel);
        info.add(BorderLayout.SOUTH, infoText);

        inputLine.add(questionLabel);
        inputLine.add(input);
        inputBox.add(BorderLayout.NORTH, inputLine);
        inputBox.add(BorderLayout.CENTER, extra);
        inputBox.add(BorderLayout.SOUTH, info);
        frame.getContentPane().add(BorderLayout.NORTH, inputBox);
        
        frame.setVisible(true);
        clear.addActionListener(this);
        submit.addActionListener(this);
    }
    public static void main(String args[]) throws Exception {
        action frame = new action();
        questionLabel.setText("Do you have a Driver's Liscense?");
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();  //find out the name of the component
        
        if (command.equals("Clear")) {     
            input.setText("");
        } 
    }


    public static boolean YNValidation(String input) {
        boolean stay = true;
        while (stay) { // repeat until input is valid
            if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("ya") || input.equalsIgnoreCase("yeah") ) {
                extra.setText("");
                return true; 
            } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("nope")) {
                extra.setText("");
                return false;
            }
            
            // ask user to enter valid input
            extra.setText("Please enter Y or N.");   
            input = inputText;         
        }
        return false;
    }

}