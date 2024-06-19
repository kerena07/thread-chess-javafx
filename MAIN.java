
package application;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class LAB4_MAIN extends Application   {

  
  @Override // Override the start method in the Application class
  public void start(Stage s) {
	    
	  //method to execute the thread
	  ExecutorService executor = Executors.newFixedThreadPool(1);
	 
	
		   
	  //loop for solution board
	  //displays 4 different boards
	    for(int x=0; x<4; x++) { 
	    	BorderPane bp =new BorderPane();
	    	bp.setBottom(new Label("Board Number: "+(x+1)));
	    	//calling thread 
	    	executor.execute((new LAB4_ThreadChess(bp)));
	    	stageMaker(bp);
	    	
	    }
	  	
  }

  
  public static void stageMaker(BorderPane bp) {
	  Scene scene = new Scene(bp, 50 * 8, 55 * 8);// size of the board
	  Stage s = new Stage();
	  s.setScene(scene);
	  s.setTitle("Eight queens"); // Set the stage title
	  s.show(); // Display the stage
	  
	  
  }
 
    

  public static void main(String[] args) { //main method 
    launch(args);
  }



}
