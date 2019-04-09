package gameai.views;

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

public class ChallengeView{

    private final TableView<Online> table = new TableView<>();
    private final ObservableList<Online> tvObservableList = FXCollections.observableArrayList();
    private BorderPane paneel;
    private BorderPane parent;
    private String game;
    private Button sub;
    private Button player;
    
    public void createUI(BorderPane parent , String game ,Button sub , Button player) {
    	this.game = game;
    	this.sub = sub;
    	this.player = player;
    	this.parent = parent;
    	
    	sub.setDisable(true);
    	player.setDisable(true);

        setTableappearance();

        fillTableObservableListWithSampleData();
        table.setItems(tvObservableList);

        
        TableColumn<Online, String> colName = new TableColumn<>("Naam");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        

        table.getColumns().addAll(colName);

        addButtonToTable();
        Button terug =  new Button("terug");
        terug.setOnAction(e -> vorige());
        paneel = new BorderPane();
       
        paneel.setCenter(table);
        paneel.setAlignment(terug, Pos.TOP_RIGHT);
        paneel.setBottom(terug);
        paneel.setPrefSize(300, 300);
        parent.setCenter(paneel);
       
        
        
        
        //mainScene = new Scene(paneel);
    }

    private void setTableappearance() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(200);
        table.setPrefHeight(200);
    }

  //  private void fillTableObservableListWithPlayers() {
    
   // }
    private void fillTableObservableListWithSampleData() {

        tvObservableList.addAll(new Online (1, "Speler2", game ),
                                new Online(2, "Speler6", game), 
                                new Online(3, "Speler5", game ), 
                                new Online(4, "Speler3", game ),
                                new Online(5, "Speler6", game ), 
                                new Online(6, "Speler5", game ), 
                                new Online(7, "Speler3", game ),
                                new Online(8, "Speler6", game ), 
                                new Online(9, "Speler5", game ), 
                                new Online(10, "Speler3", game ),
                                new Online(11, "Speler6", game ), 
                                new Online(12, "Speler5", game ), 
                                new Online(13, "Speler3", game ),
                                new Online(14, "Speler4", game ));
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

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Online online = getTableView().getItems().get(getIndex());
                            System.out.println(online);
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
            return "Challenge " + name + " " + game ;
        }

    }
}