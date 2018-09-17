import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. 
 * It stores the following information:
 * - the state of all the ``dots'' on the board (mined or not, clicked
 * or not, number of neighbooring mines...)
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough 
 *  appropriate Getters. 
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class GameModel {


     // ADD YOUR INSTANCE VARIABLES HERE

    private int numberOfSteps;
    private int neighbooringMines;
    private int numberOfMines;
    private int numberUncovered;
    private int heigthOfGame; 
    private DotInfo[][] model; 
    private int widthOfGame;
    private Random generator;





    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param width
     *            the width of the board
     * 
     * @param heigth
     *            the heigth of the board
     * 
     * @param numberOfMines
     *            the number of mines to hide in the board
     */
    public GameModel(int width, int heigth, int numberOfMines) {
        
    // ADD YOU CODE HERE
        this.widthOfGame = width;
        this.heigthOfGame = heigth;
        this.numberOfMines = numberOfMines;
        reset();

    }


 
    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */
    public void reset(){

        
    // ADD YOU CODE HERE
        numberOfSteps=0;
        numberUncovered=0;
        model = new DotInfo[heigthOfGame][widthOfGame];
        generator = new Random(); 

        for (int i=0; i<heigthOfGame; i++){
            for(int j=0; j<widthOfGame; j++){
                model[i][j] = new DotInfo(j,i);
            }
        }

        int num = 0;
        while(num < numberOfMines){
                int x = generator.nextInt(heigthOfGame);
                int y = generator.nextInt(widthOfGame);
                if (!model[x][y].isMined()) {
                    model[x][y].setMined();
                    num++;
                }
        }

        for(int i=0; i<heigthOfGame; i++){
            for(int j=0; j<widthOfGame; j++){
                if(!model[i][j].isMined()){
                    int neighbooringMines = 0;

                for(int z=i-1; z<i+2; z++){
                    for(int w=j-1; w<j+2;w++){
                        if(w>=0 && w<widthOfGame && z>=0 && z<heigthOfGame){
                            if(model[z][w].isMined()){
                                neighbooringMines++;

                            }

                        }

                    }
                }
                model[i][j].setNeighbooringMines(neighbooringMines);

            }

        }

    }
}


    /**
     * Getter method for the heigth of the game
     * 
     * @return the value of the attribute heigthOfGame
     */   
    public int getHeigth(){
        
    // ADD YOU CODE HERE
        return heigthOfGame;

    }

    /**
     * Getter method for the width of the game
     * 
     * @return the value of the attribute widthOfGame
     */   
    public int getWidth(){
        
    // ADD YOU CODE HERE
        return widthOfGame;

    }



    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isMined(int i, int j){
        
    // ADD YOU CODE HERE
        return model[j][i].isMined();

    }

    /**
     * returns true if the dot  at location (i,j) has 
     * been clicked, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean hasBeenClicked(int i, int j){
        
    // ADD YOU CODE HERE
        return model[j][i].hasBeenClicked();


    }

  /**
     * returns true if the dot  at location (i,j) has zero mined 
     * neighboor, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isBlank(int i, int j){
        
    // ADD YOU CODE HERE
        return model[j][i].getNeighbooringMines()==0;

    }
    /**
     * returns true if the dot is covered, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isCovered(int i, int j){
        
    // ADD YOU CODE HERE

        return model[j][i].isCovered();

    }

    /**
     * returns the number of neighbooring mines os the dot  
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighbooring mines at location (i,j)
     */   
    public int getNeighbooringMines(int i, int j){
        
    // ADD YOU CODE HERE
        return model[j][i].getNeighbooringMines();


    }


    /**
     * Sets the status of the dot at location (i,j) to uncovered
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void uncover(int i, int j){
        
    // ADD YOU CODE HERE
        model[j][i].uncover();


    }

    /**
     * Sets the status of the dot at location (i,j) to clicked
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void click(int i, int j){
        
    // ADD YOU CODE HERE
        model[j][i].click();

    }
     /**
     * Uncover all remaining covered dot
     */   
    public void uncoverAll(){
        
    // ADD YOU CODE HERE
        for (int i=0; i<widthOfGame; i++){
            for(int j=0; j<heigthOfGame; j++){
                if (model[j][i].isCovered()) {

                    model[j][i].uncover();
                    
                }

            }
        }

    }

 

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
        
    // ADD YOU CODE HERE
        return numberOfSteps;

    }

  

    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
      * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */   
    public DotInfo get(int i, int j) {
        
    // ADD YOU CODE HERE
        return model[j][i];

    }


   /**
     * The metod <b>step</b> updates the number of steps. It must be called 
     * once the model has been updated after the payer selected a new square.
     */
     public void step(){
        
    // ADD YOU CODE HERE
            numberOfSteps++;
    }
 
   /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        
    // ADD YOU CODE HERE
        for(int i=0; i<widthOfGame; i++){
            for(int j=0; j<heigthOfGame; j++){
                if (!(model[j][i].isMined()) && model[j][i].isCovered()){
                    return false;
                }
            }
        }
        return true;

    }


   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
        
    // ADD YOU CODE HERE
        String s="";
            for(int i=0; i<heigthOfGame; i++){
                for (int j=0; j<widthOfGame; j++){

                    if (model[i][j].isCovered()){
                        s+="_ ";
                    }

                    else if(!model[i][j].isCovered() && model[i][j].isMined()){
                        s+="* ";
                    }

                    else if (!model[i][j].isCovered() && !model[i][j].isMined()){
                        s=s+model[i][j].getNeighbooringMines()+" ";
                    }
                }
                s+="\n";
            }
            return s;
    }
}
