package ru.pgk.smartbudget.features.currency.services.cbr;

import ru.pgk.smartbudget.features.currency.dto.cbr.CurrencyCbrValue;

import java.util.List;
import java.util.Map;

public interface CurrencyCbrService {

    Map<String, CurrencyCbrValue> getAll();
}
