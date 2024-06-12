/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab6;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ADMIN
 */
public class StudentManager extends Student {

    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<Student> students = new ArrayList<>();

    public static void setStudents(ArrayList<Student> students) {
        StudentManager.students = students;
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public StudentManager() {

    }

    public boolean addStudent(Student stu) {
        for (Student s : students) {
            if (s.getStudentID().equals(stu.getStudentID())) {
                return false;
            }
        }
        return true;
    }

    public boolean DeleteStudent(String studentId) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentID().equals(studentId)) {
                students.remove(i);
            }
        }
        return true;
    }

    public ArrayList<Student> getStudentTableModel() {
        return students;
    }
}
