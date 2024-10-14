import javax.swing.*;
import java.awt.*;
// the greyed out ones are failed attempts at the multi-color avatar
import java.util.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;



class Player {
  // reference variables for when I do board stuff
  // player name that is an imput
  String playerName;
  // references space number player is on
  int fileLineCounter = 1;
  // the list that cycles through the animation frames
  ImageIcon [] spriteFrames;
  Image playerAvatarDisplay;

  int frameCounter = 0;

  //player coordinates. they start at space 1
  int xCoordinate = 68;
  int yCoordinate = 330;
   

public Player(String avatarName, int playerFrames){
  spriteFrames = new ImageIcon[playerFrames];
  if (avatarName.equalsIgnoreCase("mushroom")){
    // code does work but image is being funky
    spriteFrames[0] = new ImageIcon("Shoom_Idle1.png");
    spriteFrames[1] = new ImageIcon("Shoom_Idle2.png");
    spriteFrames[2] = new ImageIcon("Shoom_Idle3.png");
    spriteFrames[3] = new ImageIcon("Shoom_Idle4.png");
    spriteFrames[4] = new ImageIcon("Shoom_Idle5.png");
    spriteFrames[5] = new ImageIcon("Shoom_Idle6.png");
    spriteFrames[6] = new ImageIcon("Shoom_Idle7.png");
    spriteFrames[7] = new ImageIcon("Shoom_Idle8.png");
  }

  // failed attempt at multi-color avatar. inspired by: https://stackoverflow.com/questions/21382966/colorize-a-picture-in-java. It did not work and I did not have enough time to fix it so i commented it all out
  /*else if (avatarName.equalsIgnoreCase("colorshroom")){
    try {
    spriteFrames[0] = new ImageIcon((dye(ImageIO.read(new File("Shroom_Idle1.png")), new Color(255,0,0,128))));
      System.out.println("something happened");
    } catch (IOException e) {
      System.out.println("well shit");
    }
    try {
    spriteFrames[1] = new ImageIcon((dye(ImageIO.read(new File("Shroom_Idle2.png")), new Color(255,0,0,128))));
    } catch (IOException e){
      System.out.println("well shit");
    }
    try {
    spriteFrames[2] = new ImageIcon((dye(ImageIO.read(new File("Shroom_Idle3.png")), new Color(255,0,0,128))));
    } catch (IOException e){
      System.out.println("well shit");
    }
    try {
    spriteFrames[3] = new ImageIcon((dye(ImageIO.read(new File("Shroom_Idle4.png")), new Color(255,0,0,128))));
    } catch (IOException e){
      System.out.println("well shit");
    }
    try {
    spriteFrames[4] = new ImageIcon((dye(ImageIO.read(new File("Shroom_Idle5.png")), new Color(255,0,0,128))));
    } catch (IOException e){
      System.out.println("well shit");
    }
    try {
    spriteFrames[5] = new ImageIcon((dye(ImageIO.read(new File("Shroom_Idle6.png")), new Color(255,0,0,128))));
    } catch (IOException e){
      System.out.println("well shit");
    }
    try {
    spriteFrames[6] = new ImageIcon((dye(ImageIO.read(new File("Shroom_Idle7.png")), new Color(255,0,0,128))));
    } catch (IOException e){
      System.out.println("well shit");
    }
    try {
    spriteFrames[7] = new ImageIcon((dye(ImageIO.read(new File("Shroom_Idle8.png")), new Color(255,0,0,128))));
    } catch (IOException e){
      System.out.println("well shit");
    }
  } */
  playerAvatarDisplay = spriteFrames[0].getImage();
  
  
}
  public int rollDie(int maxNum){
    return ((int) (Math.random()*maxNum + 1));
  }
// frame update method that cycles through all the frames
  public void update(){
    playerAvatarDisplay = spriteFrames[frameCounter].getImage();
    if (frameCounter == spriteFrames.length -1){
      frameCounter = 0;
    }
    else{
      frameCounter++;
    }
  }
    // more failed attempts at multicolored avatars
          /*BufferedImage image = ImageIO.read(new File("DRVpH.png"));
          panel.add(new JLabel(new ImageIcon(image)));
          panel.add(new JLabel(new ImageIcon(dye(image, new Color(255,0,0,128)))));
          panel.add(new JLabel(new ImageIcon(dye(image, new Color(255,0,0,32)))));
          panel.add(new JLabel(new ImageIcon(dye(image, new Color(0,128,0,32)))));
          panel.add(new JLabel(new ImageIcon(dye(image, new Color(0,0,255,32)))));
          f.getContentPane().add(panel);
          f.pack();
          f.setVisible(true);*/


     /* private static BufferedImage dye(BufferedImage image, Color color)
      {
          int w = image.getWidth();
          int h = image.getHeight();
          BufferedImage dyed = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
          Graphics2D g = dyed.createGraphics();
          g.drawImage(image, 0,0, null);
          g.setComposite(AlphaComposite.SrcAtop);
          g.setColor(color);
          g.fillRect(0,0,w,h);
          g.dispose();
          return dyed;
      }*/

  }