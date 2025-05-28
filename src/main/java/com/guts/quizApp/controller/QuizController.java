package com.guts.quizApp.controller;

import com.guts.quizApp.model.Question;
import com.guts.quizApp.model.QuestionWrapper;
import com.guts.quizApp.model.Quiz;
import com.guts.quizApp.model.UserResponse;
import com.guts.quizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleUnresolved;
import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title) {
        return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizById(@PathVariable Integer id) throws RuntimeException {
        return quizService.getQuizQuestionsById(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitTest(@PathVariable Integer id, @RequestBody List<UserResponse> userResponse) {
        return quizService.calculateResult(id, userResponse);
    }

}
