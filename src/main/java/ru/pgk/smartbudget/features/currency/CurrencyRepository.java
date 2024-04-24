package ru.pgk.smartbudget.features.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pgk.smartbudget.features.currency.entities.CurrencyEntity;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Short> {}
