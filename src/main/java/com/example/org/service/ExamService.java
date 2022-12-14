package com.example.org.service;

import com.example.org.bean.Exam;
import com.example.org.bean.Student;
import com.example.org.dao.ExamsDao;
import com.example.org.util.DateUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExamService {
    ExamsDao dao = new ExamsDao();
    DateUtil dateUtil = new DateUtil();

    public static final Logger logger = Logger.getLogger(ExamService.class.toString());
    private boolean addExam(Exam exam) {
        logger.log(Level.INFO, exam.getExam_id() + " exam added");
        return dao.addExam(exam);
    }

    private List<Exam> getAllExams() {
        logger.log(Level.INFO, "request for all exam");
        return dao.getAllExams();
    }

    public Exam getExamId(int id) {
        return dao.getExamById(id);
    }

    private boolean updateExam(Exam exam){return dao.updateExam(exam);}

    private boolean deleteExam(Exam exam){
        logger.log(Level.INFO, exam.getExam_id() + " exam has deleted");
        return dao.deleteExam(exam);
    }

    public Response getExams(){
        List<Exam> exams = getAllExams();
        return Response.ok().entity(exams).build();
    }

    public Response getExamById(int id) {
        Exam exam = getExamId(id);
        return Response.ok().entity(exam).build();
    }

    public Response uploadExam(String exam_name,
                               String exam_start_date,
                               String exam_end_date) {

        logger.log(Level.INFO, exam_name + " exam has added");

        ExamService service = new ExamService();
        Exam exam = Exam.factory();
        exam.setExam_name(exam_name);
        Date start_date = dateUtil.parse(exam_start_date);
        Date end_date = dateUtil.parse(exam_end_date);

        if(start_date == null || end_date  == null)
            return Response.ok().entity("Error storing exam").build();

        exam.setExam_start_date(exam_start_date);
        exam.setExam_end_date(exam_end_date);

        boolean result = service.addExam(exam);

        String n = "New exam created!";
        if(!result)
            n = "Error creating exam!";
        return Response.ok().entity(n).build();

    }

    public Response deleteExam(int id){

        logger.log(Level.INFO, "Delete exam requested");
        Exam exam = getExamId(id);
        boolean result = deleteExam(exam);
        String n = new String("Exam deleted Successfully.");
        if(!result) {
            n = "Exam cannot be deleted, Please try again later.";
        }

        if (result)
            logger.log(Level.FINEST, "Exam " + id + " has deleted successfully");
        else
            logger.log(Level.SEVERE, "Exam " + id + " deletion operation unsuccessful");
        return Response.ok().entity(n).build();
    }
}



