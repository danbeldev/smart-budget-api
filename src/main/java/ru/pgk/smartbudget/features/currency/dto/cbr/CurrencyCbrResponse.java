package ru.pgk.smartbudget.features.currency.dto.cbr;

import java.util.List;

public record CurrencyCbrResponse(
        List<CurrencyCbrValue> valute
) {}
