import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;


/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener {

    // ADD YOUR INSTANCE VARIABLES HERE

    private GameView gameView;
    private GameModel gameModel;

    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int height, int numberOfMines) {

    // ADD YOU CODE HERE

        gameModel = new GameModel(width, height, numberOfMines);
        gameView = new GameView(gameModel,this);

    }


    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
        
    // ADD YOU CODE HERE

    	if (e.getActionCommand().equals("Reset")) {
			this.reset();
		}

    	else if (e.getActionCommand().equals("Quit")) {
            System.exit(0);
		}

        else{

            for (int i=0;i<gameModel.getWidth();i++){
                for (int j=0;j<gameModel.getHeigth();j++){

                    if (e.getActionCommand().equals(Integer.toString((gameModel.getWidth()*j+i)))){
                        if(!(gameModel.hasBeenClicked(i,j))){
                            gameModel.step();
                        }
                        gameModel.click(i,j);
                        play(i,j);
                        System.out.print(gameModel.toString()); 

                }
            }
        }

    }

    }

    /**
     * resets the game
     */
    private void reset(){

    // ADD YOU CODE HERE

        gameModel.reset();
        gameView.update();

    }

    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares. 
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param width
     *            the selected column
     * @param heigth
     *            the selected line
     */
    private void play(int width, int heigth){

    // ADD YOU CODE HERE

        Object[] choices = {"Quit", "Play Again"};

        if (gameModel.get(width, heigth).isMined()){

                gameModel.uncoverAll();
                gameView.update();
                int r=JOptionPane.showOptionDialog(null, "Aouch, you lost in "+Integer.toString(gameModel.getNumberOfSteps())+" steps!\nWould you like to play again?","Boom!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, null);

                if (r == JOptionPane.YES_OPTION) {

                    System.exit(0);
                }

                else if (r == JOptionPane.NO_OPTION) {

                    gameModel.reset();
                    gameView.update();
                    
                }
        }

        else if(gameModel.isCovered(width, heigth)){

                gameModel.click(width ,heigth);
                gameModel.uncover(width, heigth);

                    if(gameModel.isFinished()){
                    gameModel.uncoverAll();
                    gameView.update();
                    int r=JOptionPane.showOptionDialog(null,"Congratulations! You won in "+Integer.toString(gameModel.getNumberOfSteps())+" steps!\nWould you like to play again?","Won", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, null);
                
                        if (r == JOptionPane.YES_OPTION) {

                            System.exit(0);

                        }
                        else if (r == JOptionPane.NO_OPTION) {

                            gameModel.reset();
                            gameView.update();

                        }
                    }
                        // Add the code for clear zone here
                        else if (gameModel.isBlank(width,heigth)){

                        clearZone(gameModel.get(width,heigth));
                        
                        }
            }
        
        gameView.update();
    }

   /**
     * <b>clearZone</b> is the method that computes which new dots should be ``uncovered'' 
     * when a new square with no mine in its neighborood has been selected
     * @param initialDot
     *      the DotInfo object corresponding to the selected DotButton that
     * had zero neighbouring mines
     */
    private void clearZone(DotInfo initialDot) {


    // ADD YOU CODE HERE

    GenericArrayStack <DotInfo> Stack;

        Stack = new GenericArrayStack<DotInfo>(gameModel.getWidth() * gameModel.getHeigth());
        Stack.push(initialDot);
        DotInfo Button;

        while(!(Stack.isEmpty())){
            Button = Stack.pop();

                for (int i = Button.getY()-1; i < Button.getY()+2; i++){
                    for(int j = Button.getX()-1; j < Button.getX()+2; j++){

                        if((i >= 0 && i < gameModel.getHeigth()) && (j >= 0 && j < gameModel.getWidth())){
                            if(!gameModel.isMined(j,i) && gameModel.isCovered(j,i)){

                                gameModel.uncover(j,i);

                                if(gameModel.isBlank(j,i)){

                                    Stack.push(gameModel.get(j,i));

                                }
                            }
                        }
                    }
                }
                
                
        }

    }




}
