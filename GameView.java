import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>DotButton</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameView extends JFrame {

     // ADD YOUR INSTANCE VARIABLES HERE
    private DotButton[][] board;
    private GameModel gameModel;
    private javax.swing.JLabel nbreOfStepsLabel=null;

    /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {
        
    // ADD YOU CODE HERE
        this.gameModel = gameModel;

        setTitle("Minesweeper - ITI 1121 version");
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(gameModel.getHeigth(),gameModel.getWidth(),0,0));
        board = new DotButton[gameModel.getHeigth()][gameModel.getWidth()];

        
        int Id=0;
        for (int i=0; i<gameModel.getHeigth(); i++){
            for (int j=0; j<gameModel.getWidth(); j++) {

                board[i][j] = new DotButton(j,i,11);
                board[i][j].setPreferredSize(new Dimension(28,28));
                board[i][j].addActionListener(gameController);
                board[i][j].setActionCommand(Integer.toString(Id));
                Id++;
                p.add(board[i][j]);
                
            }
            
        }

        // For buttons and text field
        JPanel p2 = new JPanel();

        JButton b1 = new JButton("Reset");
        b1.addActionListener(gameController);
        
        JButton b2 = new JButton("Quit");
        b2.addActionListener(gameController);

        nbreOfStepsLabel = new JLabel("Number of steps: " + Integer.toString(gameModel.getNumberOfSteps()));

        p2.add(nbreOfStepsLabel);
        p2.add(b1);
        p2.add(b2);
        

        add(p, BorderLayout.NORTH);
        add(p2, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);



    }

    /**
     * update the status of the board's DotButton instances based 
     * on the current game model, then redraws the view
     */

    public void update(){
        
    // ADD YOU CODE HERE
        nbreOfStepsLabel.setText("Number of steps: " + Integer.toString(gameModel.getNumberOfSteps()));
        for (int i=0;i<gameModel.getHeigth();i++){
            for (int j=0; j<gameModel.getWidth();j++){
                board[i][j].setIconNumber(getIcon(j,i));
        }
    }  
        pack();

    }

    /**
     * returns the icon value that must be used for a given dot 
     * in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */   
    private int getIcon(int i, int j){
        
    //ADD YOU CODE HERE


        if (gameModel.isCovered(i,j)){
            return 11;
        }
        else if(gameModel.isMined(i,j)) {
            if (gameModel.hasBeenClicked(i,j)){
                return 10;
            }
            return 9;
        }

        else{
            return gameModel.getNeighbooringMines(i,j);
        }

    }


}
