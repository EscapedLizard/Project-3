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
     * ENUM PIECE:
     * This enum represents the possible contents of each cell on the board.
     * Instead of relying on images to track the game state, we store the logical state using this enum.
     *
     * RED      -->   Player 2's piece
     * BLACK    -->   Player1's piece
     * EMPTY    -->   No piece in that location
     */
    public enum Piece {
        RED,
        BLACK,
        EMPTY
    }

    // Constants representing board size
    private static final int ROWS = 6;
    private static final int COLS =7;

    /*
     * 2D Array storing the game state
     * Each position contains RED, BLACK, or EMPTY.
     */
    private Piece[][] board;

    /*
     * labels [][] stores the visual board cells.
     * Each JLabel displays an image (slot, red piece, black piece, etc.).
     */
    private JLabel[][] labels;

    /*
     * buttons [] represents the buttons at the top of each column.
     * Clicking a button drops a pieces into that column.
     */
    private JButton[] buttons;

    // Label showing whose turn it currently is
    private JLabel statusLabel;

    // Tracks which player is currently playing
    private Piece currentPlayer;

    // Counts number of moves played (used to detect tie)
    private int moveCount = 0;

    // Sound clip used when a piece is dropped
    private Clip clickSound;



    /*
     * Constructor: Game()
     * This method builds the entire graphical user interface.
     * It creates the window, board, buttons, and initializes the game state.
     */
    public Game() {
        // Set window title
        setTitle("Connect 4");

        // Set window size
        setSize(700, 600);

        // Close program when window closes
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use BorderLayout for main window
        setLayout(new BorderLayout());

        /*
         * Create the label showing which player's turn it is.
         * Initially Player 1 (Black) starts.
         */
        statusLabel = new JLabel("Player 1 (Black) Turn");

        // Center the text
        statusLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add label to top of window
        add(statusLabel, BorderLayout.NORTH);

        /*
         * Create board panel.
         * The board contains:
         *      7 columns and 7 rows
         *  Top row = buttons
         * Remaining 6 rows = game slots
         */
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(7, 7));

        // Initialize arrays
        board = new Piece[ROWS][COLS];
        labels = new JLabel[ROWS][COLS];
        buttons = new JButton[COLS];

        /*
         *Fill the board array with EMPTY values meaning no pieces are placed yet.
         */
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = Piece.EMPTY;
            }
        }

        /*
         *Create the columns buttons (top row).
         * When clicked, they call dropPiece(column).
         */
        for (int c = 0; c < COLS; c++) {

            JButton button = new JButton();

            // Adjust margins so icons look correct
            button.setMargin(new Insets(-5, -5, -5, -5));

            int column = c;

            // Add click listener to drop piece
            button.addActionListener(e -> dropPiece(column));

            buttons[c] = button;

            boardPanel.add(button);
        }
        /*
         * Create visual board cells.
         * Each cell initially displays the empty slot image.
         */
        for (int r = 0; r < ROWS; r++) {
            for (int c =0; c < COLS; c++) {

                JLabel label = new JLabel();

                // Set image for empty slot
                label.setIcon(new ImageIcon("slot.png"));
            }
        }
    }
}
