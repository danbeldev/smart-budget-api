package ru.pgk.smartbudget.features.currency.services;

import ru.pgk.smartbudget.features.currency.entities.CurrencyEntity;

import java.util.List;

public interface CurrencyService {

    List<CurrencyEntity> getAll();

    CurrencyEntity getById(Short id);

    CurrencyEntity getDefault();

    Double getCourseByCode(String code);
}
