package controller;

import exceptitions.ResourceNotFoundException;
//import exceptitions.ResourseNotFoundException;
import com.sprinint.demo.modal.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.AnswerRepository;
import repository.QuestionRepository;

import java.util.List;

@RestController
public class AnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/questions/{questionId}/answers")
    public List<Answer> getAnswersByQuestionId(@PathVariable Long questionId){
        return answerRepository.findByQuestionId(questionId);

    }

    @PostMapping("/questions/{questionId}/answers")
    public Answer addAnswer(@PathVariable Long questionId, @Valid @RequestBody Answer answer){
        return questionRepository.findById(questionId)
           .map(question -> {
               answer.setQuestion(question);
               return answerRepository.save(answer);

        }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id" + questionId));


    }

    @PostMapping("/questions/{questionId}/answers/{answerId}")
    public Answer updateAnswer(@PathVariable Long questionId,
                               @PathVariable Long answerId,
                               @Valid @RequestBody Answer answerRequest){
        if(!questionRepository.existsById(questionId)){
            throw new ResourceNotFoundException("Question not found with id" + questionId)
        }
        return answerRepository.findById(answerId)
                .map(answer -> {
                    answer.setText((answerRequest.getText()));
                    return answerRepository.save(answer);
                }).orElseThrow(() -> new ResourceNotFoundException("Answer not find with id" + answerId));

    }
    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId,
                                            @PathVariable Long answerId){
        if(!questionRepository.existsById(questionId)){
            throw new ResourceNotFoundException("Question not found with id" + questionId)
        }
        return answerRepository.findById(answerId)
                .map(answer -> {
                    answerRepository.delete(answer);
                    return  ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id"+ questionId));
    }
/*
<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>1.2.1.RELEASE</version>
            </plugin>
        </plugins>
    </build>

 */
}

