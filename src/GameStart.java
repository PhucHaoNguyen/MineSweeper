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
enum ButtonImport {
        QUIT("images/QuitButton.png", 120, 124),
        FLAG("images/FlagButton.png", 140, 140),
        UNDO("images/UndoButton.png", 443, 124),
        MUSIC_ON("images/MusicOnButton.png", 140, 140),
        MUSIC_OFF("images/MusicOffButton.png", 140, 140);

        private final String path;
        private final int width;
        private final int height;

        ButtonImport(String path, int width, int height) {
            this.path = path;
            this.width = width;
            this.height = height;
        }

        public String getPath() {
            return this.path;
        }

        public int getwidth() {
            return width;
        }

        public int getheight() {
            return height;
        }
    }
