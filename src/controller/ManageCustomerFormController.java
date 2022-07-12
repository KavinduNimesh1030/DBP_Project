package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageCustomerFormController {
    public TextField txtStudentId;
    public TextField txtStudentName;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtNic;
    public TextField txtSearch;
    public TableView<Student> tblStudent;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colNic;
    public Button btnSave;
    public Button btnDelete;
    ObservableList<Student> obList = FXCollections.observableArrayList();


    public void initialize() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        loadAllStudent();

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);
            if (newValue != null) {
                txtStudentId.setText(newValue.getStudent_id());
                txtStudentName.setText(newValue.getStudent_name());
                txtNic.setText(newValue.getNic());
                txtEmail.setText(newValue.getEmail());
                txtContact.setText(newValue.getContact());
                txtAddress.setText(newValue.getAddress());
            }
        });

    }

    private void loadAllStudent() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM student");
            while (resultSet.next()) {
                obList.add(
                        new Student(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6)
                        )

                );
                tblStudent.setItems(obList);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void btnSaveStudentOnAction(ActionEvent actionEvent) {
        if (btnSave.getText().equals("Save")) {
            try {
                CrudUtil.execute("INSERT INTO student VALUES (?,?,?,?,?,?)", txtStudentId.getText(), txtStudentName.getText(), txtEmail.getText(), txtContact.getText(), txtAddress.getText(), txtNic.getText());
                new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
                tblStudent.getItems().clear();
                loadAllStudent();
                clearINT();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something Wrong").show();
            }

        } else {
            try {
                CrudUtil.execute("UPDATE student SET student_name =?,email=?,contact=?,address=?,nic=? WHERE student_id=?", txtStudentName.getText(), txtEmail.getText(), txtContact.getText(), txtAddress.getText(), txtNic.getText(), txtStudentId.getText());
                new Alert(Alert.AlertType.CONFIRMATION, "Updated").show();
                tblStudent.getItems().clear();
                loadAllStudent();
                clearINT();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something Wrong").show();
            }
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM student WHERE student_id=?",txtSearch.getText());
            if (resultSet.next()) {
                btnSave.setText("Update");
                txtStudentId.setText(resultSet.getString(1));
                txtStudentName.setText(resultSet.getString(2));
                txtEmail.setText(resultSet.getString(3));
                txtContact.setText(resultSet.getString(4));
                txtAddress.setText(resultSet.getString(5));
                txtNic.setText(resultSet.getString(6));
            }else {
                new Alert(Alert.AlertType.ERROR, "Empty Result").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void btnDeleteStudentOnAction(ActionEvent actionEvent) {
        try {
            CrudUtil.execute("DELETE FROM student WHERE student_id =?",txtStudentId.getText());
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
            tblStudent.getItems().clear();
            loadAllStudent();
            clearINT();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
    }

    public void btnAddNewStudentOnAction(ActionEvent actionEvent) {
        btnSave.setDisable(false);
        btnSave.setText("Save");
        clearINT();
    }
    public void clearINT(){
        txtStudentId.clear();
        txtStudentName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();
        txtNic.clear();
    }
}
