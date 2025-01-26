/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package javafxapplicationdatabase;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Surface Laptop 3
 */
public class FXMLDocumentController implements Initializable {
    

    @FXML
    private TableView<student> tvStudent;
    @FXML
    private TableColumn<student, Integer> colId;
    @FXML
    private TableColumn<student, String> colName;
    @FXML
    private TableColumn<student, String> colDept;
    @FXML
    private TableColumn<student, String> colAge;
    @FXML
    private TableColumn<student, String> colEmail;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfDept;
    @FXML
    private TextField tfAge;
    @FXML
    private TextField tfEmail;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
       
        
        if(event.getSource()==btnInsert){
            insert();
            
        }
        else if (event.getSource()==btnDelete){
            delete();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showStudents();
        
        
    } 
    //mysql connection
public Connection getConnection(){
            Connection conn;
            try{
                conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/university","root","");
                System.out.println("db connected");
                return conn;
            }
            catch(Exception e){
                System.out.println("Error"+e.getMessage());
                System.out.println("unsuccessful Connection");
                return null;
            }
        }

        public ObservableList<student> getstudent(){
            ObservableList<student> studentList=FXCollections.observableArrayList();
            Connection conn = getConnection();
            String query = "SELECT * FROM student";
            
            Statement st;
            ResultSet rs=null;
            student student=null;
            
                    
            try{
                st=conn.createStatement();
                rs=st.executeQuery(query);
                student students;
                while(rs.next()){
                    students=new student(rs.getInt("id"),rs.getString("Name"),rs.getString("Department"),rs.getString("Age"),rs.getString("Email"));
                    studentList.add(students);
                }
                
            }catch(Exception e){
                e.printStackTrace();
            }
            return studentList;
        }
        
        public void showStudents(){
            ObservableList <student> list = getstudent();
            colId.setCellValueFactory(new PropertyValueFactory<student,Integer>("ID"));
            colName.setCellValueFactory(new PropertyValueFactory<student,String>("Name"));
            colDept.setCellValueFactory(new PropertyValueFactory<student,String>("Dept"));
            colAge.setCellValueFactory(new PropertyValueFactory<student,String>("Age"));
            colEmail.setCellValueFactory(new PropertyValueFactory<student,String>("Email"));
            tvStudent.setItems(list);
            
        }  
        void executeQuery(String query){
            Connection conn = getConnection();
            Statement st;
            try{
                st = conn.createStatement();
                st.executeUpdate(query);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        
        void insert(){
            String query = "INSERT INTO student VALUES("+tfId.getText()+",'"+tfName.getText()+"','"+tfDept.getText()+"','"+tfAge.getText()+"','"+tfEmail.getText()+"')";
            executeQuery(query);
            showStudents();
        }
        void delete(){
        String query = "DELETE from students where id="+tfId.getText()+"";
        executeQuery(query);
        showStudents();
        }
        
        void update(){
        String query = "UPDATE student SET id="+tfId.getText()+","+tfName.getText()+","+tfDept.getText()+","+tfAge.getText()+","+tfEmail.getText()+" WHERE id="+tfId.getText()+"";
        }
}
   
