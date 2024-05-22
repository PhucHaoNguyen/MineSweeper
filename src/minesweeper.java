import java.util.Random;
public class minesweeper{
    private Random random;
    
    private static final char MINE = 'X';
    private static final char UNREVEALED = '|';
    private static final char[] DIGITS = {'0','1','2','3','4','5','6','7','8'};
    //Setting up the number of mines near by in a 3x3 space around the place revealed

    public int numMines;
    public int numRevealed = 0;
    //default none revealed
    public char[][] board;
    public boolean[][] mines;
    public boolean[][] revealed;

    public minesweeper(){
        board = new char[10][10];
        revealed = new boolean[10][10];
        mines = new boolean[10][10];
        random = new Random();
    //board size is 10x10 set-in-stone for now
    }

    public void initializeBoard(){
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                board[row][col] = UNREVEALED;
                revealed[row][col] = false;
                mines[row][col] = false;
            }
        }

        int numAdded = 0;
        while (numAdded < numMines) {
            int row = random.nextInt(10);
            int col = random.nextInt(10);

            if (!mines[row][col]) {
                mines[row][col] = true;
                numAdded++;
            }
        }
        GameStack = new GameStack(board, revealed, mines);
    }

    public void revealCell(){

    }

    private void revealAdjacentCells(int row, int col){

    }

    private int countAdjacentMines(int row, int col){
        return count;
    }

}