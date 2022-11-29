package com.example.org.bean;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table
@Entity
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evaluation_id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column
    @NotNull
    private String responses;

    @Column
    private String evaluated;

    @Column
    private float marks_gained;

    @Column
    private long correct_questions;

    @Column
    private float negative_marks;

    private Evaluation() {

    }

    public String getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(String evaluated) {
        this.evaluated = evaluated;
    }

    public float getMarks_gained() {
        return marks_gained;
    }

    public void setMarks_gained(float marks_gained) {
        this.marks_gained = marks_gained;
    }

    public long getCorrect_questions() {
        return correct_questions;
    }

    public void setCorrect_questions(long correct_questions) {
        this.correct_questions = correct_questions;
    }

    public float getNegative_marks() {
        return negative_marks;
    }

    public void setNegative_marks(float negative_marks) {
        this.negative_marks = negative_marks;
    }

    public static Evaluation factory() {
        return new Evaluation();
    }

    public int getEvaluation_id() {
        return evaluation_id;
    }

    public void setEvaluation_id(int evaluation_id) {
        this.evaluation_id = evaluation_id;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getResponses() {
        return responses;
    }

    public void setResponses(String responses) {
        this.responses = responses;
    }
}
