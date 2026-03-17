package connect4;

import javax.swing.*;
import java.awt.*;

public class Game {

    //Create the frame
    JFrame frame = new JFrame("Connect 4");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    //Create the JPanel
    JPanel panel = new JPanel(new GridLayout(7,7));

    //For loop to add image to whole board
    for (int row = 0; row < 7; row++){
        for (int col = 0; col < 7; )

    }
//While loop to add buttons to the top of the board
//    int row = 1;
//    int col = 1;
//    while (col < 7){
//        JButton button = new JButton(new ImageIcon("black.png"));
//        panel.add(button);
//        col++;
//    }


    //Adds a label to the top of JPanel
    JLabel title = new JLabel("Player 1's turn");

}
