// Author: Emma Zhang
// Teacher: Mr. A
// Date: 12/11/2023
// ICS3U - Recommender Project: picking the best sci-fi movie

// notes
// https://docs.google.com/document/d/1BFJZOAu8JW21Z4OWrdyF4VTxQpLtETs1GQXQn9VeSRg/edit
// provide link with movie and description

package javaFinal;
import java.util.Scanner;

import java.io.*;

public class sciFiMovieConsole {
    // declare global variables
    static Scanner sc = new Scanner(System.in);
    // static Scanner fileSc = new Scanner("info.txt");
    static String result[] = {"Wall-e(2008)", "gravity(2013)", "The Wandering Earth(2019)", "The Martian(2015)", "The Star Wars Prequel(1999-2005)", "Interstellar(2014)", "Your Name(2016)", "Edge of Tomorrow(2014)", "Back to The Future(1985)", "The Matrix(1985 - 2021)", "Blade Runner", "Blade Runner 2049(2017)", "Ex Machina(1982)", "Inception(2010)", "Everything Everywhere All at Once(2022)", "Ender's Game(2013)", "Children of Men(2006)"};
    static String genre[] = {"space", "time travel", "robots", "other"};
    static int suggestion;
    static boolean ask;
    static String divider = "»»————————————————————————————————————————————————————————\n"; 
    static int genreIndex;
    static String answers[][] = new String[10][2];
    static int answerIndex = 0;
    static String welcomeQuestion = "Welcome to Sci-Fi Suggester, would you like a Sci-Fi movie suggestion?";
    static String rangeQuestion = "Are you interested in space[1], time travel[2], robots[3], other[4]?";
    static String againQuestion = "Would you like another sci-fi suggestion?";
    static boolean first = true;
    static boolean last = false;

    // main class
    public static void main (String[] args) throws Exception{

        PrintWriter pw = new PrintWriter("results.txt");
        
        // ask user if they would like a movie suggestion
        System.out.println(divider);
		if ((YNAsk(welcomeQuestion, false))) {
			ask = true;
		} else {
            pw.println(welcomeQuestion + ": " + "no");
        }

        // enter suggestor loop if user would like a movie suggestion
		while (ask) {	
            // invoke reset function
            reset();

            // ask the genre of sci-fi
			int selection = rangeAsk(rangeQuestion, 1, 4);

			// Space reccomendation
            genreIndex = selection - 1;
			if (selection == 1) {
				if (YNAsk("Are you interested in shorter movies?", true)) {
					if ((YNAsk("Are you interested in cartoon movies?", true))) {
						System.out.println("I suggest " + result[0] + ".");
						suggestion = 0;
					} else {
						if (YNAsk("Are you interested in thriller movies?", true)) {
							System.out.println("I suggest " + result[1] + ".");
							suggestion = 1;
						} else {
							System.out.println("I suggest " + result[2] + ".");
							suggestion = 2;
						}
					}
				} else {
					if (YNAsk("Are you interested in survival movies?", true)) {
						System.out.println("I suggest " + result[3] + ".");
						suggestion = 3;
					} else {
						if (YNAsk("Are you interested in movies with sequels?", true)) {
							System.out.println("I suggest " + result[4] + ".");
							suggestion = 4;
						} else {
							System.out.println("I suggest " + result[5] + ".");
							suggestion = 5;
						}
					}
				}

				// Time Travel reccomendation
				} else if (selection == 2) {
					if (YNAsk("Are you interested in anime movies?", true)) {
						System.out.println("I suggest " + result[6] + ".");
						suggestion = 6;
					} else {
						if (YNAsk("Are you interested in time loop movies?.", true)) {
							System.out.println("I suggest " + result[7] + ".");
							suggestion = 7;
						} else {
							System.out.println("I suggest " + result[8] + ".");
							suggestion = 8;
						}
					}
					
				// Robots reccomendation
				} else if (selection == 3) {
					if (YNAsk("Are you interested in action movies?", true)) {
						if (YNAsk("Are you intersted in movies with sequels?", true)) {
							System.out.println("I suggest " + result[9] + ".");
							suggestion = 9;
						} else {
							if (YNAsk("Are you interested in classic movies?", true)) {
								System.out.println("I suggest " + result[10] + ".");
								suggestion = 10;
							} else {
								System.out.println("I suggest " + result[11] + ".");
								suggestion = 11;
							}
						}
					} else {
						System.out.println("I suggest " + result[12] + ".");
						suggestion = 12;
					}
					
				// Other reccomendation
				} else {
					if (YNAsk("Are you interested in Christopher Nolan movies?", true)) {
						System.out.println("I suggest " + result[13] + ".");
						suggestion = 13;
					} else if (YNAsk("Are you interested in more recent movies?", true))  {
						System.out.println("I suggest " + result[14] + ".");
						suggestion = 14;
					} else if (YNAsk("Are you interested in alien movies?", true))  {
						System.out.println("I suggest " + result[15] + ".");
						suggestion = 15;
					} else {
						System.out.println("I suggest " + result[16] + ".");
						suggestion = 16;
					}
				}
                System.out.println("");
                
                // output user history and write to file
                if(first) {
                    first = false;
                    pw.println(welcomeQuestion + ": " + "yes");
                } else {
                    pw.println(againQuestion + ": " + "yes");
                }
                
                System.out.println("History:\nYou selected [" + genre[genreIndex] + "] as your preferred genre.");
                pw.println(rangeQuestion + ": " + genreIndex);
                for (int i = 0; i < answerIndex; i ++) {
                    System.out.println("You answered [" + answers[i][1] + "] to: " + answers[i][0]);
                    pw.println(answers[i][0] + ": " + answers[i][1]);
                }
                System.out.println("");

                // ask if user would like to have another sci-fi suggestion
                ask = YNAsk(againQuestion, true);
        }
        if(!first) {
            pw.println(againQuestion + ": " + "no");
        }
        System.out.println("Thank you for checking out this program!");
        sc.close();
        pw.close();        
    }
	
    public static boolean YNValidation(String input) {
        boolean stay = true;
        while (stay) { // repeat until input is valid
            if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("ya") || input.equalsIgnoreCase("yeah") ) {
                return true; 
            } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("nope")) {
                return false;
            }

            // ask user to enter valid input
            System.out.println("Please enter Y or N.");            
            input = sc.nextLine();
        }
        return false;
    }
    
    public static int rangeValidation(int start, int end, int input) {
        sc.nextLine();
        while (input > end || input < start) { // repeat until input is within range
            System.out.println("Please enter a number between " + start + " and " + end + ".");
            // check if integer is entered
            while(!sc.hasNextInt()) {
                sc.nextLine();
                System.out.println("Please enter an integer.");
            }
            input = sc.nextInt();
            sc.nextLine();
        }
            return input;
    }
    
    public static boolean YNAsk (String question, boolean isStoredInAnswer) {
        // ask question
        System.out.println(question);
        // check input is valid
        boolean output = YNValidation(sc.nextLine());
        System.out.println("\n" + divider);
        if (isStoredInAnswer) { // check if question needs to be stored so we can output the answer later
            // store question and answer
            answers[answerIndex][0] = question; 
            if(output) {
                answers[answerIndex][1] = "yes"; 
            } else {
                answers[answerIndex][1] = "no"; 
            }
            answerIndex += 1;
        }
        return output;
        
    }
    
    public static int rangeAsk (String question, int start, int end) {
        // ask question
        System.out.println(question);
        // check if input is integer
        while(!sc.hasNextInt()) {
            sc.nextLine();
            System.out.println("Please enter an integer.");
        }
        // check if input is within range
        int output = rangeValidation(start, end, sc.nextInt());
        System.out.println("\n" + divider);
        return output;
    }

    public static void reset () {
        // reset variables
        ask = false;
        answers = new String[10][2];
        answerIndex = 0;

    }
}
