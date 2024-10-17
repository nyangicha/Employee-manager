import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Emloyee {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtSalary;
    private JTextField txtMobile;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtid;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Emloyee");
        frame.setContentPane(new Emloyee().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;

    public void connect(){
        try {
            con = DriverManager.getConnection("Enter your connection url", "username","password");
            System.out.println("Success");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void table_load(){
        try {
            pst = con.prepareStatement("select * from employee");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Emloyee() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String employeename,salary,mobile;
                employeename = txtName.getText();
                salary = txtSalary.getText();
                mobile = txtMobile.getText();

                try {
                    pst = con.prepareStatement("insert into employee(employeename,salary,mobile) values(?,?,?)");
                    pst.setString(1, employeename);
                    pst.setString(2, salary);
                    pst.setString(3,mobile);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record added successfully");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idemployee = txtid.getText();
                    pst = con.prepareStatement("select employeename,salary,mobile from employee where idemployee=?");
                    pst.setString(1,idemployee);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()==true){
                        String employeename = rs.getString(1);
                        String salary = rs.getString(2);
                        String mobile = rs.getString(3);

                        txtName.setText(employeename);
                        txtSalary.setText(salary);
                        txtMobile.setText(mobile);
                    }
                    else {
                        txtName.setText("");
                        txtSalary.setText("");
                        txtMobile.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid employee ID");
                    }
                } catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String idemployee,employeename,salary,mobile;
                employeename = txtName.getText();
                salary = txtSalary.getText();
                mobile = txtMobile.getText();
                idemployee = txtid.getText();

                try {
                    pst = con.prepareStatement("update employee set employeename=?,salary=?,mobile=? where idemployee=?");
                    pst.setString(1,employeename);
                    pst.setString(2,salary);
                    pst.setString(3,mobile);
                    pst.setString(4,idemployee);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record updated successfully");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                }catch(SQLException e1){
                    e1.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idemployee;
                idemployee = txtid.getText();
                try {
                    pst = con.prepareStatement("delete from employee where idemployee=?");

                    pst.setString(1,idemployee);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record deleted!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.setText("");
                }
                catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });
    }
}
