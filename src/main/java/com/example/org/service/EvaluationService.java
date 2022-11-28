package com.example.org.service;

import com.example.org.bean.Evaluation;
import com.example.org.bean.Student;
import com.example.org.bean.Test;
import com.example.org.dao.EvaluationDAO;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvaluationService {

    public static final Logger logger = Logger.getLogger(EvaluationService.class.toString());
    private final EvaluationDAO dao = new EvaluationDAO();
    public boolean addExam(Evaluation exam) {
        return dao.addEvaluation(exam);
    }

    public List<Evaluation> getEvaluations(int student_id, int exam_id) {
        logger.log(Level.INFO, "Evaluation generated to student " + student_id + " for exam " + exam_id);
        return dao.getAllEvaluations(student_id, exam_id);
    }

    private Evaluation getEvaluationById(int id) {
        return dao.getEvaluationById(id);
    }

    private boolean updateEvaluation(Evaluation evaluation){return dao.updateEvaluation(evaluation);}

    private boolean deleteEvaluation(Evaluation evaluation){return dao.deleteEvaluation(evaluation);}


    public String uploadEvaluation(int student_id,
                                     int test_id,
                                     String responses){

        logger.log(Level.INFO, "Response of student " + student_id + " for test " + test_id + " upload");

        Evaluation evaluation = Evaluation.factory();
        evaluation.setResponses(responses);

        TestService testService = new TestService();
        Test test = testService.getTestById(test_id);

        if(test.getAnswerKey().split(",").length
                != responses.split(",").length) {
            return "Responses cannot be saved!";
        }

        StudentService studentService = new StudentService();
        Student student = studentService.getStudentById(student_id);
        evaluation.setStudent(student);

        evaluation.setTest(test);

        boolean result = addExam(evaluation);

        String s = "Responses were saved successfully!";;
        if(!result) {
            s = "Responses cannot be saved!";
        }

        return s;
    }


    public void makeEvaluations() {

        logger.log(Level.INFO, "Make evaluation service invoke");
        List<Evaluation> evaluations = dao.getPendingEvaluations();
        for(Evaluation evaluation : evaluations) {
            float penalty = evaluation.getTest().getNegative_marking();
            String answer_key = evaluation.getTest().getAnswerKey();
            String recorded_responses = evaluation.getResponses();
            String[] answers = answer_key.split(",");
            String[] responses = recorded_responses.split(",");

            long correct = 0;
            for(int i = 0;i<answers.length;i++) {
                if(answers[i].equals(responses[i]))
                    correct++;
            }

            long incorrect = answers.length - correct;
            float negative_marks = incorrect * (penalty);
            float marks_earned = answers.length - negative_marks;


            evaluation.setCorrect_questions(correct);
            evaluation.setNegative_marks(negative_marks);
            evaluation.setMarks_gained(marks_earned);
            evaluation.setEvaluated("true");

            dao.updateEvaluation(evaluation);
        }
    }
}
