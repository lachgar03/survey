package com.platform.survey.entites;

import com.platform.survey.enums.TypeQuestion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texte;

    @Enumerated(EnumType.STRING)
    private TypeQuestion type;

    @ManyToOne
    private Sondage sondage;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Option> options;

    @OneToMany(mappedBy = "question")
    private List<Reponse> reponses;


    public void ajouterOption(Option option) {
        options.add(option);
        option.setQuestion(this);
    }
    public void ajouterReponse(Reponse reponse) {
        reponses.add(reponse);
        reponse.setQuestion(this);
    }
}
