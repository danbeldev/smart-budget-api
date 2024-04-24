package ru.pgk.smartbudget.features.goal.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.smartbudget.features.user.entities.UserEntity;

import java.time.LocalDate;

@Data
@Entity(name = "goals")
public class GoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double targetAmount;
    private Double currentAmount;

    private LocalDate deadline;
    private Boolean isAchieved = true;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
}
