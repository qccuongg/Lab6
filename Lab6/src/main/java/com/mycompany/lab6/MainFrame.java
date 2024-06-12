/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab6;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class MainFrame extends JFrame {

    private JButton btnCheckTotalStudent, btnNewStudent, btnRefresh;
    private StudentManager stuManager = new StudentManager();

    public StudentManager getStuManager() {
        return stuManager;
    }

    public void setStuManager(StudentManager stuManager) {
        this.stuManager = stuManager;
    }

    public MainFrame(String title) throws HeadlessException {
        super(title);
        btnCheckTotalStudent = new JButton("Check total student");
        //event
        btnCheckTotalStudent.addActionListener((e) -> {
            JOptionPane.showMessageDialog(null, String.format("There are %d students in the database", stuManager.getStudents().size()), "Message", JOptionPane.INFORMATION_MESSAGE);
        });
        btnNewStudent = new JButton("New student");
        btnNewStudent.addActionListener((e) -> {
            NewStudentDialog dlg = new NewStudentDialog(this, title);
            dlg.setModal(true);
            dlg.setVisible(true);
            if (stuManager.addStudent(dlg.getNewStudent()) == true) {
                JOptionPane.showMessageDialog(null, "Success");
            } else {
                JOptionPane.showMessageDialog(null, "Unsuccess, id exists");
            }
        });
        this.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(btnCheckTotalStudent);
        panel1.add(btnNewStudent);
        this.add(panel1, BorderLayout.NORTH);
        this.setSize(800, 600);
        this.setLocation(200, 200);
        //this.pack();

        btnRefresh = new JButton("Refresh");
        panel1.add(btnRefresh);

        StudentTableModel stm = new StudentTableModel();
        stm.listToJtable();
        stm.setPreferredSize(new Dimension(200, 200));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        panel2.setPreferredSize(new Dimension(200, 200));
        panel2.add(stm);

        this.add(panel2, BorderLayout.CENTER);

        btnRefresh.addActionListener((e) -> {
            stm.loadTable();
        });
    }

    public class StudentTableModel extends JPanel {

        private JTable table;
        private DefaultTableModel model;

        public void listToJtable() {
            setTitle("Student table");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setPreferredSize(new Dimension(400, 300));

            model = new DefaultTableModel();
            table = new JTable(model);

            model.addColumn("Student ID");
            model.addColumn("First Name");
            model.addColumn("Last Name");
            model.addColumn("Gender");
            model.addColumn("School Stage");

            JScrollPane scrollPane = new JScrollPane(table);
            getContentPane().add(scrollPane, BorderLayout.CENTER);

            pack();
            setLocationRelativeTo(null);

            table.getSelectionModel().addListSelectionListener(e -> {
                int selectedRow = table.getSelectedRow();
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this row?",
                        "Delete Row", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    String studentID = (String) model.getValueAt(selectedRow, 0);
                    if (stuManager.DeleteStudent(studentID)) {
                        model.removeRow(selectedRow);
                    }
                }

            });
        }

        public void loadTable() {
            model.setRowCount(0);
            for (Student st : stuManager.getStudents()) {
                Object[] rows = new Object[]{
                    st.getStudentID(), st.getFirstName(), st.getLastName(), st.getGender(), st.getSchoolStage()
                };
                model.addRow(rows);
            }
        }

    }

    public MainFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
    }
}
