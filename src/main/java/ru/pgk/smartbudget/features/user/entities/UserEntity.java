package ru.pgk.smartbudget.features.user.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;

import java.util.Collection;

@Data
@Entity(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String passwordHash;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<TransactionEntity> transactions;
}
