package gui;

import board.ChessBoard;
import board.ChessLocation;
import game.ChessGame;
import javafx.scene.layout.TilePane;
import pieces.ChessPiece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final ChessGame game;

    private ChessLocation sourceTile;
    private ChessLocation destinationTile;
    private ChessPiece humanMovedPiece;

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static String defaultPieceImagesPath = "/Users/Cameron/IdeaProjects/Chess/src/resources/";// /Users/Cameron/IdeaProjects/Chess/src/resources

    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");

    public Table(ChessGame game) {
        this.gameFrame = new JFrame("JChess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.game = game;
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open PGN");
            }
        });

        fileMenu.add(openPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    private class BoardPanel extends JPanel {
        final ArrayList<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    final TilePanel tilePanel = new TilePanel(this, new ChessLocation(row, col)); //TODO: get the row and col of each tile, not make a new location
                    this.boardTiles.add(tilePanel);
                    add(tilePanel);
                }
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final ChessBoard board) {
            removeAll();
            for (final TilePanel tilePanel : boardTiles) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel {

        private final ChessLocation tileLocation;

        TilePanel(final BoardPanel boardPanel, ChessLocation tileLocation) {
            super(new GridBagLayout());
            this.tileLocation = tileLocation;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(game.getBoard());

            addMouseListener(new MouseListener() { //TODO: Use MouseMotionListener to drag pieces to destination
                @Override
                public void mouseClicked(final MouseEvent e) {

                    if(isRightMouseButton(e)) { //right click cancels piece selection
                        //first click

                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;

                    } else if (isLeftMouseButton(e)) {

                        if (sourceTile == null) {
                            sourceTile = tileLocation;
                            humanMovedPiece = game.getBoard().getPieceAt(sourceTile);
                            if (humanMovedPiece == null) {
                                sourceTile = null;
                            }
                        } else {
                            destinationTile = tileLocation;
                            //final Move move = null;
                            //DO MOVE
                            //ADD MOVE TO MOVE LOG
                            humanMovedPiece.moveTo(destinationTile);
                            /*
                             */

                            sourceTile = null; //clear
                            destinationTile = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard(game.getBoard());
                            }
                        });
                    }

                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });



            validate();
        }

        public void drawTile (final ChessBoard board) {
            assignTileColor();
            assignTilePieceIcon(board);
            validate();
            repaint();
        }

        private void assignTilePieceIcon(final ChessBoard board) {
            this.removeAll();

            if(board.isPieceAt(this.tileLocation)) {

                try {
                    final BufferedImage image =
                            ImageIO.read(new File(defaultPieceImagesPath +
                                    board.getPieceAt(this.tileLocation).getPlayer().getName().substring(0, 1).toString().toUpperCase() +
                                    (board.getPieceAt(this.tileLocation).getID() + "").toUpperCase() + ".gif"));

                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void assignTileColor() {
            if (tileLocation.getRow() == 0 ||
                    tileLocation.getRow() == 2 ||
                    tileLocation.getRow() == 4 ||
                    tileLocation.getRow() == 6) {
                setBackground(tileLocation.getLocationNum() % 2 == 0 ?
                        darkTileColor : lightTileColor);
            } else {
                setBackground(tileLocation.getLocationNum() % 2 != 0 ?
                        darkTileColor: lightTileColor);
            }
        }

    }

}
