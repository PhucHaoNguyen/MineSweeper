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

    }

    public void initializeBoard(){

    }

    public void revealCell(){

    }

    private void revealAdjacentCells(int row, int col){

    }

    private int countAdjacentMines(int row, int col){
        return count;
    }

}