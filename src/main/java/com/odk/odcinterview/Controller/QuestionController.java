package com.odk.odcinterview.Controller;

import com.odk.odcinterview.Model.*;
import com.odk.odcinterview.Model.Question;
import com.odk.odcinterview.Payload.NombreQuestionResponse;
import com.odk.odcinterview.Repository.QuestionRepository;
import com.odk.odcinterview.Service.*;
import com.odk.odcinterview.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    EntretienService entretienService;
    @Autowired
    QuestionService questionService;
    @Autowired
    CritereService critereService;
    @Autowired
    NoteService noteService;
    @Autowired
    PostulantService postulantService;
    @Autowired
    private QuestionRepository questionRepository;

    //methode permettant de recuperer une question
    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionInfo(@PathVariable Long id) {
        Question question= questionService.readQuestionByid(id);
        if (question == null) {
            return new ResponseEntity<>("Cette question n existe pas.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(question, HttpStatus.OK);
    }
    //methode permettant d'ajouter une question

    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestBody Question question) {


        if(questionService.existQuestionBynom(question.getQuestionNom())) {
            return new ResponseEntity<>("cette question existe deja.", HttpStatus.BAD_REQUEST);
        }
        questionService.saveQuestion(question);
        return new ResponseEntity<>(question, HttpStatus.CREATED);
    }
    //methode permettant de modifier une Question

    @PutMapping("/update/{idQuestion}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long idQuestion,@RequestBody Question question) {
        if (questionService.readQuestionByid(idQuestion) == null) {
            return new ResponseEntity<>("cette question n existe pas.", HttpStatus.NOT_FOUND);
        }
        Question question1=questionService.updateQuestion(question,idQuestion);
        return new ResponseEntity<>(question1, HttpStatus.OK);
    }
    //methode permettant de supprimer une Question
    @DeleteMapping("/delete/{idQuestion}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long idQuestion) {
        Question question = questionService.readQuestionByid(idQuestion);
        if (question == null) {
            return new ResponseEntity<>("cette question n existe pas.", HttpStatus.NOT_FOUND);
        }
        questionService.deleteQuestion(question);
        return new ResponseEntity<>("Question a ete supprime avec succes", HttpStatus.OK);
    }
    //methode permettant de recuperer tous les Questions
    @GetMapping("/list")
    public ResponseEntity<?> getQuestionList() {
        List<Question> questions = questionService.readQuestions();
        if (questions.isEmpty()){
            return new ResponseEntity<>("Pas encore de Questions.", HttpStatus.OK);
        }
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/critere/{idCritere}")
    public ResponseEntity<?> getQuestionByCritere(@PathVariable Long idCritere){
        Critere critere = critereService.readCritereByid(idCritere);
        if (critere == null){
            return new ResponseEntity<>("Critere non trouvé.", HttpStatus.OK);
        }
        return new ResponseEntity<>(questionRepository.findQuestionByCritere(critere),HttpStatus.OK);
    }
    @GetMapping("/repond/{idPostulant}")
    public ResponseEntity<?> getNombreQuestionRepond(@PathVariable Long idPostulant) {
        Postulant postulant = postulantService.readPostulantByid(idPostulant);
        if (postulant == null) {
            return new ResponseEntity<>("ce postulant n existe pas.", HttpStatus.NOT_FOUND);
        }
        NombreQuestionResponse nombreQuestionResponse = questionService.getNombreQuestionRepond(idPostulant);
        return  new ResponseEntity<>(nombreQuestionResponse,HttpStatus.OK);
    }
    @GetMapping("/entretien/{idEntretien}")
    public ResponseEntity<?> getQuestionByEntretien(@PathVariable Long idEntretien) {
        Entretien entretien = entretienService.readEntretienByid(idEntretien);
        if (entretien == null) {
            return new ResponseEntity<>("cet entretien n existe pas.", HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(questionService.getQuestionByEntretien(idEntretien),HttpStatus.OK);
    }



    }
