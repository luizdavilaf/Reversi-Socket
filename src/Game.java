import java.util.Scanner;

import javax.swing.text.PlainDocument;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;



public class Game
{
   
    private Field fieldObj;
    private String[][] field;
    private int black; //number of black discs on the board
    private int white; //number of white discs on the board
    private boolean curPlayer; //true - current player is white, false - current player is black
    private Scanner regScan; //scanner that is used throughout the program
    private String winner;  //as the game ends, stores the winner's colour
    private boolean surrender; //if someone decides to surrender, becomes true and stops the loop in gameFlow()
    private ArrayList<Player> players = new ArrayList<>(); 
   
   /*  public static void main(String args[]) {
        Game game = new Game(ArrayList players);
        game.startGame();
    } */


    public Game(ArrayList<Player> players)
    {
        fieldObj = new Field();
        regScan = new Scanner(System.in);
        black = 0;    //sets counter values
        white = 0;    //    to 0
        curPlayer = true; //sets current player to white
        surrender = false;
        winner = "UNDEFINED"; //sets winned to undefined as the game has just begun
        this.players =players;
    }
    
    /**
     * Starts the game loading the game field, settins starting discs and sets the first player to white
     * @throws IOException
     * 
     */
    public void startGame() throws IOException
    {
        field = fieldObj.setField();
        curPlayer = fieldObj.getFirstTurn();
        gameFlow();
    }
    
    
    
    /**
     * Sets and updates the counters for the number of white/black discs on the board
     * 
     */
    public void setCounters()
    {
        int b = 0;
        int w = 0;
        //scans the field updating the counters
        for(int i = 0; i<field.length; i++)
        {
            for(int j = 0; j<field[0].length; j++)
            {
                if(field[i][j].equals("B"))
                {
                    b++;
                }
                else if(field[i][j].equals("W"))
                {
                    w++;
                }
            }
        }
        black = b;
        white = w;
    }
    
    /**
     * Exits the game
     * 
     */
    public void closeGame()
    {
        System.exit(1);
    }
    
   
    
    /**
     * Represents the flow of the game, getting user turn inputs, processing them etc.
     * @throws IOException
     * 
     */
    public void gameFlow() throws IOException
    {
        //variables that determine if the first move was already made
        int firstMove = 0;              
        boolean wasFirstMove = false;   
        
        boolean skippedTurn = false; //determines of the previous turn was skipped due to lack of moves
        boolean validMove = false;   //determines if user's input was 'grammatically' correct and can be further processed by the program
        boolean moveLegal = false;   //determines if the user's input was a legal move, allowed by the game rules
        String userInput = "";
        int infinite = 0; //while it's equal to 0, loop runs. it always is equal to 0
        boolean gameFinished = false; //determines if the game was finished or not
        
        HashMap<String,Boolean> movesLeft = new HashMap<String, Boolean>();
        ArrayList<String> movesList = new ArrayList<String>(); //stores the available moves for the current player
        boolean moves = false;      //determines if there are available moves for the current player
        
        //temporary variables used to check if the previous&current move were both skipped
        boolean one;
        boolean two; 
        
        //the same as the two variables above, but not temporary
        movesLeft.put("White", true);
        movesLeft.put("Black", true);
        
        do
        {
            String colour = "";
            String opposColour = "";
            
            //if first move was made, update variable
            if(!wasFirstMove)
            {
                wasFirstMove = true;
            }
            
            //if both the first move was made and it was legal,  OR  if previous turn was skipped, change the current player and reset skippedTUrn variable
            if((wasFirstMove && moveLegal) || skippedTurn)
            {
                curPlayer = !curPlayer;
                skippedTurn = false;
                moveLegal = false;
            }
            
            //store current and opposite players' colours
            if(curPlayer)
            {
                colour = "White";
                opposColour = "Black";
            }
            else if(!curPlayer)
            {
                colour = "Black";
                opposColour = "White";;
            }
                
            //print the field, update counters, print counters and store available moves
            System.out.println("\u000c");
            fieldObj.printField();
            displayCounters(); 
            movesList = getAvailableMoves();
            
            //if there are available moves - print them, it there are no available moves, print an appropriate message and skip the turn
            if(movesList.size() == 0)
            {
                moves = false;
                skippedTurn = true;
                System.out.println(colour + " ,you don't have any moves left. Skipping turn. (press ENTER)");
                regScan.nextLine();
            }
            else
            {
                moves = true;
                int moveCount = 0; //stores the number of printed moves not to print more than 5 moves in a line, to make it more clear
                System.out.println("Available moves: (" + movesList.size() + ")"); //also prints the number of available moves
                for(int i = 0; i < movesList.size(); i++)
                {
                    System.out.print(movesList.get(i)+"\t"); //prints the move
                    moveCount++;
                    
                    //if line contains 5 moves, print a blank line and reset the number of moves printed
                    if(moveCount == 5)
                    {
                        System.out.println();
                        moveCount = 0;
                    }
                }
                System.out.println("\n");
            }
            
            //store if there were moves left for the current player
            movesLeft.put(colour, moves);
            
            //if there were moves left for the current player, ask him to make a move
            if(movesLeft.get(colour))
            {
                System.out.println(colour + " player's turn:");
                userInput = regScan.nextLine();
                
                validMove = checkTurnInput(userInput); // move is 'gramatically' correct
                if (validMove) {
                    moveLegal = checkMoveLegal(userInput); // move is legal according to the game rules
                }
              
                movesLeft.put(opposColour, true); //sets the previous player turn to the default value (not skipped)
            }
            
            //if both previous player's turn and current player's turn was skipped, end the game
            one = movesLeft.get(colour);
            two = movesLeft.get(opposColour);
            if(one == false && two == false)
            {
                gameFinished = true;
            }
        }
        while(infinite == 0 && surrender == false && gameFinished == false);
        
        //if the game was finished, define the winner and print it
        if(gameFinished)
        {
            endGame();
        }
        
        //reset the values of the variables
        gameFinished = false;
        surrender = false;
        return;
    }
    
    /**
     * Updates and prints the counters for the number of white/black discs on the board
     * @throws IOException
     */
    public void displayCounters() throws IOException
    {
        setCounters();
        String print;
        print = ("\nWhite: " + white + "\nBlack: " + black + "\n\n");
        //System.out.println("\nWhite: " + white);
        //System.out.println("Black: " + black + "\n\n");
        System.out.println(print);
        byte[] output1 = print.getBytes();
        
        for(Player p : players){
             
             OutputStream o = p.getSocket().getOutputStream();
             o.write(output1);
             
        }
    }
    
    /**
     * Checks if user turn input was grammatically correct
     * 
     * @param userInput represents user turn input
     * @return validMove returns the validity of move (true - valid/false - invalid)
     */
    public boolean checkTurnInput(String userInput)
    {
        int num;
        boolean validMove = true;
        
        //checks the length of the input
        int inputLength = userInput.length();
        //if not equal to 2, it is incorrect, else - check if the first is a letter that can be further processed into a horizontal coordinate (a,b,c,d,e,f,g,h)
        //and if the second part of the input is a number
        if(inputLength!=2)
        {
            validMove = false;
            System.out.println(userInput.length());
        }
        else
        {
            //splits user input
            String letter = userInput.substring(0,1);
            String number = userInput.substring(1,2);
            
            //if the letter part of user input is not an appropriate letter
            if(!letter.equals("a") && !letter.equals("b") && !letter.equals("c") && !letter.equals("d") && !letter.equals("e") && !letter.equals("f") && !letter.equals("g") 
            && !letter.equals("h") && !letter.equals("A") && !letter.equals("B") && !letter.equals("C") && !letter.equals("D") && !letter.equals("E") 
            && !letter.equals("F") && !letter.equals("G") && !letter.equals("H"))
            {
                System.out.println("INVALIDLETTER");
                validMove = false;
            }
            
            //tries to get the numeric value from the second part of the string, if impossible - turn input is invalid
            try
            {
                num = Integer.parseInt(number);
                //if the number is not within the field bounds, turn is invalid
                if(num > 8 || num < 1)
                {
                    validMove = false;
                    System.out.println("Wrong number");
                }
            }
            catch(NumberFormatException ex)
            {
                System.out.println("NUMBERFORMAT");
                validMove = false;
            }
            
            
        }
        return validMove;
    }
    
    /**
     * Checks if the user's turn was legal according to the game rules
     * 
     * @param place represents the user turn input
     * @return moveLegal tells if the move was legal or not
     */
    public boolean checkMoveLegal(String place)
    {
        String letter;
        String numStr;
        int num;
        int letterNum;
        
        boolean[] directions = new boolean[8];//a boolean value that tells if the line was formed in a certain direction
        boolean moveLegal = false;
        
        //splits the turn input in two strings
        letter = place.substring(0,1);
        numStr = place.substring(1,2);
        
        //gets the coordinate value from the secon part of the string
        num = Integer.parseInt(numStr);
        num-=1;
        
        //assigns a numeric value to a width coordinate according to it's letter
        switch(letter)
        {
            case "A":
            case "a":   letterNum = 0;
                        break;
            case "B":
            case "b":   letterNum = 1;
                        break;
            case "C":
            case "c":   letterNum = 2;
                        break;
            case "D":
            case "d":   letterNum = 3;
                        break;
            case "E":
            case "e":   letterNum = 4;
                        break;
            case "F":
            case "f":   letterNum = 5;
                        break;
            case "G":
            case "g":   letterNum = 6;
                        break;
            case "H":
            case "h":   letterNum = 7;
                        break;
            default:    letterNum = 100000;
                        break;
        }
        
        //sets the current player's colour
        String playerColour;
        if(curPlayer)
        {
            playerColour = "W";
        }
        else
        {
            playerColour = "B";
        }
        
        //if player is trying to put a disc onto an empty tile, check if the move is legal, else move is not legal
        if(field[num][letterNum].equals("0"))
        {
            //store if player's move causes to form an appropriate line of discs (in every direction) to be flipped and flips them if it does
            directions[0] = checkValidLineFormed(num, letterNum, "up", playerColour, "domove");
            directions[1] = checkValidLineFormed(num, letterNum, "dUpRight", playerColour, "domove");
            directions[2] = checkValidLineFormed(num, letterNum, "right", playerColour, "domove");
            directions[3] = checkValidLineFormed(num, letterNum, "dDownRight", playerColour, "domove");
            directions[4] = checkValidLineFormed(num, letterNum, "down", playerColour, "domove");
            directions[5] = checkValidLineFormed(num, letterNum, "dDownLeft", playerColour, "domove");
            directions[6] = checkValidLineFormed(num, letterNum, "left", playerColour, "domove");
            directions[7] = checkValidLineFormed(num, letterNum, "dUpLeft", playerColour, "domove");
            
            //loops through the array of directions and makes a move legal if discs are being flipped in at least one direction
            for(int i = 0; i < directions.length;i++)
            {
                if(directions[i] == true)
                {
                    moveLegal = true;
                }
            }
        }
        else
        {
            moveLegal = false;
        }
        return moveLegal;
    }
    
    /**
     * Finds and returns a list of available moves for the current player
     * 
     * @return moves the list of legals move available for the current player
     */
    public ArrayList<String> getAvailableMoves()
    {
        ArrayList<String> moves = new ArrayList<String>(); //stores the available moves
        String playerColour; 
        String opposColour; //stores an opposite player's colour
        boolean movesExist = true;
        int num; //stores a vertical coordinate of the line displayed to user
        boolean[] directions = new boolean[8]; //used to check if the appropriate lines form in different directions
        String letterH; //the horizontal coordinate transformed into letter
        
        //sets the current and opposite player symbols to look for
        if(curPlayer)
        {
            playerColour = "W";
            opposColour = "B";
        }
        else
        {
            playerColour = "B";
            opposColour = "W";
        }
        
        //loops through each tile on the field (field array), checking if the lines form
        for(int tempV = 0; tempV<field.length; tempV++)
        {
            for(int tempH = 0; tempH<field[0].length; tempH++)
            {
                //if the line is unoccupied by discs, placing a disc on it would be a valid move, else does nothing
                if(field[tempV][tempH].equals("0"))
                {
                    //checks if placing a disc on that tile would make a line form for each direction
                    directions[0] = checkValidLineFormed(tempV, tempH, "up", playerColour, "check");
                    directions[1] = checkValidLineFormed(tempV, tempH, "dUpRight", playerColour, "check");
                    directions[2] = checkValidLineFormed(tempV, tempH, "right", playerColour, "check");
                    directions[3] = checkValidLineFormed(tempV, tempH, "dDownRight", playerColour, "check");
                    directions[4] = checkValidLineFormed(tempV, tempH, "down", playerColour, "check");
                    directions[5] = checkValidLineFormed(tempV, tempH, "dDownLeft", playerColour, "check");
                    directions[6] = checkValidLineFormed(tempV, tempH, "left", playerColour, "check");
                    directions[7] = checkValidLineFormed(tempV, tempH, "dUpLeft", playerColour, "check");
                    
                    //loops through the directions and checks if line is formed in any of these directions thus making a move legal
                    for(int i = 0; i < directions.length; i++)
                    {
                        //if the move is legal, transform it in a format, understandable to user
                        if(directions[i] == true)
                        {
                            //assign a letter value to a horizontal coordinate
                            switch(tempH)
                            {
                                case 0:     letterH = "a";
                                            break;
                                case 1:     letterH = "b";
                                            break;
                                case 2:     letterH = "c";
                                            break;
                                case 3:     letterH = "d";
                                            break;
                                case 4:     letterH = "e";
                                            break;
                                case 5:     letterH = "f";
                                            break;
                                case 6:     letterH = "g";
                                            break;
                                case 7:     letterH = "h";
                                            break;
                                default:    letterH = "UNDEFINED";
                                            break;
                            }
                            num = tempV+1;  //increase a vertical coordinate by one (the first line on the field presented to user is 1st line, but in the field array it's 0th)
                            moves.add(letterH+num); //stores a move
                            break;
                        }
                    }
                }
            }
        }
        
        return moves;
    }
    
    /**
     * Checks if user's turn will form a valid line of discs that can be flipped
     * 
     * @param vertPos represents the vertical position of the tile to be checked
     * @param horPos represents the horizontal position of the tile to be checked
     * @param direction represents the direction in which to check if the line will be formed
     * @param playerColor represents the symbol of the current player ("W" for white, "B" for black)
     * @param action tells the method whether to check if both the move is legal and flip the discs, or just check if the move is legal
     * 
     * @return lineFormed represents the legality of the move
     */
    public boolean checkValidLineFormed(int vertPos, int horPos, String direction, String playerColour, String action)    
    {
        boolean lineFormed = false; //value that is being returned
        int infinity = 0; //is always equal to 0
        
        //makes the method work properly and each time it's called to start from the beginning (sometimes, it beginned from the middle without the loop, i don't know why).
        while(infinity == 0)
        {
            int deltaH = 0; //distance by which horizontal coordinate will be changed each time
            int deltaV = 0; //distance by which vertical coordinate will be changed each time
            int tempH = horPos; //temporary tile horizontal position
            int tempV = vertPos;//temporary tile vertical position
            
            //sets the delta variables according to the direction of the line checked
            if(direction.equals("up"))
            {
                deltaV = -1;
            }
            else if(direction.equals("right"))
            {
                deltaH = 1;
            }
            else if(direction.equals("down"))
            {
                deltaV = 1;
            }
            else if(direction.equals("left"))
            {
               deltaH = -1; 
            }
            else if(direction.equals("dDownLeft"))
            {
                deltaH = -1;
                deltaV = -1;
            }
            else if(direction.equals("dUpRight"))
            {
                deltaH = 1;
                deltaV = -1;
            }
            else if(direction.equals("dDownRight"))
            {
                deltaH = 1;
                deltaV = 1;
            }
            else if(direction.equals("dUpLeft"))
            {
                deltaH = -1;
                deltaV = 1;
            }
            
            tempH +=deltaH;
            tempV +=deltaV;
            //if the next tile in the line is within the field bounds and is opposite player's colour, start checking the line, else no valid line can be formed in that direction
            if(tempH >= 0 && tempV >= 0 && tempH<field[0].length && tempV<field.length && !field[tempV][tempH].equals(playerColour) && !field[tempV][tempH].equals("0"))
            {                
                //while the tiles are within the bonds, check the tiles
                while(tempV >= 0 && tempV < field.length && tempH >= 0 && tempH < field[0].length)
                {
                    //if the next tile of the line is empty, no valid lime can be formed in that direction, else if next tile contains an opponent's disc, a valid line was formed
                    //and then break out of loop, if neither of these two happened, check the next tile
                    if(field[tempV][tempH].equals("0"))
                    {
                        lineFormed = false;
                        break;
                    }
                    else if(field[tempV][tempH].equals(playerColour))
                    {
                        lineFormed = true;
                        break;
                    }
                    else
                    {
                        tempV+=deltaV;
                        tempH+=deltaH;
                    }
                    
                }
                    
            }
            
            //if line was formed and the command to the method was equal to 'domove', flip discs
            if(lineFormed == true && action.equals("domove"))
            {
                //go backwards, subtracting delta variables from temporary variables until the coordinates are equal to the line beginning coordinates
                while(tempV != vertPos || tempH != horPos)
                {
                    field[tempV][tempH] = playerColour; //flips the disc
                    
                    tempH -=deltaH;
                    tempV -=deltaV;
                }
                field[vertPos][horPos] = playerColour; // put a new disc on the appropriate tile of the board
            }
            return lineFormed;
        }
        return lineFormed;
    }
    
   
    
   
   
    /**
     * Surrenders the game, displaying the winner and setting the surrender variable's value so that it will break out of gameFlow infinite loop and return to main menu
     * @throws IOException
     * 
     */
    public void surrender() throws IOException
    {
        surrender = true;
        String colour = "White";
        if(curPlayer)
        {
            colour = "Black";
        }
        else if(!curPlayer)
        {
            colour = "White";
        }
        System.out.println("\u000c");
        fieldObj.printField();
        displayCounters();
        System.out.println(colour + " wins.\n(PRESS ENTER)");
        regScan.nextLine(); //waits until user presses enter to let him appreciate his victory and view the field
        return;
    }
    
    /**
     * Ends the game stating the winner and getting beck to the main menu
     * 
     */
    public void endGame()
    {
        if(black>white)
        {
            winner = "black";
        }
        else if(white>black)
        {
            winner = "white";
        }
        else if(white==black)
        {
            winner = "tie";
        }
        
        if(winner.equals("tie"))
        {
            System.out.println("It's a tie!");
        }
        else
        {
            System.out.println("Winner: " + winner + "!");
        }
        regScan.nextLine(); //wait until user presses enter key to let him appreciate his victory
    }
}