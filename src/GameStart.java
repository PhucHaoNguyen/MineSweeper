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
    private Image[] symbolImages = new Image[SymbolImport.values().length];

    private Image TickImage;
    private Image CrossImage;

    private Clip musicClip;
    private boolean BGMOn;

    private boolean revealAll = false;
    private boolean gameWon = false;
    private boolean gameLost = false;

    private Point flagLocation = null;

        public int getheight() {
            return height;
        }
    }
    
    public GameStart(boolean music, int numMines) {
        Game.numMines = numMines;

        this.BGMOn = music;

        setPreferredSize(new Dimension(GameSettings.WIDTH, GameSettings.HEIGHT));

        try {
            File GamePlay = new File("images/GamePlay.png");
            BufferedImage GamePlayOriginal = ImageIO.read(GamePlay);
            Image scaledMenu = GamePlayOriginal.getScaledInstance(GameSettings.WIDTH, GameSettings.HEIGHT,
                    Image.SCALE_SMOOTH);
            GameImage = scaledMenu;

            for (ButtonImport button : ButtonImport.values()) {
                File buttonFile = new File(button.getPath());
                BufferedImage buttonOriginal = ImageIO.read(buttonFile);
                Image scaledButton;

                if (button == ButtonImport.MUSIC_ON || button == ButtonImport.MUSIC_OFF
                        || button == ButtonImport.QUIT || button == ButtonImport.UNDO || button == ButtonImport.FLAG) {
                    scaledButton = buttonOriginal.getScaledInstance(
                            GameSettings.AdjustWidth(128),
                            GameSettings.AdjustHeight(128),
                            Image.SCALE_SMOOTH);
                } else {
                    scaledButton = buttonOriginal.getScaledInstance(
                            GameSettings.AdjustWidth(443),
                            GameSettings.AdjustHeight(124),
                            Image.SCALE_SMOOTH);
                }

                switch (button) {
                    case QUIT:
                        QuitImage = scaledButton;
                        break;
                    case FLAG:
                        FlagButtonImage = scaledButton;
                        break;
                    case UNDO:
                        UndoImage = scaledButton;
                        break;
                    case MUSIC_ON:
                        MusicOnImage = scaledButton;
                        break;
                    case MUSIC_OFF:
                        MusicOffImage = scaledButton;
                        break;
                }
            }
    }

    File bgm = new File("sounds/Music.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bgm);

            musicClip = AudioSystem.getClip();
            musicClip.open(audioIn);

            if (BGMOn) {
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                musicClip.start();
            }

            File TickSign = new File("images/Tick.png");
            BufferedImage TickSignOriginal = ImageIO.read(TickSign);
            Image scaledTick = TickSignOriginal.getScaledInstance(GameSettings.AdjustWidth(392),
                    GameSettings.AdjustHeight(368),
                    Image.SCALE_SMOOTH);
            TickImage = scaledTick;

            File CrossSign = new File("images/Cross.png");
            BufferedImage CrossSignOriginal = ImageIO.read(CrossSign);
            Image scaledCross = CrossSignOriginal.getScaledInstance(GameSettings.AdjustWidth(640),
                    GameSettings.AdjustHeight(640),
                    Image.SCALE_SMOOTH);
            CrossImage = scaledCross;

            for (SymbolImport symbolType : SymbolImport.values()) {
                File symbolFile = new File(symbolType.getImagePath());
                BufferedImage symbolImageOriginal = ImageIO.read(symbolFile);
                Image scaledSymbol = symbolImageOriginal.getScaledInstance(
                        GameSettings.AdjustWidth(Symbolsize),
                        GameSettings.AdjustHeight(Symbolsize),
                        Image.SCALE_SMOOTH);
                symbolImages[symbolType.ordinal()] = scaledSymbol;
            }

        } catch (Exception e) {
            System.out.println("Exception found: " + e);
        }
