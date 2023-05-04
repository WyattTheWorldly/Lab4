//model class
public class Student {
    private int studentID;
    private String firstName;
    private String lastName;
    private int finalGrade;

    public Student(int studentID, String firstName, String lastName, int finalGrade) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.finalGrade = finalGrade;
    }

    public Student(){}

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(int finalGrade) {
        this.finalGrade = finalGrade;
    }

    @Override
    public String toString() {
        return studentID +
                ", " + firstName +
                " " + lastName +
                ", "+ finalGrade + "\n";
    }
}
