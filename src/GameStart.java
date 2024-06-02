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
        Game.initializeBoard();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                if (!gameWon && !gameLost) {
                    if (mouseX >= GameSettings.AdjustWidth(1742)
                            && mouseX <= GameSettings.AdjustWidth(1742) + GameSettings.AdjustWidth(140)
                            && mouseY >= GameSettings.AdjustHeight(38)
                            && mouseY <= GameSettings.AdjustHeight(38) + GameSettings.AdjustHeight(140)) {
                        BGMOn = !BGMOn;
                        if (BGMOn) {
                            musicClip.start();
                        } else {
                            musicClip.stop();
                        }
                        repaint();
                    }

                    if (mouseX >= GameSettings.AdjustWidth(1080)
                            && mouseX <= GameSettings.AdjustWidth(1080 + UndoImage.getWidth(null))
                            && mouseY >= GameSettings.AdjustHeight(698)
                            && mouseY <= GameSettings.AdjustHeight(698 + UndoImage.getHeight(null))) {
                        Game.Undo();
                        repaint();
                    }

                    if (mouseX >= GameSettings.AdjustWidth(Symbolsize)
                            && mouseX <= GameSettings.AdjustWidth(Symbolsize * 11)
                            && mouseY >= GameSettings.AdjustHeight(Symbolsize)
                            && mouseY <= GameSettings.AdjustHeight(Symbolsize * 11)) {
                        int row = (int) (mouseY / GameSettings.AdjustHeight(Symbolsize)) - 1;
                        int col = (int) (mouseX / GameSettings.AdjustWidth(Symbolsize)) - 1;

                        Game.Save();

                        if (Game.board[row][col] == '-') {
                            Game.revealCell(row, col);
                            if (Game.mines[row][col]) {
                                gameLost = true;
                                try {
                                    playlose();
                                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
                                    e1.printStackTrace();
                                }
                                repaint();

                            } else if (Game.numRevealed == 100 - Game.numMines) {
                                gameWon = true;
                                try {
                                    playwin();
                                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
                                    e1.printStackTrace();
                                }
                                repaint();
                            } else {
                                repaint();
                            }
                        }
                    }
                }

                if (mouseX >= GameSettings.AdjustWidth(1080)
                        && mouseX <= GameSettings.AdjustWidth(1080 + UndoImage.getWidth(null))
                        && mouseY >= GameSettings.AdjustHeight(858)
                        && mouseY <= GameSettings.AdjustHeight(858 + UndoImage.getHeight(null))) {
                    System.exit(0);
                }
            }

        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!gameWon && !gameLost) {
                    if (e.getX() >= GameSettings.AdjustWidth(1578)
                            && e.getX() <= GameSettings.AdjustWidth(1578) + GameSettings.AdjustWidth(140)
                            && e.getY() >= GameSettings.AdjustHeight(38)
                            && e.getY() <= GameSettings.AdjustHeight(38) + GameSettings.AdjustHeight(140)) {
                        flagLocation = e.getPoint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (flagLocation != null) {
                    int x = e.getX();
                    int y = e.getY();

                    if (x >= GameSettings.AdjustWidth(Symbolsize)
                            && x <= GameSettings.AdjustWidth(Symbolsize * 11)
                            && y >= GameSettings.AdjustHeight(Symbolsize)
                            && y <= GameSettings.AdjustHeight(Symbolsize * 11)) {
                        int row = (int) (y / GameSettings.AdjustHeight(Symbolsize)) - 1;
                        int col = (int) (x / GameSettings.AdjustWidth(Symbolsize)) - 1;
                        Game.Save();
                        if (Game.board[row][col] == '-') {
                            Game.board[row][col] = 'F';
                        } else if (Game.board[row][col] == 'F') {
                            Game.board[row][col] = '-';
                        }
                    }
                    flagLocation = null;
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (flagLocation != null) {
                    flagLocation.setLocation(e.getPoint());
                    repaint();
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(GameImage, 0, 0, this);

        if (BGMOn) {
            g.drawImage(MusicOnImage, GameSettings.AdjustWidth(1742), GameSettings.AdjustHeight(38), this);
        } else {
            g.drawImage(MusicOffImage, GameSettings.AdjustWidth(1742), GameSettings.AdjustHeight(38), this);
        }

        g.drawImage(FlagButtonImage, GameSettings.AdjustWidth(1578), GameSettings.AdjustHeight(38), this);
        g.drawImage(UndoImage, GameSettings.AdjustWidth(1080), GameSettings.AdjustHeight(698), this);
        g.drawImage(QuitImage, GameSettings.AdjustWidth(1080), GameSettings.AdjustHeight(858), this);
