package ru.pgk.smartbudget.features.currency;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pgk.smartbudget.features.currency.dto.CurrencyDto;
import ru.pgk.smartbudget.features.currency.mappers.CurrencyMapper;
import ru.pgk.smartbudget.features.currency.services.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/currencies")
@RequiredArgsConstructor
@Tag(name = "Currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    private final CurrencyMapper currencyMapper;

    @GetMapping
    private List<CurrencyDto> getAll() {
        return currencyMapper.toDto(currencyService.getAll());
    }
}
