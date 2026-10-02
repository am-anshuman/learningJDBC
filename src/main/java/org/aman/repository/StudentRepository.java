package org.aman.repository;

import org.aman.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    String url = "jdbc:mysql://localhost:3306/student_db";
    String username = "root";
    String password = "123456";

    public void createStudent(Student student) {

        String sql = """
                    insert into students (name, email, age)
                    values (?, ?, ?)
                    """;

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setInt(3, student.getAge());

            int rowAffected = preparedStatement.executeUpdate();

            if(rowAffected == 1) {
                System.out.println("Create operation successful");
            } else {
                System.out.println("Create operation failed");
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }
    }

    public void updateStudent(Student student, Long id) {

        String sql = """
                    update students
                    set name = ?,
                        email = ?,
                        age = ?
                    where id = ?
                    """;

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setLong(4, id);

            int rowAffected = preparedStatement.executeUpdate();

            if(rowAffected == 1) {
                System.out.println("Update operation successful");
            } else {
                System.out.println("Update failed");
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }
    }

    public void deleteStudent(Long id) {

        String sql = """
                    delete from students where id = ?
                    """;

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setLong(1, id);

            int result = preparedStatement.executeUpdate();

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

    public void getStudentById(Long id) {

        String sql = """
                    select id, name, email, age from students where id=?
                    """;

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setLong(1, id);

            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
                    ) {

                if(resultSet.next()) {
                    Student student = mapRow(resultSet);
                    System.out.println(student);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }
    }

    public void getStudents() {
        String sql = """
                    select id, name, email, age from students
                    """;

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
                    ) {
                List<Student> studentList = new ArrayList<>();

                while(resultSet.next()) {
                    Student student = mapRow(resultSet);
                    studentList.add(student);
                }
            } catch (SQLException e) {

            }
        } catch (Exception e) {
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
