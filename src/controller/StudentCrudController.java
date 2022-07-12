package controller;

import model.Student;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentCrudController {
    public static Student getStudentDetail(String id) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM student WHERE student_id=?", id);
            while (resultSet.next()) {
                return new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
        return null;
    }

}
