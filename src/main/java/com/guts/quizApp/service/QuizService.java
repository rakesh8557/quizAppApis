package com.guts.quizApp.service;

import com.guts.quizApp.dao.QuestionDao;
import com.guts.quizApp.dao.QuizDao;
import com.guts.quizApp.model.Question;
import com.guts.quizApp.model.QuestionWrapper;
import com.guts.quizApp.model.Quiz;
import com.guts.quizApp.model.UserResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private QuizDao quizDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestionByCategoryAndNumOfQues(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizDao.save(quiz);
        return new ResponseEntity<>("Quiz created", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestionsById(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        if(quiz.isPresent()) {
            List<Question> questionsFromDB = quiz.get().getQuestions();
            List<QuestionWrapper> questionForUser = new ArrayList<>();
            for (Question q : questionsFromDB) {
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionForUser.add(qw);
            }
            return new ResponseEntity<>(questionForUser, HttpStatus.OK);
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Questions not found");
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<UserResponse> userResponse) {
        QuestionService qs = new QuestionService();
        Integer correctAnswer = 0;
        for (UserResponse ur : userResponse) {
            Question q = qs.getQuestionById(ur.getQuestionId());
            if(q.getRightAnswer().equals(ur.getResponse()))
                correctAnswer++;
        }

        return new ResponseEntity<>(correctAnswer, HttpStatus.OK);
    }
}
