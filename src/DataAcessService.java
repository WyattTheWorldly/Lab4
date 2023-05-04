import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataAcessService {

    String url = "";
    String user = "";
    String password = "";

    //returns an ArrayList of all the Student objects in the database.
    public ArrayList<Student> selectAllStudents() {

        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM 302_grads";

        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            PreparedStatement selectPS = connection.prepareStatement("SELECT * FROM 302_grades");
            ResultSet resultSet = selectPS.executeQuery();
            while (resultSet.next()) {
                students.add(new Student(resultSet.getInt("studentID"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("finalGrade")));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    //Inserts a new student into the database
    public void insertStudent(Student student){
        String sql = "INSERT INTO 302_grades (studentID, firstName, lastName, finalGrade) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            PreparedStatement insertPS = connection.prepareStatement(sql);
            insertPS.setInt(1, student.getStudentID());
            insertPS.setString(2, student.getFirstName());
            insertPS.setString(3, student.getLastName());
            insertPS.setInt(4, student.getFinalGrade());
            int insertCount = insertPS.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Returns true iff a student with exact id is in database.
    public boolean studentExistsWithId(int studentID) {

        //I wrote this code, and I'm very proud of it!
        List<Student> students = selectAllStudents()
                .stream()
                .filter(s -> s.getStudentID() == studentID)
                .collect(Collectors.toList());

        if (students.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    //Removes a student tuple from the database
    public void deleteStudentByID(int studentID){

        String sql = "DELETE FROM 302_grades WHERE studentID = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            PreparedStatement deletePS = connection.prepareStatement(sql);
            deletePS.setInt(1, studentID);
            int deleteCount = deletePS.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //updates the student with exact id. replaces all information with the Student object parameter.
    public void updateStudent(int studentID, Student student){

        String sql = "UPDATE 302_grades SET studentID = ?, firstName = ?, lastName = ?, finalGrade = ? WHERE studentID = ? ";
        try (Connection connection = DriverManager.getConnection(url, user, password);) {

            PreparedStatement updatePS = connection.prepareStatement(sql);
            updatePS.setInt(1, student.getStudentID());
            updatePS.setString(2, student.getFirstName());
            updatePS.setString(3, student.getLastName());
            updatePS.setInt(4, student.getFinalGrade());
            updatePS.setInt(5, studentID);
            int updateCount = updatePS.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
