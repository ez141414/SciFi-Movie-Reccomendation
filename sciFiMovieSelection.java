// Author: Emma Zhang
// Teacher: Mr. A
// Date: 12/11/2023
// ICS3U - Recommender Project: picking the best sci-fi movie

package javaFinal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

public class sciFiMovieSelection extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	// declare global variables
    static PrintWriter pw;    
    static String fp = "./img/"; // fp = file path

    // question related variables/arrays
    static String movies[] = {"Wall-e(2008)", "Gravity(2013)", "The Wandering Earth(2019)", "The Martian(2015)", "The Star Wars Prequel(1999-2005)", "Interstellar(2014)", "Your Name(2016)", "Edge of Tomorrow(2014)", "Back to The Future(1985)", "The Matrix(1985 - 2021)", "Blade Runner", "Blade Runner 2049(2017)", "Ex Machina(1982)", "Ender's Game(2013)", "The Terminator(1984)", "Tenet(2020)", "Inception(2010)", "Everything Everywhere All at Once(2022)", "Children of Men(2006)"};
    static String images[] = {fp + "walle.jpeg", fp +  "gravity.jpg", fp +  "wandaringearth.jpg", fp +  "themartian.jpg", fp +  "starwars.jpg", fp +  "interstellar.jpg", fp +  "yourname.jpg", fp +  "edgeof.jpg", fp +  "backtofuture.jpg", fp +  "matrix.jpg", fp + "bladerunner.jpg", fp +  "bladerunner2049.jpg", fp +  "exmachina.jpg", fp +  "endersgame.jpg", fp + "terminator.jpeg", fp + "tenet.jpg",fp + "inception.jpg", fp +  "everything.jpg", fp + "children.jpg"};
    static Boolean[][] movieData = {{true, false, true, false, true, false, false, false}, {true, false, true, false, false, false, false, false}, {true, false, true, false, false, false, false, true},{false, false, true, false, false, false, false, false}, {true, true, true, false, false, false, false, true}, {false, false, true, true, false, false, true, false}, {true, false, false, false, false, false, true, false}, {true, false, false, true, false, false, false, false}, {true, true, false, true, false, false, false, true}, {true, true, false, false, true, true, true, true}, {true, true, false, false, true, true, false, true}, {false, false, false, false, true, true, false, true}, {true, false, false, false, true, true, false, false}, {true, false, true, false, false, false, false, false}, {true, true, false, true, true, true, false, true}, {false, false, false, true, false, false, true, false}, {false, false, false, false, false, false, true, false}, {true, false, false, false, false, false, true, false}, {true, false, false, false, false, true, false, false}};
    static String questionImage[] = {fp + "scifi.jpg" , fp + "time.jpg", fp + "pre2000s.jpeg", fp + "spacegenre.jpg", fp + "timetravel.jpeg", fp + "robotgenre.jpeg", fp + "dystopian.jpg", fp + "complex.png", fp + "sequel.jpg", ""};
    static int questionAmount = 10;
    static String[] stringUserInput = new String[questionAmount];
    
    // other variables
    static int suggestion;
    static boolean ask;
    static int[] points = new int[movies.length];
    static boolean first = true;
    static boolean last = false;
    static int questionNum = 0;
    static boolean[] userAnswer = new boolean[questionAmount - 2];
    static boolean done = false;
    
    static String command;
    static String inputText;

    // declare GUI
    JFrame frame = new JFrame("Sci-Fi Movie Suggester");
    JPanel buttons = new JPanel();
    JPanel inputBox = new JPanel(new BorderLayout());
    static JPanel inputLine = new JPanel();
    static JPanel info = new JPanel();
    
    static JButton back = new JButton("Back");
    static JButton submit = new JButton("Submit");
    
    static JLabel questionLabel = new JLabel("");
    static JLabel extra = new JLabel("");
    static JTextArea infoText = new JTextArea("");
    static JTextField input = new JTextField("", 2);
    static ImageIcon imageIcon = new ImageIcon("");
    static JLabel imageLabel = new JLabel();

    public sciFiMovieSelection(){
        // add GUI components to content page
        Color cyan = new Color(224, 255, 255);
        Color background = Color.BLACK;
        frame.setSize(550, 300);
        inputBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // background colour
        inputBox.setBackground(background);
        buttons.setBackground(background);
        inputLine.setBackground(background);
        info.setBackground(background);
        infoText.setBackground(new Color(1, 58, 69));

        // text colour
        input.setBackground(background);
        questionLabel.setForeground(cyan);
        infoText.setForeground(cyan);
        input.setForeground(cyan);
        extra.setForeground(cyan);
        
        // button
        submit.setForeground(cyan);
        back.setForeground(cyan);
        submit.setBackground(new Color(1, 58, 69));
        back.setBackground(new Color(1, 58, 69));

        buttons.add(back);
        buttons.add(submit);
        frame.getContentPane().add(BorderLayout.CENTER, buttons);
        
        // info text
        infoText.setEditable(false);
        infoText.setVisible(false);
        extra.setHorizontalAlignment(JLabel.CENTER);
        
        // adding components to GUIs
        info.add(BorderLayout.NORTH, imageLabel);
        info.add(BorderLayout.SOUTH, infoText);
        imageIcon = new ImageIcon("./img/scifi.jpg");
        imageResize(200, 120);

        inputLine.add(questionLabel);
        inputLine.add(input);
        inputBox.add(BorderLayout.NORTH, inputLine);
        inputBox.add(BorderLayout.CENTER, extra);
        inputBox.add(BorderLayout.SOUTH, info);
        frame.getContentPane().add(BorderLayout.NORTH, inputBox);
        
        frame.setVisible(true);
        back.addActionListener(this);
        submit.addActionListener(this);
    }

    public static void main(String args[]) throws Exception {
        new sciFiMovieSelection();
        pw = new PrintWriter("result.txt");
        back.setVisible(false);
        // ask user if they would like a movie suggestion
        questionLabel.setText(readQuestion(0));
     }

    // listens for event
    public void actionPerformed(ActionEvent event) {
        command = event.getActionCommand();  //find out the name of the component
        if (command.equals("Submit") && !done) {
            // validates input text
            inputText = input.getText();
            if (YNValidation(inputText)) {
            	stringUserInput[questionNum] = inputText;
            	
                if (questionNum == 0) { // check if first question
                    if (yesOrNo(inputText)) { // initialize to start asking questions
                        ask = true;
                        questionNum += 1;
						questionLabel.setText(readQuestion(questionNum));
                        imageIcon = new ImageIcon(questionImage[questionNum]); 
                        imageResize(200, 120);
                        back.setVisible(true);
                    } else { // stop program
                        hideEnd(); // invoke end function
                        extra.setText("Thank you for checking out this program!");
                    }
                        
                } else if (questionNum > 0 && questionNum < questionAmount - 2) { // scifi questions
                    userAnswer[questionNum - 1] = yesOrNo(inputText); // set answer
                    questionNum += 1; // increment question
                    if (questionNum == 2) { // show back button after first question after the first round of quesetions
                    	back.setVisible(true);                		
                    }
                    
                    // set next question UI
                    questionLabel.setText(readQuestion(questionNum));
                    imageIcon = new ImageIcon(questionImage[questionNum]);
                    imageResize(200, 120);
                    
                } else if (questionNum == questionAmount - 2) {
                    points = new int[movies.length];
                    userAnswer[questionNum - 1] = yesOrNo(inputText);
                    questionNum += 1;
                    // get suggestion from compare points function
                    suggestion = compare();
                    
                    // set next question UI
                    questionLabel.setText(readQuestion(questionNum));
                    imageIcon = new ImageIcon(images[suggestion]);
                    imageResize(80, 120);

                    infoText.setVisible(true);
                    double matchPts = points[suggestion];
                    double fullPts = userAnswer.length;
                    // give suggestion and match % and write to txt file
                    pw.println("\nI suggest you watch " + movies[suggestion] + ".\n" + Math.round((matchPts/fullPts)*100) + "% match, questions with '**' are matches.");
                    infoText.setText("I suggest you watch " + movies[suggestion] + ".\n" + Math.round((matchPts/fullPts)*100) + "% match, questions with '**' are matches.");
                    String answer;
                    String same;
                    // tell the user their answer matches, checking if answers match, and indicating with '**'
                    for (int i = 0; i < userAnswer.length; i++) {
                        if(userAnswer[i]) {
                            answer = "yes";
                        } else {
                            answer = "no";
                        }
                        if(userAnswer[i] == movieData[suggestion][i]) {
                            same = "**";
                        } else {
                            same = "";
                        }
                        questionLabel.setText(readQuestion(questionNum));
                        pw.println("You answered [" + answer + "] to " + same + readQuestion(i+1));
                        infoText.setText(infoText.getText() + "\nYou answered [" + answer + "] to " + same + readQuestion(i+1));
                    }
                    
                } else if (questionNum == questionAmount - 1) { // instructions for second last question to prepare for last question
                    if (yesOrNo(inputText)) {
                    	pw.println();
                    	reset();
                    	first = false;
                        questionNum = 1;
                        imageIcon = new ImageIcon(questionImage[questionNum]);
                        imageResize(200, 120);
                        back.setVisible(false);
                    } else {
                        hideEnd();
                        extra.setText("Thank you for checking out this program!");
                    }
                    questionLabel.setText(readQuestion(questionNum));
                } 
            }
            
            if (stringUserInput.length - 1 > questionNum ) {
            	input.setText(stringUserInput[questionNum]); // setting input box text to previously entered text when back is clicked 
            } else {
            	input.setText("");     
            }
        } 
        
        if (command.equals("Back")) {  // go back input box
        	infoText.setText("");
        	extra.setText("");
        	if (questionNum != 0) {
	    		questionNum -= 1;
	    		imageIcon = new ImageIcon(questionImage[questionNum]);
	    		imageResize(200, 120);
	    		questionLabel.setText(readQuestion(questionNum));
        	}
        	if (questionNum == 0){ // make sure back isn't visible on the first questions
                back.setVisible(false);
        	}
            if (!first && questionNum == 1) {
            	back.setVisible(false);                		
            }
        	input.setText(stringUserInput[questionNum]);
        } 
    }

    public static int compare() { // compare amount of point each movie got
        int maxIndex = 0;
        for (int i = 0; i < userAnswer.length; i++) { // loop through user answers
            for (int j = 0; j < movieData.length; j++) { // loop through each movie's data
                if (userAnswer[i] == movieData[j][i]) { // compare user answer with movie data and add points to the movie accordingly
                    points[j] += 1;
                    if (points[j] > points[maxIndex]) {
                        maxIndex = j;
                    }
                }
            }
        }
        for (int i = 0; i < movieData.length; i++ ) {
            System.out.println(movies[i] + ": " + points[i]);
        }
        return maxIndex; // return movie points
    }

    public static boolean YNValidation(String input) { // check if user entered valid input
        extra.setText("");
        pw.println(readQuestion(questionNum) + ": " + inputText); // add user input to file
        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("ya") || input.equalsIgnoreCase("yeah") || input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("nope")) {
            return true; 
        } else { // ask user to enter valid input
            extra.setText("Please enter Y or N.");   
            return false;
        }
    }

    public static boolean yesOrNo(String input) { // check if user entered yes or no
        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("ya") || input.equalsIgnoreCase("yeah") ) {
            return true; 
        }
        return false;
    }

    public static void reset () { // reset variables and gui
        infoText.setVisible(false);
        imageIcon = new ImageIcon("./img/scifi.jpg");
        userAnswer = new boolean[questionAmount - 2];
        points = new int[movies.length];
    	stringUserInput = new String[questionAmount];
    }

    public static void hideEnd() {
        done = true; // tell submit button that program has ended
        pw.close(); // close print writer
        // hide input GUIs
        inputLine.setVisible(false);
        submit.setVisible(false);
        back.setVisible(false);
    }

    public static void imageResize (int width, int height) { // resize image
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        imageLabel.setIcon(imageIcon);
    }
    
    public static String readQuestion(int index) { // read question from text file
    	try {
			return Files.readAllLines(Paths.get("questions.txt")).get(index);
		} catch (IOException e) {
			// Output Error
			e.printStackTrace();
			return "Error, question not found";
		}
    }
}