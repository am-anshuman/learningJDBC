package org.aman.repository;

import org.aman.model.Student;

import java.sql.*;

public class StudentRepository {

    String url = "jdbc:mysql://localhost:3306/student_db";
    String username = "root";
    String password = "123456";

    public void createStudent(Student student) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            String sql = """
                            insert into students (name, email, age)
                            values ('%s', '%s', '%d')
                            """.formatted(student.getName(), student.getEmail(), student.getAge());

            int result = statement.executeUpdate(sql);

            if(result == 1) {
                System.out.println("Create operation successful");
            } else {
                System.out.println("Creation failed");
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }
    }

    public void updateStudent() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            String sql = "update students set age=25 where id=1";

            int result = statement.executeUpdate(sql);

            if(result == 1) {
                System.out.println("Update operation successful");
            } else {
                System.out.println("Update failed");
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }
    }

    public void deleteStudent() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            String sql = "delete from students where id=1";

            int result = statement.executeUpdate(sql);

            if(result == 1) {
                System.out.println("Delete operation successful");
            } else {
                System.out.println("Deletion failed");
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }
    }

    public void getStudentById() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            String sql = "select id, name, email, age from students where id=2";

            ResultSet resultSet = statement.executeQuery(sql);

            resultSet.next();

            Student student = mapRow(resultSet);

            System.out.println(student);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }
    }

    public void completeCRUD() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            String sql = "select id, name, email, age from students where id=2";

            boolean result = statement.execute(sql);

            if(result) {
                ResultSet resultSet = statement.getResultSet();
            } else {
                int rowAffected = statement.getUpdateCount();
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }
    }

    private Student mapRow(ResultSet resultSet) throws SQLException {
        Student student = new Student();

        student.setId(resultSet.getLong("id"));
        student.setName(resultSet.getString("name"));
        student.setEmail(resultSet.getString("email"));
        student.setAge(resultSet.getInt("age"));

        return student;
    }
}
