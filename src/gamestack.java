import java.util.Stack;
public class gamestack {
    private char[][] board;
    private boolean[][] revealed;
    private boolean[][] mines;
    private Integer numRevealed;

    private Stack<char[][]> boardStack;
    private Stack<boolean[][]> revealedStack;
    private Stack<boolean[][]> minesStack;
    private Stack<Integer> numRevealedStack;

    public GameStack(char[][] board, boolean[][] revealed, boolean[][] mines) {
        
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean[][] getRevealed() {
        return revealed;
    }

    public boolean[][] getMines() {
        return mines;
    }

    public int getnumRevealed() {
        return numRevealed;
    }

    public boolean canUndo() {
        return !boardStack.isEmpty();
    }

    public void saveSnapshot() {
        
    }

    public void undo() {
        
    }
}