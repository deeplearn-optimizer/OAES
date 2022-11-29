package com.example.org.bean;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table
@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int test_id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @Column
    @NotNull
    private String answerKey;

    @Column
    @NotNull
    private float negative_marking;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String date;

    private Test(int test_id, Exam exam, String answerKey, float negative_marking, String name, String date) {
        this.test_id = test_id;
        this.exam = exam;
        this.answerKey = answerKey;
        this.negative_marking = negative_marking;
        this.name= name;
        this.date = date;
    }

    private Test() {

    }
    public static Test factory() {
        return new Test();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }

    public float getNegative_marking() {
        return negative_marking;
    }

    public void setNegative_marking(float negative_marking) {
        this.negative_marking = negative_marking;
    }
}
