package com.platform.survey.entites;
import com.platform.survey.enums.TypeQuestion;
import jakarta.persistence.*;
import lombok.Data;

import javax.swing.text.html.Option;
import java.util.List;


@Entity
@Data
public class Question {
    @Id @GeneratedValue
    private Long id;
    private String texte;
    @Enumerated(EnumType.STRING)
    private TypeQuestion type;
    @ManyToOne
    private Sondage sondage;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Option> options;


}
