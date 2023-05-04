import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JFrame {

    //Global fields for the GUI
    private JPanel panelMain;
    private JTextField txtLastName;
    private JTextField txtFinalGrade;
    private JTextField txtFirstName;
    private JTextField txtStudentID;
    private JButton btnAdd;
    private JButton btnRemove;
    private JButton btnUpdate;
    private JButton btnPrint;
    private JTextArea txtDataArea;
    DataAcessService dataAcess = new DataAcessService();
    boolean dataAreaClean = true;

    public GUI() {

        //Creating the GUI settings
        setContentPane(panelMain);
        setTitle("Student DB");
        setSize(500, 580);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Print button Action listener. Has business logic.
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Student> students = dataAcess.selectAllStudents();

                if (dataAreaClean) {
                    for (Student s : students) {
                        txtDataArea.setText(txtDataArea.getText() + s.toString());
                    }
                    dataAreaClean = false;
                } else {
                    txtDataArea.setText("");
                    for (Student s : students) {
                        txtDataArea.setText(txtDataArea.getText() + s.toString());
                    }
                }

            }
        });

        //Remove button Action listener. Has business logic.
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentIdInput = txtStudentID.getText();

                if(!isNumber(studentIdInput)){
                    JOptionPane.showMessageDialog(btnRemove, "Please only enter numbers into the student ID field.");
                } else if (studentIdInput.equals("")) {
                    JOptionPane.showMessageDialog(btnRemove, "Please type something into the Student ID field then try again.");
                } else if (!dataAcess.studentExistsWithId(Integer.parseInt(studentIdInput))) {
                    JOptionPane.showMessageDialog(btnRemove, "There are no students with ID# " + studentIdInput);
                } else {
                    int confirmation = JOptionPane.showConfirmDialog(btnRemove,
                            "Are you sure you want to remove student with ID# " + studentIdInput + " from the database?");
                    //0 -yes, 1 - no, 2 - cancel
                    if (confirmation == 0) {
                        dataAcess.deleteStudentByID(Integer.parseInt(studentIdInput));
                        txtDataArea.setText(txtDataArea.getText() + "\n Changes made, press print to view.");
                    }
                }

            }

        });

        //Add button Action listener. Has business logic.
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentIdInput = txtStudentID.getText();
                String firstNameInput = txtFirstName.getText();
                String lastNameInput = txtLastName.getText();
                String finalGrade = txtFinalGrade.getText();

                if(!isNumber(studentIdInput) || !isNumber(finalGrade)){
                    JOptionPane.showMessageDialog(btnRemove, "Please only enter numbers into the student ID and final grade fields.");
                } else if (studentIdInput.equals("") || firstNameInput.equals("") || lastNameInput.equals("") || finalGrade.equals("")) {
                    JOptionPane.showMessageDialog(btnRemove, "Insufficient data entry. Please make sure all fields are filled out.");
                } else if (dataAcess.studentExistsWithId(Integer.parseInt(studentIdInput))) {
                    JOptionPane.showMessageDialog(btnRemove, "A student already exists with ID# " + studentIdInput);
                } else if (Integer.parseInt(finalGrade) > 100 || Integer.parseInt(finalGrade) < 0) {
                    JOptionPane.showMessageDialog(btnRemove, "Please enter a grade that is within the range of 0-100.");
                } else {
                    dataAcess.insertStudent(new Student(Integer.parseInt(studentIdInput),
                            firstNameInput,
                            lastNameInput,
                            Integer.parseInt(finalGrade)));
                    txtDataArea.setText(txtDataArea.getText() + "\n Changes made, press print to view.");
                }
            }
        });

        //Update button Action listener. Has business logic.
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentIdInput = txtStudentID.getText();
                String firstNameInput = txtFirstName.getText();
                String lastNameInput = txtLastName.getText();
                String finalGrade = txtFinalGrade.getText();

                if(!isNumber(studentIdInput) || !isNumber(finalGrade)){
                    JOptionPane.showMessageDialog(btnRemove, "Please only enter numbers into the student ID and final grade fields.");
                } else if (studentIdInput.equals("") || firstNameInput.equals("") || lastNameInput.equals("") || finalGrade.equals("")) {
                    JOptionPane.showMessageDialog(btnRemove, "Insufficient data entry. Please make sure all fields are filled out.");
                } else if (!dataAcess.studentExistsWithId(Integer.parseInt(studentIdInput))) {
                    JOptionPane.showMessageDialog(btnRemove, "No student exists with ID# " + studentIdInput);
                } else if (Integer.parseInt(finalGrade) > 100 || Integer.parseInt(finalGrade) < 0) {
                    JOptionPane.showMessageDialog(btnRemove, "Please enter a grade that is within the range of 0-100.");
                } else {
                    int studentID = Integer.parseInt(studentIdInput);
                    dataAcess.updateStudent(studentID, new Student(studentID, firstNameInput, lastNameInput, Integer.parseInt(finalGrade)));
                    txtDataArea.setText(txtDataArea.getText() + "\n Changes made, press print to view.");
                }
            }
        });

    }

    //Helper function. returns true if all characters in string are digits.
    private boolean isNumber (String str){
        for (char c: str.toCharArray()) {
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
}
