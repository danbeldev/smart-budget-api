package ru.pgk.smartbudget.features.currency.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.currency.CurrencyRepository;
import ru.pgk.smartbudget.features.currency.dto.cbr.CurrencyCbrValue;
import ru.pgk.smartbudget.features.currency.entities.CurrencyEntity;
import ru.pgk.smartbudget.features.currency.services.cbr.CurrencyCbrService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    private final CurrencyCbrService currencyCbrService;

    @Value("${base.currency_code_id}")
    private Short defaultCurrencyId;

    @Override
    @Transactional(readOnly = true)
    public List<CurrencyEntity> getAll() {
        return currencyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CurrencyEntity getById(Short id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public CurrencyEntity getDefault() {
        return currencyRepository.findById(defaultCurrencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found"));
    }

    @Override
    public Double getCourseByCode(String code) {
        Map<String, CurrencyCbrValue> map = currencyCbrService.getAll();
        if(map.containsKey(code))
            return map.get(code).getValue();
        else
            throw new ResourceNotFoundException("Currency course not found");
    }
}
