package edu.vt.cs5044;

import static edu.vt.cs5044.DABGuiName.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// 2023.Spring

/**
 * class for DAB game GUI
 * Follow it with additional details about its purpose
 * and how to use it.
 *
 * @author nilotpal
 * @version Apr 27, 2023
 *
 */
@SuppressWarnings("serial")
public class DABPanel extends JPanel {

    private final DotsAndBoxes game;

    private final JLabel p1ScoreLabel;
    private final JLabel p2ScoreLabel;
    private final JLabel turnLabel;
    private final JComboBox<Integer> xCombo;
    private final JComboBox<Integer> yCombo;
    private final JComboBox<Direction> dirCombo;
    private final JButton drawButton;
    private final DABGrid dabGrid;
    /**
     * 
     * Construct a new DABPanel object.
     *
     * @param frame Jframe object
     */
    public DABPanel(JFrame frame) {

        // Add a menu bar to the frame that will contain this panel
        frame.setJMenuBar(setupMenuBar());

        // Create a new DABGame instance that will act as the game engine
        game = new DABGame();

        // Next we construct the user interface components, and assign a unique name to each
        // The name is required so that the test code can identify and fetch each component

        // This combo box is initially empty; you'll add option values in updateCombos() below
        xCombo = new JComboBox<>();
        xCombo.setName(X_COMBO);

        // This combo box is initially empty; you'll add option values in updateCombos() below
        yCombo = new JComboBox<>();
        yCombo.setName(Y_COMBO);

        // This combo box is pre-populated with all the valid Direction enum values
        dirCombo = new JComboBox<>(Direction.values());
        dirCombo.setName(DIR_COMBO);

        // The draw button will attempt to draw an edge, as specified by the combo box selections
        drawButton = new JButton("Draw!");
        drawButton.setName(DRAW_BUTTON);
        drawButton.addActionListener(this::handleDrawButton);
        // NOTE: For any credit, an anonymous inner class must be used
        // NOTE: For more credit, a lambda expression must be used instead
        // NOTE: For full credit, a method reference must be used instead

        // The JLabel components begin empty; you'll set the relevant text in updateStatus() below
        turnLabel = new JLabel("");
        turnLabel.setName(TURN_LABEL);

        p1ScoreLabel = new JLabel("");
        p1ScoreLabel.setName(P1_SCORE_LABEL);

        p2ScoreLabel = new JLabel("");
        p2ScoreLabel.setName(P2_SCORE_LABEL);

        // This is the custom DABGrid component provided by the library
        dabGrid = new DABGrid(game);
        dabGrid.setName(DAB_GRID);

        // Perform the layout of all the user interface components
        setupLayout();

        // Begin a new 3x3 game by default
        startGame(3);

    }
    /**
     * 
     * method for draw button action handler
     *
     * @param ae ActionEvent
     */
    private void handleDrawButton(ActionEvent ae) {
        int x = (int) xCombo.getSelectedItem();
        int y = (int) yCombo.getSelectedItem();
        Coordinate coord = new Coordinate(x, y);
        Direction dir = (Direction) dirCombo.getSelectedItem();
        boolean isEdgeDrawn = game.drawEdge(coord, dir);
        updateStatus(isEdgeDrawn);
    }
    /**
     * 
     * method to update status of the current game
     *
     * @param updateRequired true or false
     */
    private void updateStatus(boolean updateRequired) {
        if (!updateRequired) {
            return;
        }
        // update p1ScoreLabel, p2ScoreLabel, turnLabel
        Map<Player, Integer> scores = game.getScores();
        int p1Score = scores.get(Player.ONE);
        int p2Score = scores.get(Player.TWO);
        p1ScoreLabel.setText("ONE: " + String.valueOf(p1Score) + "                      ");
        p2ScoreLabel.setText("                          " + "TWO: " + String.valueOf(p2Score));
        Player player = game.getCurrentPlayer();
        if (player == Player.ONE) {
            turnLabel.setText("Player ONE Go!");
        }
        if (player == Player.TWO) {
            turnLabel.setText("Player TWO Go!");
        }
        if (player == null) {
            drawButton.setEnabled(false);
            turnLabel.setText("OVER");
        }
        repaint();
    }
    /**
     * 
     * method to update x and y coordinates
     *
     */
    private void updateCombos() {
        // NOTE: For any credit, the combo options must handle games up to a size of 4x4
        // NOTE: For more credit, the combo options must exactly match the size of the game
        // NOTE: For full credit, a single loop must be used to eliminate redundancies
        int size = game.getSize();
        xCombo.removeAllItems();
        yCombo.removeAllItems();
        for (int i = 0; i < size; i++) {
            xCombo.addItem(i);
            yCombo.addItem(i);
        }
    }
    /** 
     * 
     * method to start a new game
     *
     * @param size grid size in terms of boxes
     */
    private void startGame(int size) {
        game.init(size);
        updateCombos();
        updateStatus(true);
        if (!drawButton.isEnabled()) {
            drawButton.setEnabled(true); 
        }
        repaint();
    }
    /**
     * 
     * method to set up game layout
     *
     */
    private void setupLayout() {
        // NOTE: For any credit, the layout must include all components
        // NOTE: For more credit, the layout must look reasonably neat
        // NOTE: For full credit, the layout must be reasonably responsive to resizing of the frame
        JPanel topPanel = new JPanel();
        topPanel.add(turnLabel);
        
        JPanel middlePanel = new JPanel();
        middlePanel.add(xCombo);
        middlePanel.add(yCombo);
        middlePanel.add(dirCombo);
        middlePanel.add(drawButton);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(p1ScoreLabel);
        bottomPanel.add(p2ScoreLabel);
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        add(topPanel);
        add(middlePanel);
        add(dabGrid);
        add(bottomPanel);
    }
    /**
     * 
     * method to set up menu bar
     *
     * @return menuBar, which is a JMenuBar type
     */
    private JMenuBar setupMenuBar() {
        // NOTE: For any credit, anonymous inner classes must be used
        // NOTE: For more credit, lambda expressions must be used instead
        // NOTE: For full credit, a method reference must be used within the interactive mode lambda
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenu newSubMenu = new JMenu("New");
        JMenuItem twoByTwo = new JMenuItem("2x2");
        twoByTwo.addActionListener(ae -> startGame(2));
        JMenuItem threeByThree = new JMenuItem("3x3");
        threeByThree.addActionListener(ae -> startGame(3));
        JMenuItem fourByFour = new JMenuItem("4x4");
        fourByFour.addActionListener(ae -> startGame(4));
        JCheckBoxMenuItem interactiveMenuItem = new JCheckBoxMenuItem();
        interactiveMenuItem.setText("set interactive");
        interactiveMenuItem.addActionListener(ae -> {
                if (interactiveMenuItem.isSelected()) {
                    dabGrid.setListener(this::updateStatus);
                }
                else {
                    dabGrid.removeListener();
                }
        }
        );
        
        menuBar.add(gameMenu);
        gameMenu.add(newSubMenu);
        gameMenu.add(interactiveMenuItem);
        newSubMenu.add(twoByTwo);
        newSubMenu.add(threeByThree);
        newSubMenu.add(fourByFour);
        
        return menuBar; 
    }
    /**
     * 
     * method to create and show GUI
     *
     */
    private static void createAndShowGUI() {
        // This is boilerplate code; please just use this exactly as it is.
        JFrame frame = new JFrame("Dots And Boxes");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JComponent newContentPane = new DABPanel(frame);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }
    /**
     * 
     * mnain method
     *
     * @param args String
     */
    public static void main(String[] args) {
        // This is boilerplate code; please be sure to use this exactly as-is.
        // Note that a method reference is used to simplify the syntax
        SwingUtilities.invokeLater(DABPanel::createAndShowGUI);
    }

}
