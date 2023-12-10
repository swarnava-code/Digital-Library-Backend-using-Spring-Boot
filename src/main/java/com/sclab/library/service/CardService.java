package com.sclab.library.service;

import com.sclab.library.entity.Card;
import com.sclab.library.entity.Student;
import com.sclab.library.repository.StudentRepository;
import com.sclab.library.util.CustomMessage;
import com.sclab.library.repository.CardRepository;
import com.sclab.library.util.CustomResponseEntity;
import com.sclab.library.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private StudentRepository studentRepository;

    public ResponseEntity createCard(Card cardRequest, String studentId) {
        var optStudent = studentRepository.findById(studentId);
        boolean isStudentPresent = optStudent.isPresent();
        if (isStudentPresent) {
            Student student = optStudent.get();
            Card existingCard = student.getCard();
            boolean isStudentHaveCard = existingCard != null;
            boolean isStudentCardExpired = false;
            if (isStudentHaveCard) {
                var date = TimeUtil.addYearInDate(0);
                isStudentCardExpired = existingCard.getValidUpto().before(date);
            }
            if (
                    !isStudentHaveCard || isStudentCardExpired
            ) {
                Date date = new Date(System.currentTimeMillis());
                cardRequest.setCreatedOn(date);
                cardRequest.setUpdatedOn(date);
                cardRequest.setValidUpto(TimeUtil.addYearInDate(1));
                cardRequest.setEmail(student.getEmail());
                Card savedCard = cardRepository.save(cardRequest);
                student.setCard(savedCard);
                studentRepository.save(student);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
            } else {
                return CustomResponseEntity.CUSTOM_MSG(HttpStatus.BAD_REQUEST,
                        "isStudentPresent", isStudentPresent,
                        "isStudentHaveCard", isStudentHaveCard,
                        "isStudentCardExpired", isStudentCardExpired,
                        "student", student,
                        "card", existingCard
                );
            }
        }
        return CustomResponseEntity.NOT_FOUND("student not found");
    }

    public ResponseEntity getById(String id) {
        Optional<Card> card = cardRepository.findById(id);
        if (card.isEmpty()) {
            CustomMessage customMessage = new CustomMessage();
            customMessage.setMessage("Not found with this id: " + id);
            customMessage.setStatusCode(404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);
        }
        return ResponseEntity.status(HttpStatus.OK).body(card);
    }

    public ResponseEntity getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(cards);
    }

}