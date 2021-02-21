package bookshop;

import java.awt.Font;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.layout.*;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class BookShop extends Application {
    private TextField tFieldTitle = new TextField();
    private TextField tFieldAuthor = new TextField();
    private TextField tFieldPYear = new TextField();
    private TextField tFieldPublisher = new TextField();
    private Button previous = new Button("<<Previous");
    private Button next = new Button("Next>>");
    private String driver = "com.mysql.jdbc.Driver";
    @Override
    public void start(Stage primaryStage) {
        Menu file = new Menu("File");
        Menu help = new Menu("Help");
        CheckMenuItem exit = new CheckMenuItem("Exit");
        CheckMenuItem about = new CheckMenuItem("About");
        file.getItems().add(exit);
        help.getItems().add(about);
        
        exit.setOnAction(e ->{
            if(exit.isSelected()){
                primaryStage.close();
            }
        });
        
        about.setOnAction(e ->{
            if(about.isSelected()){
                javax.swing.JOptionPane.showMessageDialog(null, "Programmer Name: Ian Masaga\nDate Created: 15 May 2020");
            }
        });
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(file,help);
        
        Text books = new Text("Books");
        //books.setFont(new Font.Font("Verdana",FontWeight.BOLD,70));
        
        StackPane bookPane = new StackPane();
        bookPane.getChildren().add(books);
        bookPane.setStyle("-fx-font-style: italic");
        bookPane.setAlignment(Pos.CENTER);
        bookPane.setPadding(new Insets(10,10,10,10));
        
        Label lblTitle = new Label("Title:");
        Label lblAuthor = new Label("Author:");
        Label lblPYear = new Label("Publication Year:");
        Label lblPublisher = new Label("Publisher:");
        
        GridPane info = new GridPane();
        info.setVgap(5);
        info.setHgap(20);
        info.add(lblTitle,0,0);
        info.add(tFieldTitle,1,0);
        info.add(lblAuthor,0,1);
        info.add(tFieldAuthor,1,1);
        info.add(lblPYear,0,2);
        info.add(tFieldPYear,1,2);
        info.add(lblPublisher,0,3);
        info.add(tFieldPublisher,1,3);
        
        HBox buttonsPane = new HBox(120);
        buttonsPane.getChildren().addAll(previous,next);
        
         Connection con;
        
        try{
            Class.forName(driver);
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/BookStore","root","69912101");
            ResultSet results;
            Statement stmt = con.createStatement();
            results = stmt.executeQuery("select title,publisher,year_published,author from books");
            
            primaryStage.setOnShowing(e->{
                try{
                    if(results.first()){
                        tFieldTitle.setText(results.getString("title"));
                        tFieldAuthor.setText(results.getString("author"));
                        tFieldPYear.setText(Integer.toString(results.getInt("year_published")));
                        tFieldPublisher.setText(results.getString("publisher"));
                    }
                }catch(SQLException ax){
                
                }
            });
            
            next.setOnAction(e->{
                try{
                    if(results.next()){
                        tFieldTitle.setText(results.getString("title"));
                        tFieldAuthor.setText(results.getString("author"));
                        tFieldPYear.setText(Integer.toString(results.getInt("year_published")));
                        tFieldPublisher.setText(results.getString("publisher"));
                    }else{
                        results.first();
                        tFieldTitle.setText(results.getString("title"));
                        tFieldAuthor.setText(results.getString("author"));
                        tFieldPYear.setText(Integer.toString(results.getInt("year_published")));
                        tFieldPublisher.setText(results.getString("publisher"));
                    }
                }catch(SQLException ax){
                
                }
            });
            
            previous.setOnAction(e->{
                try{
                    if(results.previous()){
                        tFieldTitle.setText(results.getString("title"));
                        tFieldAuthor.setText(results.getString("author"));
                        tFieldPYear.setText(Integer.toString(results.getInt("year_published")));
                        tFieldPublisher.setText(results.getString("publisher"));
                    }else{
                        results.last();
                        tFieldTitle.setText(results.getString("title"));
                        tFieldAuthor.setText(results.getString("author"));
                        tFieldPYear.setText(Integer.toString(results.getInt("year_published")));
                        tFieldPublisher.setText(results.getString("publisher"));
                    }
                }catch(SQLException ax){
                
                }
            });
        }catch(SQLException e){
        
        }catch(ClassNotFoundException ex){
        
        }
        
        VBox finalLayout = new VBox(30);
        finalLayout.setPadding(new Insets(10,10,10,10));
        finalLayout.getChildren().addAll(bookPane,info,buttonsPane);
         
        BorderPane finalLayout1 = new BorderPane();
        finalLayout1.setTop(menuBar);
        finalLayout1.setBottom(finalLayout);
        Scene scene =new Scene(finalLayout1);
        primaryStage.setScene(scene);
        primaryStage.setTitle("BookShop");
        primaryStage.show();
    }
    
    public void getDataBaseInfo(){
        Connection con;
        
        try{
            Class.forName(driver);
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/BookStore","root","69912101");
            ResultSet results;
            Statement stmt = con.createStatement();
            results = stmt.executeQuery("select title,publisher,year_published,author from books");
            
            if(results.next()){
                tFieldTitle.setText(results.getString("title"));
                tFieldAuthor.setText(results.getString("author"));
                tFieldPYear.setText(Integer.toString(results.getInt("year_published")));
                tFieldPublisher.setText(results.getString("publisher"));
            }
            
        }catch(SQLException e){
        
        }catch(ClassNotFoundException ex){
        
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
