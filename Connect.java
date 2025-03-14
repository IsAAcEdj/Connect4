import java.util.*;

// The template class
public class Connect {

  /*
    ---------------------------------Variables-------------------------------------
   */
    public static int input;
    //Number of columns constant
    public static final int COL_SIZE = 7;
    //Number of rolls constant
    public static final int ROW_SIZE = 6;


    // Strings for piece representation
    // PIECE[0] = " "
    // PIECE[1] = "X"
    // PIECE[2] = "0"
    public static final String[] PIECE
            = new String[]{ " ", "X", "O" };


    // same with PIECE but has color!
    public static final String[] COLOR_PIECE
            = new String[]{ " ",
            "\033[38;5;00m\033[48;5;10mX\033[0;0m",
            "\033[38;5;15m\033[48;5;9mO\033[0;0m"
    };



    /*
    ---------------------------------Methods-------------------------------------
   */


    /**
     * @return whether the column index is valid
     * @param	col	the column index
     */
    public static boolean isValidPosition( int col ) {
        return col >= 0 && col < COL_SIZE;
    }



    /**
     * @return whether column index and row index are valid
     * @param	col	the column index
     * @param	row	the row index
     */
    public static boolean isValidPosition( int col, int row ) {
        return col >= 0 && col < COL_SIZE
                && row >= 0 && row < ROW_SIZE;
    }



    /**
     * @return the next available row index, -1 if non
     * @param	board the board
     * @param	col	the column index
     */
    public static int nextRow( String[] board, int col ) {
        if ( isValidPosition( col ) ) {
            if ( board[ col ].length() < ROW_SIZE ) {
                return board[ col ].length();
            }
        }
        return -1;
    }



    /**
     * @return whether the column has space for a new piece
     * @param	board the board
     * @param	col	the column index
     */
    public static boolean isOpen( String[] board, int col ) {
        return nextRow( board, col ) >= 0;
    }



    /**
     * @return whether the board has an open column
     * @param	board the board
     */
    public static boolean isOpen( String[] board ) {
        for ( int col = 0; col < COL_SIZE; col ++ ) {
            if ( isOpen( board, col ) ) {
                return true;
            }
        }
        return false;
    }



    /**
     * @return the piece char at the given position
     * @param	board the board
     * @param	col the column index
     * @param	row the row index
     */
    public static int getPiece( String[] board, int col, int row ) {
        if ( isValidPosition( col, row ) ) {
            if ( row >= board[ col ].length() ) { return 0; }
            return board[ col ].charAt( row ) - '0';
        }
        return -1;
    }



    /**
     * Add a piece to the given column
     * @param	board the board
     * @param	col	the column index
     * @param	player the player number
     */
    public static void add( String[] board, int col, int player ) {
        board[ col ] = board[ col ] + player;
    }



    /**
     * Remove a piece from the given column
     * @param	board the board
     * @param	col	the column index
     */
    public static void remove( String[] board, int col ) {
        board[ col ] = board[ col ].substring(
                0, board[ col ].length() - 1 );
    }



    /**
     * Get a column choice from player
     * @param	board the board
     * @return the column in which to place the piece
     */
    public static int inputCol( String[] board ) {
        Scanner keyboard = new Scanner( System.in );
        int col, row;
        do {
            System.out.print( "\nEnter column: " );
            col = keyboard.nextInt();
        } while ( !isOpen( board, col ) );
        return col;
    }



    /**
     * @return an initial board
     */
    public static String[] initialize() {
        String[] board = new String[ COL_SIZE ];
        for ( int i = 0; i < COL_SIZE; i ++ ) {
            board[ i ] = "";
        }
        return board;
    }


  /*
    ---------------------------------Project Part1-------------------------------------
  */


    /**
     * Present the board on the screen
     *
     * The parameter is a String[] variable board, which is
     * instantiated in the main().
     *
     * Execute a double for-loop, where
     *   the external loop is for the column index
     *   the internal loop is for the row index (backwards).
     *
     * For each column-row index pair,
     * 1. obtain the board element using getNumber method
     * 2. obtain the String corresponding to the element
     *   in the PIECE array (this is a static constant)
     * 3. print the String obtained in (2)
     * 4. print a newline when an internal loop is complete
     * Print dashes and the column numbers
     */
    static void present( String[] board ) {

        /**
         * @TODO print out the board by printing a PIECE, find location with getPiece() method
         **/
        System.out.println("-----------------------------------\n");
        for (int i = ROW_SIZE; i > 0; i--) {
            for (int j = 0; j < COL_SIZE; j++) {
                System.out.printf("| %s |", COLOR_PIECE[getPiece(board, j, i - 1)]);
            }
            System.out.println("\n-----------------------------------\n");
        }
        System.out.println("| 0 || 1 || 2 || 3 || 4 || 5 || 6 |");
        /**
         * @TODO print the column numbers
         */

    }




    /**
     * @return if the player has won the game
     * @param	board the board
     * @param	player the int representing the player
     *
     * Using four double loops, conduct searches in the
     * four directions. In each direction do the following:
     * Use a double-loop to generate a starting position of
     * the search. For each position,
     * . use the method getNumber to obtain four numbers
     * . return true if the obtained the numbers are
     *   equal to the player number
     * If none of the searches resulted in finding four
     * consecutive elements equal to the player number,
     * return false.
     */
    static boolean isWinner( String[] board, int player ) {
        /**
         * @TODO row-wise win check
         */
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COL_SIZE - 3; j++) {
                        if(getPiece(board, i, j) == player &&
                        getPiece(board, i+1, j) == player &&
                        getPiece(board, i+2 ,j) == player &&
                        getPiece(board, i+3, j) == player) {
                            return true;
                }
            }
        }
        /**
         * @TODO column-wise win check
         */
        for (int i = 0; i < COL_SIZE; i++) {
            for (int j = 0; j < ROW_SIZE - 3; j++) {
                if(getPiece(board, i, j) == player &&
                        getPiece(board, i, j+1) == player &&
                        getPiece(board, i ,j+2) == player &&
                        getPiece(board, i, j+3) == player) {
                    return true;
                }
            }
        }
        /**
         * @TODO diagonal win check
         */
        for (int i = 0; i < COL_SIZE - 3; i++) {
            for (int j = 0; j < ROW_SIZE - 3; j++) {
                if(getPiece(board, i, j) == player &&
                        getPiece(board, i+1, j+1) == player &&
                        getPiece(board, i+2, j+2) == player &&
                        getPiece(board, i+3, j+3) == player) {
                    return true;
                }
            }
        }
        /**
         * @TODO anti diagonal win check
         */
        for (int i = 3; i < COL_SIZE; i++) {
            for (int j = 0; j < ROW_SIZE; j++) {
                if(getPiece(board, i, j) == player &&
                        getPiece(board, i-1, j+1) == player &&
                        getPiece(board, i-2 ,j+2) == player &&
                        getPiece(board, i-3, j+3) == player) {
                    return true;
                }
            }
        }
        return false;
    }



    /**
     * the main() method
     */
    public static void main (String[] args ) {
        Scanner keyboard = new Scanner(System.in);
        String[] board;
        board = initialize();
        System.out.println("----CONNECT 4----");
        /* As long as the board is open and the winner does
         * not exist, do the following:
         * 1. Present the board
         * 2. Receive, from the user, a valid column number
         *    choice for Player 1.
         * 3. Play the piece on the board using the method add().
         * 4. Use isWinner() to determine if Player 1 has won.
         * 5. If Player 1 has won,
         *      present the board, report that Player 1 has won,
         *      and terminate the game.
         * 6. If Player 1 has not won, repeat 1 - 5 for Player 2.
         * When the board is not open and the winner does not
         * exist, present the board and reports that the game
         * was a draw.
         */
        while(isOpen(board)) {
            present(board);
            System.out.println("Player 1, Select a Column");
            input = keyboard.nextInt();
            while (!isValidPosition(input)) {
                System.out.println("INVALID Column Selection");
                input = keyboard.nextInt();
            }
            switch(input) {
                case 0:
                    add(board, 0, 1);
                    break;
                case 1:
                    add(board, 1, 1);
                    break;
                case 2:
                    add(board, 2, 1);
                    break;
                case 3:
                    add(board, 3, 1);
                    break;
                case 4:
                    add(board, 4, 1);
                    break;
                case 5:
                    add(board, 5, 1);
                    break;
                case 6:
                    add(board, 6, 1);
                    break;
            }
            if(isWinner(board, 1)){
                present(board);
                System.out.println("Player 1 Has Won!");
                break;
            }
            present(board);
            System.out.println("Player 2, Select a Column");
            input = keyboard.nextInt();
            while (input > COL_SIZE + 1 || input < 0 || !isOpen(board, input)) {
                System.out.println("INVALID Column Selection");
                input = keyboard.nextInt();
            }
            switch(input) {
                case 0:
                    add(board, 0, 2);
                    break;
                case 1:
                    add(board, 1, 2);
                    break;
                case 2:
                    add(board, 2, 2);
                    break;
                case 3:
                    add(board, 3, 2);
                    break;
                case 4:
                    add(board, 4, 2);
                    break;
                case 5:
                    add(board, 5, 2);
                    break;
                case 6:
                    add(board, 6, 2);
                    break;
            }
            if(isWinner(board, 2)){
                present(board);
                System.out.println("Player 2 Has Won!");
                break;
            }

        }
        if(!isWinner(board, 1) && (!isWinner(board, 2))){
            present(board);
            System.out.println("Draw. Nobody Wins.");
        }
    }


}