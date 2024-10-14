import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.util.Collections;


class Game extends JFrame{
  JFrame gameDisplay = new JFrame();
  Scanner consoleInput = new Scanner(System.in);
  PaintedPanel gameBoard = new PaintedPanel();
  // timer used for character animation
  Timer charFrameTimer = new Timer (150, new TimerListener());
  // a variable I use for the while loop when players are asked to input their usernames and which avatar they want
  int scannerChecker;
  // array lists so I can add an unlimited amount of the variables I need 
  ArrayList <String> avatarList = new ArrayList<String>();
  ArrayList <Player> playerList = new ArrayList<Player>();
  ArrayList <Integer> positionList = new ArrayList <Integer>();
  // current player variable for the methods
  Player currentPlayer;
  // starting position
  int startxCoordinate = 68;
  int startyCoordinate = 330;
  // final position
  int finalxCoordinate = 68;
  int finalyCoordinate = 25;
  // for the time checker so you can't spam click. set to zero so it works for the first click
  long timeElapsed = 0;
  // tracks which player in the playerList's turn it is
  int playerTracker = 0;
  

public Game() {
  // reading the position file and adding it to the position array. For a given coordinate, the space's number times 2 - 1 is the x coordinate while the space's number times 2 is the y coordinate
  File positions = new File("GamePositions.txt");
  try (Scanner input = new Scanner(positions)){
      while(input.hasNextLine()){
        try {
           int positionNumber = Integer.parseInt(input.nextLine());
          positionList.add(positionNumber);
        }
        catch (NumberFormatException e) {
           System.out.println("invalid position number");
        }
      }
    } catch (FileNotFoundException fnfe){
      System.out.println("File not found");
    }
  
  
  System.out.println("How many players are playing? (no maximum but pls don't do more than a reasonable number or else the program will prob be extremely slow)");
  avatarList.add("mushroom");
  int numPlayers = consoleInput.nextInt();
  for (int i = 0; i < numPlayers; i++){
    scannerChecker = 0;
    System.out.println("Which Character Avatar would player " + (i + 1) + " like?");
    System.out.println("Options: " + avatarList);
    while (scannerChecker != 1){
      String charAvatar = consoleInput.next();
      if (!avatarList.contains(charAvatar.toLowerCase())){
        System.out.println("Not a valid Avatar. Please type a valid Avatar name");
        continue;
      }
      else if (charAvatar.equalsIgnoreCase(avatarList.get(0))){
        if (charAvatar.equalsIgnoreCase("mushroom")){
          playerList.add(new Player("mushroom", 8));
          System.out.println("What is your name?");
          playerList.get(i).playerName = consoleInput.next();
        }
        // the failed attempt to color the avatar
        /*else if (charAvatar.equalsIgnoreCase("colorshroom")){
          playerList.add(new Player("colorshroom", 8));
          System.out.println("What is your name?");
          playerList.get(i).playerName = consoleInput.next();
        }*/
          scannerChecker = 1;
          // ends the while loop
        }
      }
  }
  gameDisplay.add(gameBoard);
  gameDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  gameDisplay.setSize(550, 550);
  //https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
  gameDisplay.setExtendedState(JFrame.MAXIMIZED_BOTH); 
  gameDisplay.setUndecorated(true);

  gameDisplay.setVisible(true);
  gameDisplay.addMouseListener(new mListener());
  
 // character animation timer start 
  charFrameTimer.start();
  // shuffle array list for fun
  //https://stackoverflow.com/questions/16112515/how-to-shuffle-an-arraylist
  Collections.shuffle(playerList);
  System.out.println("Time to randomize the players");
  currentPlayer = playerList.get(playerTracker);
  System.out.println("Its's " + playerList.get(playerTracker).playerName + "'s turn!!'");
  System.out.println("Click the board to roll the dice");
  
    
}
   public void RandomEvent(){
     // different move spaces so there is a net gain of spaces since the larger is for moving forward while the smaller is for moving backwards
     int moveSpaces = (int) (Math.random()*5 + 1);
     int smallerMoveSpaces = (int) (Math.random()*3 + 1);
     int randomEncounterNumber;
     if ((int) (Math.random()*4 + 1) > 1){
       randomEncounterNumber = (int) (Math.random()*10 + 1);
       if (randomEncounterNumber == 1 || randomEncounterNumber == 2){
         System.out.println("Random encounter!!!");
         
         System.out.println("oops you have to move back " + moveSpaces + " spaces");
         // player movement stuff 
         currentPlayer.fileLineCounter = currentPlayer.fileLineCounter - moveSpaces;
         // accounts for if this random event makes the player position negative BUG: does not always work for some reason
         if (currentPlayer.fileLineCounter < 0){
           currentPlayer.fileLineCounter = 1;
           currentPlayer.xCoordinate = positionList.get(currentPlayer.fileLineCounter);
           currentPlayer.yCoordinate = positionList.get((currentPlayer.fileLineCounter + 1 ));
           System.out.println("to make sure you will not fall into the endless void, we have politely moved you to the start");
         } 
         
       }
       if (randomEncounterNumber == 3 || randomEncounterNumber == 4){
         System.out.println("Random encounter!!!");
         System.out.println("Wow the jungle loves you. you get launched forward " + smallerMoveSpaces + " spaces");
         currentPlayer.fileLineCounter = currentPlayer.fileLineCounter + smallerMoveSpaces;
       }
       if (randomEncounterNumber == 5){
         System.out.println("Random encounter!!!");
         // changes based on location on the board so its not unfairly gamechanging 
         if (currentPlayer.fileLineCounter < 20){
           System.out.println("woah you found a cool abandoned stone ladder from the old ancient civilization that once lived in this jungle. you take it up to space 20.");
           currentPlayer.fileLineCounter = 20;
         }
           else if (currentPlayer.fileLineCounter > 20 && currentPlayer.fileLineCounter < 50){
         System.out.println("Random encounter!!!");
         System.out.println("oops, you took a tumble down the jungle mouintain, breaking multiple squishy bones and falling with enough force to create a crater at space 20");
             currentPlayer.fileLineCounter = 20;
         }
         else if (currentPlayer.fileLineCounter > 50 && currentPlayer.fileLineCounter < 80){
            System.out.println("Random encounter!!!");
           System.out.println("a mystical gust of wind picks you up and sends you flying to space 80!!!");
           currentPlayer.fileLineCounter = 80;
         }
         else if (currentPlayer.fileLineCounter > 80 && currentPlayer.fileLineCounter < 100){
            System.out.println("Random encounter!!!");
            System.out.println("a magical boulder knocks you off the mountain. you fall to your certain death but magical leaves catch you before you black out. you find yourself at space 80");
           currentPlayer.fileLineCounter = 80;
          }
         }
       }
     // random encounter that's like 0.001% chance of happening (fun fact I actually got this once while testing)
     int randomRandomEncounterNumber = (int) (Math.random()*1000 + 1);
     if (randomRandomEncounterNumber == 176){
       System.out.println("OMG ANOTHER Random encounter!!!");
       System.out.println("looks like luck really really is not on your side today. you feel the world grow blurry around you, you can feel yourself floating, and suddenly you're gone. you open your eyes and are back at the beginning. perhaps the jungle deemed you not worthy enough to enter its domain");
       currentPlayer.fileLineCounter = 1;
     }
     // accounts for if the player gets launched past the final space due to a random event
       if (currentPlayer.fileLineCounter > 100){
         currentPlayer.fileLineCounter = 100;
         currentPlayer.xCoordinate = finalxCoordinate;
         currentPlayer.yCoordinate = finalyCoordinate;
        } else{
         currentPlayer.xCoordinate = positionList.get((currentPlayer.fileLineCounter * 2) - 2);
         currentPlayer.yCoordinate = positionList.get((currentPlayer.fileLineCounter * 2) - 1 );
        }
     gameBoard.repaint();

  }

class TimerListener implements ActionListener {
  public void actionPerformed(ActionEvent ae){
    // animates all the players in the playerlist
    if (!playerList.isEmpty()) {
      for (int i = 0; i < playerList.size(); i++){
        playerList.get(i).update();
        gameBoard.repaint();
      }
     }
  }
}
class PaintedPanel extends JPanel {

@Override
public void paintComponent(Graphics g){
  ImageIcon gameImage = new ImageIcon("GameBoard.png");
  int imgWidth = gameImage.getIconWidth();
  int imgHeight = gameImage.getIconHeight();
  Image gameboardimage = gameImage.getImage();
  g.drawImage(gameboardimage, 0, 0, 550, 550*imgHeight/imgWidth, 0, 0, imgWidth, imgHeight, this);
  // paints all the players in the list and also uses the player x and y coordiantes so it can update with the game progress
  for (int j = 0; j < playerList.size(); j++){
    g.drawImage(playerList.get(j).playerAvatarDisplay, playerList.get(j).xCoordinate, playerList.get(j).yCoordinate, 40, 40, this);
  }

}
}
  // mouse listener which tracks mouse clicks
  class mListener implements MouseListener {
      @Override
    public void mouseClicked(MouseEvent e){
      //https://stackoverflow.com/questions/2572868/how-to-time-java-program-execution-speed
      final long startTime = System.currentTimeMillis();
      if (timeElapsed > 1 || timeElapsed == 0){
        // player movement
        int diceRoll = currentPlayer.rollDie(6);
        currentPlayer.fileLineCounter = currentPlayer.fileLineCounter + (diceRoll);
        System.out.println("Wow!! " + currentPlayer.playerName + " rolled a " + diceRoll);
        // so player can't roll past 100. this if statement runs for 99% of the game
        if (currentPlayer.fileLineCounter < 100){
          currentPlayer.xCoordinate = positionList.get((currentPlayer.fileLineCounter * 2) - 2);
          currentPlayer.yCoordinate = positionList.get((currentPlayer.fileLineCounter * 2) - 1 );
           RandomEvent();
          System.out.println(currentPlayer.playerName + " is now at space " + currentPlayer.fileLineCounter);
          // changes player to next person in the list
          playerTracker ++;
          currentPlayer = playerList.get(playerTracker);
          // resets the player number when it reaches the final player in the list
          if (playerTracker == playerList.size() -1){
            playerTracker = -1;
          }
          System.out.println("It's " + currentPlayer.playerName + "'s turn!!'");
          System.out.println("Click the board to roll the dice");
          // win condition/ winner screen
        } else{
          currentPlayer.fileLineCounter = 100;
          currentPlayer.xCoordinate = finalxCoordinate;
          currentPlayer.yCoordinate = finalyCoordinate;
          gameBoard.repaint();
          System.out.println("Congratulations!!!!! " + currentPlayer.playerName + " Wins!!!!!! THEY ESCAPED THE JUNGLE!!!");
          charFrameTimer.stop();
        }
        // to prevent spam clicking
      } else{
        System.out.println("woah you're clicking too fast. just take a second to enjoy the scenery. OR IF YOU ARE TRYING TO CHEAT NUH UH U WAIT UR TURN");
      }
      final long endTime = System.currentTimeMillis();
      timeElapsed = endTime - startTime;
      }
    @Override
      public void mouseEntered(MouseEvent e){
      }
    @Override
      public void mouseExited(MouseEvent e){
      }
    @Override
      public void mousePressed(MouseEvent e){
      }
    @Override
      public void mouseReleased(MouseEvent e){
      }
  }
}