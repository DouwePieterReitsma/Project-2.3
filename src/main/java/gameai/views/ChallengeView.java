package gameai.views;

import java.util.ArrayList;

import gameai.controllers.ConnectionListenerThread;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.geometry.Pos;
/**
 * This is where you get to see who is only and get to challenge those players.
 * 
 * @author Bram
 *
 */
public class ChallengeView {
    private final TableView<Online> table = new TableView<>();
    private final ObservableList<Online> tvObservableList = FXCollections.observableArrayList();
    private BorderPane paneel;
    private String game;
    private String username;
    private Button sub;
    private ConnectionListenerThread connectThread;
    private ArrayList<String> playerList;
    private int playerCounter;
    private BorderPane parent;
/**
 * creates the ChallengeView 
 * 
 * @param connectThread
 * @param parent
 * @param game
 * @param sub
 * @param username
 * @param playerList
 */
    public void createUI(ConnectionListenerThread connectThread, BorderPane parent , String game ,Button sub , String username, ArrayList<String> playerList) {
    	this.connectThread = connectThread;
    	this.game = game;
    	this.sub = sub;
    	this.playerList = playerList;
    	this.parent = parent;
    	this.username = username;

    	sub.setDisable(true);

        setTableappearance();

        fillTableObservableListWithSampleData();
        table.setItems(tvObservableList);

        TableColumn<Online, String> colName = new TableColumn<>("Naam");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        table.getColumns().addAll(colName);

        addButtonToTable();
        Button terug =  new Button("Terug");
        terug.setOnAction(e -> vorige());
        paneel = new BorderPane();

        paneel.setCenter(table);
        paneel.setAlignment(terug, Pos.TOP_RIGHT);
        paneel.setBottom(terug);
        paneel.setPrefSize(300, 300);
        parent.setCenter(paneel);
    }
/**
 * this method is used to set the appearance of the table
 */
    private void setTableappearance() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(200);
        table.setPrefHeight(200);
    }
/**
 * this method is used to fill the table with data
 */
    private void fillTableObservableListWithSampleData() {
    	playerCounter = 0;
    	for(int i = 0; i < playerList.size(); i++) {
    		if(!playerList.get(i).equals(username)) {
    			playerCounter++;
        		tvObservableList.add(new Online(playerCounter, playerList.get(i), game));
    		}
    	}
    }
    /**
     * this method is used to go back to the mainWindow
     */

    public void vorige() {
    	sub.setDisable(false);
    	parent.setCenter(null);
    }
    /**
     *  this method is used to add the button Uitdagen in the second Column
     */
    private void addButtonToTable() {
        TableColumn<Online, Void> colBtn = new TableColumn("Uitdagen");

        Callback<TableColumn<Online, Void>, TableCell<Online, Void>> cellFactory = new Callback<TableColumn<Online, Void>, TableCell<Online, Void>>() {
            @Override
            public TableCell<Online, Void> call(final TableColumn<Online, Void> param) {
                final TableCell<Online, Void> cell = new TableCell<Online, Void>() {

                    private final Button btn = new Button("Uitdagen");
                    //Check if player is player
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Online online = getTableView().getItems().get(getIndex());

                            try {
								connectThread.sendCommand(online.toString());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);

    }
    /**
     * this is used to display the players that are online
     * @author Bram
     *
     */
    public class Online {

        private int id;
        private String name;
        private String game;
        /**
         * This creates the object with the players info
         * @param id
         * @param name
         * @param game
         */
        private Online(int id, String name, String game) {
            this.id = id;
            this.name = name;
            this.game = game;
        }
        /**
         * this method is used to get the ID
         * @return int
         */
        public int getId() {
            return id;
        }
        /**
         * This method is used to set hte ID
         * @param ID
         */
        public void setId(int ID) {
            this.id = ID;
        }
        /**
         * this method is used to get the name
         * @return String
         */
        public String getName() {
            return name;
        }
        /**
         * This method is used to set the name
         * @param nme
         */
        public void setName(String nme) {
            this.name = nme;
        }
        /**
         * This method is used to het the game name
         * @return String
         */
        public String getGame() {
        	return game;
        }
        /**
         * this method is used to set the game
         * @param gme
         */
        public void setGame(String gme) {
        	this.game= gme;
        }
        /**
         * this method is used to output a string with the data necessary to challenge the player
         */
        @Override
        public String toString() {
            return "challenge \"" + name + "\" \"" + game + "\"\n" ;
        }

    }
}