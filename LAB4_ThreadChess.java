

package application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class LAB4_ThreadChess implements Runnable{
	  public static final int SIZE = 8; // The size of the chess board
	  // queens are placed at (i, queens[i])
	  // -1 indicates that no queen is currently placed in the ith row
	  // Initially, place a queen at (0, 0) in the 0th row
	  
	  private Label lblStatus = new Label();
	  // Display chess board
	  ChessBoard chessBoard = new ChessBoard();
	  int k = 0;// initialize k to zero
	  
	  BorderPane pane;
	  static int row1=-1;// initialize the row to -1 because we are starting the loop at 0
	  
	  public LAB4_ThreadChess(BorderPane pane) {
		  this.pane = pane;
		  row1++;//add the number of rows by 1
		  
	  }
	  
	  int[] queens = {row1, -1, -1, -1, -1, -1, -1, -1}; // row1 is a value that picks changing to position the queen
	  
	 
	  
		@Override
		public void run() {//method run
		    HBox hbox = new HBox();
		    pane.setTop(lblStatus);
		    BorderPane.setAlignment(lblStatus, Pos.CENTER);
		    pane.setCenter(chessBoard);
		    hbox.setAlignment(Pos.CENTER);
		    hbox.setSpacing(10);
		    
		   
		    //create a new thread
		    new Thread(() -> {
		        while (true) {//loop if true
		       	
		           search();// call method search 
		           Platform.runLater(() -> chessBoard.repaint());
		           if (k == -1) { //not completed board 
		             Platform.runLater(() -> lblStatus.setText("No solution found"));
		             
		           }
		           else if (k == 8) { //all 8 rows have a queen 
		             Platform.runLater(() -> lblStatus.setText("A solution found"));
		             
		           }
		           
		           try {
		             Thread.sleep(200);//the speed value of the backtracking the higher the value the slower the backtracking
		           }
		           catch (Exception ex) {//throws exception  
		           }
		         }
		       	  //goes back to the main class where start method is located 
		       }).start();
		    
		  
		}
	  
	  

	  /** Search for a solution */
	  private boolean search() {
	    // k - 1 indicates the number of queens placed so far
	    // We are looking for a position in the kth row to place a queen
	    if (k >= 0 && k < SIZE) {
	      // Find a position to place a queen in the kth row
	      int j = findPosition(k);
	      if (j < 0) {// i is the column
	        queens[k] = -1;
	        k--; // back track to the previous row
	      } else {
	        queens[k] = j;
	        k++;//continue to the next row
	      }
	    }
	    
	    if (k == -1)
	      return false; // No solution
	    else
	      return true; // A solution is found
	  }

	  public int findPosition(int k) {
	    int start = queens[k] + 1; // Search for a new placement

	    for (int j = start; j < SIZE; j++) {
	      if (isValid(k, j))
	        return j; // (k, j) is the place to put the queen now
	    }

	    return -1;
	  }
	  
	  /** Return true if a queen can be placed at (row, column) */
	  public boolean isValid(int row, int column) {
	    for (int i = 1; i <= row; i++)
	      if (queens[row - i] == column // Check column
	        || queens[row - i] == column - i // Check up left diagonal
	        || queens[row - i] == column + i) // Check up right diagonal
	        return false; // There is a conflict
	    return true; // No conflict
	  }

	  class ChessBoard extends Pane {//type of pane used is a chess board
	    private Image queenImage = new Image("C:\\Users\\User\\Desktop\\SEM 3\\CSC3104- ADV\\queen.jpg");     
	      
	    ChessBoard() {
	      setStyle("-fx-border-color: black");
	    }
	    
	    //the width and height of the chess board grid 
	    double w = 400;
	    double h = 400;
	    
	    public void repaint() {
	      getChildren().clear();

	      // Highlight the current row being searched 
	      Rectangle rectangle = new Rectangle(0, k * h / SIZE, 
	          w, h / SIZE);
	      rectangle.setFill(Color.PINK);
	      getChildren().add(rectangle);
	      
	      // Paint the queens
	      for (int i = 0; i < SIZE; i++) {
	        int j = queens[i]; // The position of the queen in row i
	        ImageView imageView = new ImageView(queenImage);
	        imageView.setX(j * w / SIZE);
	        imageView.setY(i * h / SIZE);
	        imageView.setFitWidth(w / SIZE); 
	        imageView.setFitHeight(h / SIZE);
	        getChildren().add(imageView);
	      }

	      // Draw the horizontal and vertical lines
	      for (int i = 1; i < SIZE; i++) {
	        getChildren().addAll(new Line(0, i * h / SIZE, 
	          w, i * h / SIZE), new Line(i * w / SIZE, 0, 
	          i * w / SIZE, h));
	      }  
	    }
	  }


}