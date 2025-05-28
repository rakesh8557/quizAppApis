package com.guts.quizApp.service;

import com.guts.quizApp.dao.QuestionDao;
import com.guts.quizApp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        try {
            return questionDao.findAll();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("some error occured", e);
        }
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    public String addQuestion(Question question) {
        questionDao.save(question);
        return "added";
    }

    public String deleteQuestion(Integer id) {
        Question existingQuestion = getQuestionById(id);
        if(existingQuestion != null) {
            questionDao.deleteById(id);
            return "deleted";
        }
        return "not found";
    }

    public Question getQuestionById(Integer id) {
        try {
            Optional<Question> existingQuestion = questionDao.findById(id);
            if(existingQuestion.isPresent())
                return existingQuestion.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Question();
    }
}
