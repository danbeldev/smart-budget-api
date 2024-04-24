package ru.pgk.smartbudget.features.currency.services.cbr;

import ru.pgk.smartbudget.features.currency.dto.cbr.CurrencyCbrValue;

import java.util.List;

public interface CurrencyCbrService {

    List<CurrencyCbrValue> getAll();
}
