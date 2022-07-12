package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Student;
import util.CrudUtil;

import java.sql.SQLException;

public class ManageCustomerFormController {
    public TextField txtStudentId;
    public TextField txtStudentName;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtNic;
    public TextField txtSearch;
    public TableView tblStudent;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colNic;

    public void initialize(){

    }


    public void btnSaveStudentOnAction(ActionEvent actionEvent) {
        try {
            CrudUtil.execute("INSERT INTO student VALUES (?,?,?,?,?,?)",txtStudentId.getText(),txtStudentName.getText(),txtEmail.getText(),txtContact.getText(),txtAddress.getText(),txtNic.getText());
            new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something Wrong").show();
        }

    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        Student s = StudentCrudController.getStudentDetail(txtSearch.getText());
        if(s!=null) {
            txtStudentId.setText(s.getStudent_id());
            txtStudentName.setText(s.getStudent_name());
            txtEmail.setText(s.getEmail());
            txtNic.setText(s.getNic());
            txtAddress.setText(s.getAddress());
            txtContact.setText(s.getContact());
        }else {
            new Alert(Alert.AlertType.ERROR,"Empty Result").show();
        }
    }

    public void btnDeleteStudentOnAction(ActionEvent actionEvent) {
    }

    public void btnAddNewStudentOnAction(ActionEvent actionEvent) {
    }
}
