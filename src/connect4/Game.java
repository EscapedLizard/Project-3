/*
Title       : Game.java
Description : A graphical Connect 4 game using Java Swing where 2 players take turns dropping pieces into a board. The 1st player to connect 4 pieces horizontally, vertically, or diagonally wins
Author      : Sakina Hussain, Bethany Mixon
Date        : 03/16/2026
Version     : 1.0
Usage       : Compile and run the program to play Connect 4 with 2 players
Java Version: 24
 */
package connect4;

//  Import GUI and sound libraries, also file class for loading sound file
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.File;

// Create the Game class
// The assignment requires a JFrame window for the GUI
public class Game extends JFrame {
    /*
     * ENUM representing the possible pieces on the board
     */
    public enum Piece {
        RED,
        BLACK,
        EMPTY
    }

    // Board size
    private static final int ROWS = 6;
    private static final int COLS =7;

    // Game state array
    private Piece[][] board;

    // GUI board display
    private JLabel[][] labels;

    // Buttons for columns
    private JButton[] buttons;

    // Label showing game status
    private JLabel statusLabel;

    // Tracks which player is playing
    private Piece currentPlayer;

    // Count total moves (for tie)
    private int moveCount = 0;

    // Sound clip
    private Clip clickSound;



    /*
     * Constructor
     * Builds the entire game interface
     */
    public Game() {

        setTitle("Connect 4");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create status label
        statusLabel = new JLabel("Player 1 (Black) Turn");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        // Create board panel.
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(7, 7));

        // Initialize arrays
        board = new Piece[ROWS][COLS];
        labels = new JLabel[ROWS][COLS];
        buttons = new JButton[COLS];

        // Fill the board array with EMPTY
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = Piece.EMPTY;
            }
        }

        /*
         *Create column buttons
         */
        for (int c = 0; c < COLS; c++) {

            JButton button = new JButton();
            button.setMargin(new Insets(-5, -5, -5, -5));

            final int column = c;

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dropPiece(column);
                }
            });

            buttons[c] = button;

            boardPanel.add(button);
        }
        /*
         * Create board slots
         */
        for (int r = 0; r < ROWS; r++) {

            for (int c =0; c < COLS; c++) {

                JLabel label = new JLabel();

                label.setIcon(new ImageIcon("slot.png"));

                label.setHorizontalAlignment(JLabel.CENTER);

                labels[r][c] = label;

                boardPanel.add(label);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        // Player 1 starts
        currentPlayer = Piece.BLACK;

        // Load sound
        loadSound();

        ImageIcon icon = new ImageIcon("black.png");
        for (int i = 0; i < COLS; i++){
            buttons[i].setIcon(icon);
        }

        setVisible(true);
    }
    /*
     * Drop a piece into a column
     */
    private void dropPiece(int column) {

        int row = -1;

        // Find the lowest empty row
        for (int r = ROWS -1; r >= 0; r--) {

            if (board[r][column] == Piece.EMPTY) {
                row = r;
                break;
            }
        }

        // Column is full
        if (row == -1) {
            return;
        }
        // Place piece
        board[row][column] = currentPlayer;

        // Update GUI image
        if (currentPlayer == Piece.BLACK) {
            labels[row][column].setIcon(new ImageIcon("black.png"));
        } else {
            labels[row][column].setIcon(new ImageIcon("red.png"));
        }

        // Play sound
        playSound();

        moveCount++;

        // Check win
        if (checkWin(row, column)) {

            statusLabel.setText(currentPlayer + " wins!");

            for (int i = 0; i < COLS; i++) {
                buttons[i].setEnabled(false);
            }
            return;
        }

        // Check tie
        if (moveCount == 42) {

            statusLabel.setText("Game is a tie!");

            for (int i = 0; i < COLS; i++) {
                buttons[i].setEnabled(false);
            }
            return;
        }

        // Disable button if column becomes full
        if (board[0][column] != Piece.EMPTY) {
            buttons[column].setEnabled(false);
        }

        // Switch player
        if(currentPlayer == Piece.BLACK) {
            currentPlayer = Piece.RED;
        } else {
            currentPlayer = Piece.BLACK;
        }

        ImageIcon icon;

        //Update icons on the top row
        if (currentPlayer == Piece.BLACK){
            icon = new ImageIcon("black.png");
        } else {
            icon = new ImageIcon("red.png");
        }

        // Updates all the top buttons
        for(int i = 0; i < COLS; i++){
            if (buttons[i].isEnabled()) {
                buttons[i].setIcon(icon);
            }
        }

        statusLabel.setText(currentPlayer + " turn");
    }

    /*
     * Check if a move created four in a row
     */
    private boolean checkWin(int row, int col) {

        Piece p = board[row][col];

        // Horizontal check
        int count = 0;
        for (int c = 0; c < COLS; c++) {

            if (board[row][c] == p) {
                count++;
                if (count >= 4) return  true;
                } else {
                count = 0;
            }
        }

        // Vertical check
        count = 0;
        for (int r = 0; r < ROWS; r++) {

            if (board[r][col] == p) {
                count++;
                if (count >= 4) return true;
            } else {
                count = 0;
            }
        }

        // Diagonal (top-left to bottom-right)
        count = 0;
        int startRow = row;
        int startCol = col;

        while(startRow > 0 && startCol > 0) {
            startRow--;
            startCol--;
        }

        while(startRow < ROWS && startCol < COLS) {

            if(board[startRow][startCol] == p) {
                count++;
                if(count >= 4) return true;
            } else {
                count = 0;
            }
            startRow++;
            startCol++;
        }
        // Diagonal (bottom-left to top-right)
        count = 0;
        startRow = row;
        startCol = col;

        while(startRow < ROWS-1 && startCol > 0) {
            startRow++;
            startCol--;
        }

        while(startRow >= 0 && startCol < COLS) {
            if(board[startRow][startCol] == p) {
                count++;
                if(count >= 4) return true;
            } else {
                count = 0;
            }

            startRow--;
            startCol++;
        }
        return false;
    }



    /*
     * Load click sound
     */
    private void loadSound() {

        try {
            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(new File("click.wav"));

            clickSound = AudioSystem.getClip();

            clickSound.open(audio);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /*
     * Play click sound
     */
    private void playSound() {

        if (clickSound != null) {

            clickSound.stop();

            clickSound.setMicrosecondPosition(0);

            clickSound.start();
        }
    }

    /*
     * Main Method
     */
    public static void main(String[] args) {
        new Game ();
    }
}