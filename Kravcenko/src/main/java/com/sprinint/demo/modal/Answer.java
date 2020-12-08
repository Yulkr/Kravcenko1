package com.sprinint.demo.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="answers")

public class Answer extends AuditModal {

    @Id
    @GeneratedValue(generator = "answer_generator")

    @SequenceGenerator(
            name = "answer_generator",
            sequenceName = "answer_sequence",
            initialValue = 1000

    )
    private Long id;
    private Long getId(){

        return id;
    }
    @Column(columnDefinition = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Question question;

    public String getText(){
        return text;
    }

    public Question getQuestion(){
        return question;
    }

    public void setQuestion(Question question){
        this.question=question;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", question=" + question +
                '}';
    }
}
