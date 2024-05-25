import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.sound.sampled.*;
public class GameStart extends JPanel {
    public GameMinesweeper Game = new GameMinesweeper();

    private static final int Symbolsize = 90;

    private Image GameImage;
    private Image QuitImage;
    private Image MusicOnImage;
    private Image MusicOffImage;
    private Image FlagButtonImage;
    private Image UndoImage;

    enum SymbolImport {
        ONE("images/1.png"),
        TWO("images/2.png"),
        THREE("images/3.png"),
        FOUR("images/4.png"),
        FIVE("images/5.png"),
        SIX("images/6.png"),
        SEVEN("images/7.png"),
        EIGHT("images/8.png"),
        FLAG("images/Flag.png"),
        MINE("images/Mine.png"),
        UNREVEALED("images/Unrevealed.png");

        private String imagePath;

        SymbolImport(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getImagePath() {
            return imagePath;
        }
    }
