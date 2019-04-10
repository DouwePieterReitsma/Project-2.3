package gameai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gameai.controllers.ConnectionListenerThread;
import gameai.controllers.MainThread;
import gameai.views.Popup;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Main extends Application {
	private BorderPane bPane;
	private GridPane loginPane;
	private AnchorPane bottomPane;

	private VBox titleBox;

	private ExecutorService threadPool;

	private Runnable mainThread;

	private Button loginButton;

	private Label titleLabel;
	private Label ipLabel;
	private Label portLabel;
	private Label nameLabel;
	private Label errorLabel;

	private TextField ipTextArea;
	private TextField portTextArea;
	private TextField nameTextArea;

	private static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception{
    	mainStage = stage;

    	//Make sure everything stops after exit
    	mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
    	    @Override
    	    public void handle(WindowEvent t) {
    	        Platform.exit();
    	        System.out.println("Application sucessfully stopped!");
    	        System.exit(0);
    	    }
    	});

    	//FXML view
        Parent root = FXMLLoader.load(getClass().getResource("views/main.fxml"));

        //Create Threadpool
        threadPool = Executors.newFixedThreadPool(6);

    	//Create border pane
        bPane = new BorderPane();

        //Create gridpane for login info
        loginPane = new GridPane();
        loginPane.setVgap(1);
        loginPane.setHgap(5);
        loginPane.setPadding(new Insets(10, 10, 10, 10));

        //Create button
        loginButton = new Button("Verbinden");
        loginButton.setOnAction((event) -> {
        	try {
				ConnectToServer();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });

        //Create bottompane
        bottomPane = new AnchorPane();
        bottomPane.getChildren().add(loginButton);
        bottomPane.setRightAnchor(loginButton, 10d);
        bottomPane.setBottomAnchor(loginButton, 10d);

        //Create and set titleLabel
        titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-weight: bold");

        //Create and set errorlabel
        errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);

        //Create vbox
        titleBox = new VBox(2);
        titleBox.getChildren().add(titleLabel);
        titleBox.getChildren().add(errorLabel);
        titleBox.setAlignment(Pos.CENTER);

        //Make new loginScene
        Scene loginScene = new Scene(bPane, 375, 200);

        //CreateLoginPane function
        CreateLoginPane();

        //Add to borderpane
        bPane.setTop(titleBox);
        bPane.setCenter(loginPane);
        bPane.setBottom(bottomPane);

        //Set alignment borderpane
        bPane.setAlignment(titleBox, Pos.TOP_CENTER);
        bPane.setAlignment(loginPane, Pos.CENTER);
        bPane.setAlignment(bottomPane, Pos.BOTTOM_RIGHT);

        //Stageset
        mainStage.setTitle("Login");
        mainStage.setScene(loginScene);
        mainStage.setResizable(false);
        mainStage.show();
        Popup.display();
    }

    private void CreateLoginPane() {
    	//Create Labels
        ipLabel = new Label("Hostnaam/IP:");
        portLabel = new Label("Poort:");
        nameLabel = new Label("Gebruikersnaam:");

    	//Create new textareas
        ipTextArea = new TextField("localhost");
    	portTextArea = new TextField("7789");
    	nameTextArea = new TextField("Bram");

    	//Check if logininfo exists
        File tempFile = new File("login.txt");
        boolean exists = tempFile.exists();

        if(exists) {
        	try {
				BufferedReader br = new BufferedReader(new FileReader(tempFile));
				String st = br.readLine();
				String[] loginInfo = st.split(",");

				//Set logininfo
		        nameTextArea.setText(loginInfo[0]);
		        ipTextArea.setText(loginInfo[1]);
		        portTextArea.setText(loginInfo[2]);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    	//Set pref sizes
    	ipTextArea.setPrefHeight(5);
        ipTextArea.setPrefWidth(75);
    	portTextArea.setPrefHeight(5);
        portTextArea.setPrefWidth(75);
        nameTextArea.setPrefHeight(5);
        nameTextArea.setPrefWidth(75);

    	//Add to loginpane
        loginPane.add(nameLabel,0,0);
        loginPane.add(nameTextArea,1,0);
        loginPane.add(ipLabel, 0, 1);
        loginPane.add(ipTextArea, 1, 1);
        loginPane.add(portLabel, 0, 2);
        loginPane.add(portTextArea, 1, 2);

    	 //Set gridpane child alignments
        loginPane.setConstraints(nameLabel, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        loginPane.setConstraints(nameTextArea, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        loginPane.setConstraints(ipLabel, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        loginPane.setConstraints(ipTextArea, 1, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        loginPane.setConstraints(portLabel, 0, 2, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        loginPane.setConstraints(portTextArea, 1, 2, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
    }

    private void ConnectToServer() throws FileNotFoundException {
    	//Check if inputs are correct
    	if(portTextArea.getText().length() > 5) {
    		errorLabel.setText("Poort waarde is te lang.");
    		return;
    	}
    	else if(nameTextArea.getText().length() > 20) {
    		errorLabel.setText("Gebruikersnaam is te lang.");
    		return;
    	}
    	else if(ipTextArea.getText().length() > 20) {
    		errorLabel.setText("Hostnaam/IP is te lang.");
    		return;
    	}
    	else if(!portTextArea.getText().matches("[0-9]+")) {
    		errorLabel.setText("Poort waarde mag alleen nummers bevatten.");
    	}
    	else if(portTextArea.getText().length() == 0 || nameTextArea.getText().length() == 0 || ipTextArea.getText().length() == 0) {
    		errorLabel.setText("Alle vakjes invullen AUB.");
    		return;
    	}

    	//Disable button
    	loginButton.setDisable(true);

    	//Set new mainthread
    	mainThread = new MainThread(threadPool, errorLabel, loginButton, ipTextArea.getText(), Integer.parseInt(portTextArea.getText()), nameTextArea.getText());

    	//Save info
    	PrintWriter out = new PrintWriter("login.txt");
    	out.println(nameTextArea.getText() + "," + ipTextArea.getText() + "," + portTextArea.getText());
    	out.close();

    	//Run mainthread
    	threadPool.execute(mainThread);
    }

    //Stage getter
    public static Stage GetMainStage() {
    	return mainStage;
    }
    public static void runPopup() {
    	Platform.runLater(() -> Popup.display() );
    }

    //Launch application
    public static void main(String[] args) {
        launch(args);
    }

    //Close application
    @Override
	public void stop(){
    	threadPool.shutdown();
		while(!threadPool.isTerminated()) {}
		System.out.println("Application succesfully closed!");
	}
}