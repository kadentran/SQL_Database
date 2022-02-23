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


public class Main2 extends Application {
private static Connection cnSQL;
private static String strSql;
private static Statement stmtSQL;
private String strConnect = "jdbc:mysql://localhost:3306/company?characterEncoding=utf8";
private String user = "root";
private String password = "anhvaem23";
StackPane root;
GridPane grid;
GridPane gpControls;
public TableView lvEmployee;

public TextField tfEmployeeID;
public TextField tfFname;
public TextField tfLname;
public TextField tfAddress;
public TextField tfSalary;

private ObservableList<Employee> empList;


    /***************************************************************************
     * This is the start of the program. Set up the screen here and do other
     * initialization.
     ***************************************************************************/
    @Override
    public void start(Stage primaryStage)
    {
      empList = FXCollections.observableArrayList();
      
      tfEmployeeID = new TextField();
      tfFname = new TextField();
      tfLname = new TextField();
      tfAddress = new TextField();
      tfSalary = new TextField();
      root = new StackPane();
      grid = new GridPane();
      lvEmployee = new TableView();
      TableColumn colID = new TableColumn("Emp. ID");
      colID.setMinWidth(80);
      TableColumn colFname = new TableColumn("First Name");
      colFname.setMinWidth(150);
      TableColumn colLname = new TableColumn("Last Name");
      colLname.setMinWidth(150);
      TableColumn colSalary = new TableColumn("Salary");
      lvEmployee.setMinWidth(450);
      lvEmployee.getColumns().addAll(colID, colFname, colLname, colSalary);
      
      // Set up the data entry controls in their own grid.
      gpControls = new GridPane();
      gpControls.add(new Label("Employee ID"), 0, 0);
      gpControls.add(tfEmployeeID, 1, 0);
      gpControls.add(new Label("Last Name"), 0, 1);
      gpControls.add(tfLname, 1,1);
      gpControls.add(new Label("First Name"),0, 2);
      gpControls.add(tfFname, 1,2);
      gpControls.add(new Label("Address"), 0, 3);
      gpControls.add(tfAddress, 1,3);
      gpControls.add(new Label("Salary"), 0, 4);
      gpControls.add(tfSalary, 1,4);
      Button btnGetSalary = new Button("Get Salary");
      gpControls.add(btnGetSalary, 0, 5);
      btnGetSalary.setOnAction(new ButtonPressed());
      gpControls.setPadding(new Insets(10, 10 ,15, 10));
      grid.add(gpControls, 0,0);
      grid.add(lvEmployee, 1, 0);
      
      colID.setCellValueFactory(new PropertyValueFactory("employeeID"));
      colFname.setCellValueFactory(new PropertyValueFactory("fname"));
      colLname.setCellValueFactory(new PropertyValueFactory("lname"));
      colSalary.setCellValueFactory(new PropertyValueFactory("salary"));
      lvEmployee.getSelectionModel().selectedItemProperty().addListener(new ListListener());
      
      // Get the employee list and give it to the TableView.
      build(empList);
      lvEmployee.setItems(empList);
      
      
      Scene scene = new Scene(grid, 800, 900);
      
      primaryStage.setTitle("SQL Programming Demo");
      primaryStage.setScene(scene);
      primaryStage.show();
    }
    
    /***************************************************************************
     * Open a connection to a Microsoft SQL Server instance that contains the
     * Company database and read the Employee table into the list passed as
     * a parameter.
     * @param emps -- List of employees for the TableView.
     ***************************************************************************/
    private void build(ObservableList<Employee> emps)
    {
      //??String strConnect = "jdbc:mysql://CS252748\\SQLEXPRESS:1433;databaseName=company;user=sa;password=SQL1";
      String strdata;
      try
      {
      //???Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      //???cnSQL = DriverManager.getConnection(strConnect);

      cnSQL = connectDB();
      stmtSQL = cnSQL.createStatement();
      ResultSet rs = stmtSQL.executeQuery("SELECT * FROM Employee");
      while (rs.next())
      {
        strdata = rs.getString("fname") + " " + rs.getString("lname");
        System.out.println(strdata);
        Employee emp = new Employee();
        emp.setEmployeeID(rs.getString("ssn"));
        emp.setFname(rs.getString("fname"));
        emp.setLname(rs.getString("lname"));
        emp.setAddress(rs.getString("address"));
        emp.setSalary(rs.getDouble("salary"));
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
  * When the "Get Salary" button is pressed, come here to call a scalar-valued
  * function to retrieve the salary using the employee ID from the selected
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
        String strID = tfEmployeeID.getText();
        //???Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //???cnSQL = DriverManager.getConnection(strConnect);
        // Each question mark in the prepared statement below is a positional
        // parameter that is replaced by the two lines of code following
        // it.  Note the syntax of the statement in quotes, with braces, etc.
        CallableStatement cstmt = cnSQL.prepareCall("{? = CALL GetSalary(?)}");
        cstmt.registerOutParameter(1, Types.FLOAT);
        cstmt.setString(2, strID);
        cstmt.execute();
        double dSalary = cstmt.getDouble(1);
        System.out.println(dSalary);
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
      Employee e = (Employee)newValue;
      tfEmployeeID.setText(e.getEmployeeID());
      tfFname.setText(e.getFname());
      tfLname.setText(e.getLname());
      // Only here to show how to get this index, if you need it.
      int iRow = lvEmployee.getSelectionModel().getSelectedIndex();
      //???throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 }  // End of ListListener class.
 
 /******************************************************************************
  * Class representing data from the table in the Company database.  This must
  * be public so the TableView can associate its data elements with the
  * proper columns. 
  ******************************************************************************/
 public class Employee
 {
 private final SimpleStringProperty employeeID;
 private final SimpleStringProperty fname;
 private final SimpleStringProperty lname;
 private final SimpleStringProperty address;
 private final SimpleDoubleProperty salary;
 
 // Constructor just allocates the properties.
 public Employee()
 {
   employeeID = new SimpleStringProperty();
   fname = new SimpleStringProperty();
   lname = new SimpleStringProperty();
   address = new SimpleStringProperty();
   salary = new SimpleDoubleProperty();
 }
    /**
     * @return the employeeID
     */
    public String getEmployeeID()
    {
      return employeeID.get();
    }

    public void setEmployeeID(String employeeID)
    {
      this.employeeID.set(employeeID);
    }

    public String getFname()
    {
      return fname.get();
    }

    public void setFname(String fname)
    {
      this.fname.set(fname);
    }

    public String getLname()
    {
      return lname.get();
    }

    public void setLname(String lname)
    {
      this.lname.set(lname);
    }

    public String getAddress()
    {
      return address.get();
    }

    public void setAddress(String address)
    {
      this.address.set(address);
    }

    public double getSalary()
    {
      return salary.get();
    }

   public void setSalary(double salary)
    {
      this.salary.set(salary);
    }
  }  // End of Employee class.
}