/*
 * Simple program to connect to a SQL database and read records.  This uses the
 * Company database from the Database Systems book by Elmasri and Navathe and
 * a local instance of Microsoft SQL Server.
 *
 * Written by John Cole at The University of Texas at Dallas using code from
 * Stackoverflow.com for the connection strings.  StarteFd 10/4/2014.
 *
 * Info on connecting to MS SQL Server using sa:
 * https://stackoverflow.com/questions/10815975/sqlexpress-local-database-administrator-password
 *
 * WARNING: Do not consider this an example of how you should write code.  I used
 * inner classes and such because I wanted this example to be all in one module
 * for ease of compilation, but all of the SQL I/O should be in a separate Java
 * file in its own class.
 */

package application;

import java.sql.*;
import java.util.*;
import java.awt.Paint;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main3 extends Application {
private static Connection cnSQL;
private static String strSql;
private static Statement stmtSQL;
private String strConnect = "jdbc:mysql://localhost:3306/library?characterEncoding=utf8";
private String user = "root";
private String password = "anhvaem23";
StackPane root;
GridPane grid;
GridPane gpControls;
public TableView lvBook;

public TextField tfBookID;
public TextField tfBookName;
public TextField tfAuthorID;
public TextField tfPublisherID;
public TextField tfISBN;
/*
public TextField tfEmployeeID;
public TextField tfFname;
public TextField tfLname;
public TextField tfPublisherID;
public TextField tfISBN;
*/
private ObservableList<Book> empList;


    /***************************************************************************
     * This is the start of the program. Set up the screen here and do other
     * initialization.
     ***************************************************************************/
    @Override
    public void start(Stage primaryStage)
    {
      empList = FXCollections.observableArrayList();
      
      tfBookID = new TextField();
      tfBookName = new TextField();
      tfAuthorID = new TextField();
      tfPublisherID = new TextField();
      tfISBN = new TextField();
      root = new StackPane();
      grid = new GridPane();
      lvBook = new TableView();
      TableColumn colID = new TableColumn("Book ID");
      colID.setMinWidth(80);
      TableColumn colBookName = new TableColumn("Book Name");
      colBookName.setMinWidth(150);
      TableColumn colAuthorID = new TableColumn("Author ID");
      colAuthorID.setMinWidth(150);
      TableColumn colISBN = new TableColumn("ISBN");
      lvBook.setMinWidth(450);
      lvBook.getColumns().addAll(colID, colBookName, colAuthorID, colISBN);
      
      // Set up the data entry controls in their own grid.
      gpControls = new GridPane();
      gpControls.add(new Label("Book ID"), 0, 0);
      gpControls.add(tfBookID, 1, 0);
      gpControls.add(new Label("Author ID"), 0, 1);
      gpControls.add(tfAuthorID, 1,1);
      gpControls.add(new Label("Book Name"),0, 2);
      gpControls.add(tfBookName, 1,2);
      gpControls.add(new Label("PublisherID"), 0, 3);
      gpControls.add(tfPublisherID, 1,3);
      gpControls.add(new Label("ISBN"), 0, 4);
      gpControls.add(tfISBN, 1,4);
      Button btnGetISBN = new Button("Get ISBN");
      gpControls.add(btnGetISBN, 0, 5);
      btnGetISBN.setOnAction(new ButtonPressed());
      gpControls.setPadding(new Insets(10, 10 ,15, 10));
      grid.add(gpControls, 0,0);
      grid.add(lvBook, 1, 0);
      
      colID.setCellValueFactory(new PropertyValueFactory("BookID"));
      colBookName.setCellValueFactory(new PropertyValueFactory("BookName"));
      colAuthorID.setCellValueFactory(new PropertyValueFactory("AuthorID"));
      colISBN.setCellValueFactory(new PropertyValueFactory("ISBN"));
      lvBook.getSelectionModel().selectedItemProperty().addListener(new ListListener());
      
      // Get the Book list and give it to the TableView.
      build(empList);
      lvBook.setItems(empList);
      
      
      Scene scene = new Scene(grid, 800, 900);
      
      primaryStage.setTitle("Library");
      primaryStage.setScene(scene);
      primaryStage.show();
    }
    
    /***************************************************************************
     * Open a connection to a Microsoft SQL Server instance that contains the
     * Company database and read the Book table into the list passed as
     * a parameter.
     * @param emps -- List of Books for the TableView.
     ***************************************************************************/
    private void build(ObservableList<Book> emps)
    {
      //??String strConnect = "jdbc:mysql://CS252748\\SQLEXPRESS:1433;databaseName=company;user=sa;password=SQL1";
      String strdata;
      try
      {
      //???Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      //???cnSQL = DriverManager.getConnection(strConnect);

      cnSQL = connectDB();
      stmtSQL = cnSQL.createStatement();
      ResultSet rs = stmtSQL.executeQuery("SELECT * FROM Book");
      while (rs.next())
      {
        strdata = rs.getString("BookName") + " " + rs.getString("AuthorID");
        System.out.println(strdata);
        Book emp = new Book();
        emp.setBookID(rs.getString("ItemNumber"));
        emp.setBookName(rs.getString("BookName"));
        emp.setAuthorID(rs.getString("AuthorID"));
        emp.setPublisherID(rs.getString("PublisherID"));
        emp.setISBN(rs.getDouble("ISBN"));
        emps.add(emp);
      } 
      cnSQL.close();
      }
      catch (Exception ex)
      {
      System.out.println("Error: " + ex.getMessage());
      }
    }
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
 public static void main(String[] args) {
    launch(args);
  }
 
 /******************************************************************************
  * Connect to the database and return a Connection object, or null if it
  * failed.  Yes, that object is an instance variable, but it should not be.
  ******************************************************************************/
private Connection connectDB()
  {
     try
     {
     Class.forName("com.mysql.jdbc.Driver");
     cnSQL=DriverManager.getConnection(strConnect, user, password);
        }
     catch (Exception ex)
     {
         System.out.println(ex.getMessage());
     }
     return cnSQL;
 }    
 
 /******************************************************************************
  * When the "Get ISBN" button is pressed, come here to call a scalar-valued
  * function to retrieve the ISBN using the Book ID from the selected
  * person.
  ******************************************************************************/
 public class ButtonPressed implements EventHandler
 {
    @Override
    public void handle(Event event)
    {
        //???String strConnect = "jdbc:sqlserver://CS252748\\SQLEXPRESS:1433;databaseName=company;user=sa;password=JohnCole1";
        try
        {
        cnSQL = connectDB();
        String strID = tfBookID.getText();
        //???Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //???cnSQL = DriverManager.getConnection(strConnect);
        // Each question mark in the prepared statement below is a positional
        // parameter that is replaced by the two lines of code following
        // it.  Note the syntax of the statement in quotes, with braces, etc.
        CallableStatement cstmt = cnSQL.prepareCall("{? = CALL GetISBN(?)}");
        cstmt.registerOutParameter(1, Types.FLOAT);
        cstmt.setString(2, strID);
        cstmt.execute();
        double dISBN = cstmt.getDouble(1);
        System.out.println(dISBN);
        }
        catch (Exception ex)
        {
          System.out.println(ex.getMessage());
        }
        finally
        {
          try
          {
          cnSQL.close();
          }
          catch (Exception ex)
          {}
        }
    }
 }  // End of ButtonPressed class.
 
 /******************************************************************************
  * This is invoked when the selection in the TableView changes.  I don't like
  * the way this works.  It does not pass a reference to the list, so we have
  * to know which one it was.  With only one list, that is okay.  The newValue
  * parameter is the object that was selected.
  ******************************************************************************/
 public class ListListener implements ChangeListener
 {
    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue)
    {
      Book e = (Book)newValue;
      tfBookID.setText(e.getBookID());
      tfBookName.setText(e.getBookName());
      tfAuthorID.setText(e.getAuthorID());
      // Only here to show how to get this index, if you need it.
      int iRow = lvBook.getSelectionModel().getSelectedIndex();
      //???throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 }  // End of ListListener class.
 
 /******************************************************************************
  * Class representing data from the table in the Company database.  This must
  * be public so the TableView can associate its data elements with the
  * proper columns. 
  ******************************************************************************/
 public class Book
 {
 private final SimpleStringProperty BookID;
 private final SimpleStringProperty BookName;
 private final SimpleStringProperty AuthorID;
 private final SimpleStringProperty PublisherID;
 private final SimpleDoubleProperty ISBN;
 
 // Constructor just allocates the properties.
 public Book()
 {
   BookID = new SimpleStringProperty();
   BookName = new SimpleStringProperty();
   AuthorID = new SimpleStringProperty();
   PublisherID = new SimpleStringProperty();
   ISBN = new SimpleDoubleProperty();
 }
    /**
     * @return the BookID
     */
    public String getBookID()
    {
      return BookID.get();
    }

    public void setBookID(String BookID)
    {
      this.BookID.set(BookID);
    }

    public String getBookName()
    {
      return BookName.get();
    }

    public void setBookName(String BookName)
    {
      this.BookName.set(BookName);
    }

    public String getAuthorID()
    {
      return AuthorID.get();
    }

    public void setAuthorID(String AuthorID)
    {
      this.AuthorID.set(AuthorID);
    }

    public String getPublisherID()
    {
      return PublisherID.get();
    }

    public void setPublisherID(String PublisherID)
    {
      this.PublisherID.set(PublisherID);
    }

    public double getISBN()
    {
      return ISBN.get();
    }

   public void setISBN(double ISBN)
    {
      this.ISBN.set(ISBN);
    }
  }  // End of Book class.
}