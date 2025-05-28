package com.guts.quizApp.controller;

import com.guts.quizApp.model.Question;
import com.guts.quizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> allQuestions = questionService.getAllQuestions();
        if(allQuestions.isEmpty()) {
            return new ResponseEntity<>(allQuestions, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(allQuestions, HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Integer id) {
        return new ResponseEntity<>(questionService.getQuestionById(id), HttpStatus.OK);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category) {
        List<Question> questions =  questionService.getQuestionsByCategory(category);
        if(questions.isEmpty()) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(questions, HttpStatus.OK);
        }
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return new ResponseEntity<>(questionService.addQuestion(question), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public String deleteQuestion(@PathVariable Integer id) {
        return questionService.deleteQuestion(id);
    }
}
