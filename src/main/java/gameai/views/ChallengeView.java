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

public class ChallengeView {
    private final TableView<Online> table = new TableView<>();
    private final ObservableList<Online> tvObservableList = FXCollections.observableArrayList();
    private BorderPane paneel;
    private String game;
    private String username;
    private Button sub;
    private Button player;
    private ConnectionListenerThread connectThread;
    private ArrayList<String> playerList;
    private int playerCounter;
    private BorderPane parent;

    public void createUI(ConnectionListenerThread connectThread, BorderPane parent , String game ,Button sub , Button player, String username, ArrayList<String> playerList) {
    	this.connectThread = connectThread;
    	this.game = game;
    	this.sub = sub;
    	this.player = player;
    	this.playerList = playerList;
    	this.parent = parent;
    	this.username = username;

    	sub.setDisable(true);
    	player.setDisable(true);

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

    public BorderPane getStage() {
    	return paneel;
    }

    private void setTableappearance() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(200);
        table.setPrefHeight(200);
    }

    private void fillTableObservableListWithSampleData() {
    	playerCounter = 0;
    	for(int i = 0; i < playerList.size(); i++) {
    		if(!playerList.get(i).equals(username)) {
    			playerCounter++;
        		tvObservableList.add(new Online(playerCounter, playerList.get(i), game));
    		}
    	}
    }

    public void vorige() {
    	sub.setDisable(false);
    	player.setDisable(false);
    	parent.setCenter(null);
    	System.out.println("terug naar het vorige scherm");
    }

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
                            System.out.println(online);

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
    public class Online {

        private int id;
        private String name;
        private String game;

        private Online(int id, String name, String game) {
            this.id = id;
            this.name = name;
            this.game = game;
        }

        public int getId() {
            return id;
        }

        public void setId(int ID) {
            this.id = ID;
        }

        public String getName() {
            return name;
        }

        public void setName(String nme) {
            this.name = nme;
        }
        public String getGame() {
        	return game;
        }
        public void setGame(String gme) {
        	this.game= gme;
        }

        @Override
        public String toString() {
            return "challenge \"" + name + "\" \"" + game + "\"\n" ;
        }

    }
}