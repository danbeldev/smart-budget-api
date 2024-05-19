package ru.pgk.smartbudget.features.currency.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.pgk.smartbudget.common.exceptions.ResourceNotFoundException;
import ru.pgk.smartbudget.features.currency.CurrencyRepository;
import ru.pgk.smartbudget.features.currency.dto.cbr.CurrencyCbrValue;
import ru.pgk.smartbudget.features.currency.entities.CurrencyEntity;
import ru.pgk.smartbudget.features.currency.services.cbr.CurrencyCbrService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceImplTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private CurrencyCbrService currencyCbrService;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @Test
    void getAll_ReturnsListOfCurrencies() {
        // Arrange
        CurrencyEntity currency = new CurrencyEntity();
        currency.setCode("USD");

        when(currencyRepository.findAll()).thenReturn(Collections.singletonList(currency));

        // Act
        List<CurrencyEntity> result = currencyService.getAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("USD", result.get(0).getCode());
    }

    @Test
    void getById_ExistingId_ReturnsCurrencyEntity() {
        // Arrange
        CurrencyEntity currency = new CurrencyEntity();
        currency.setCode("USD");

        when(currencyRepository.findById((short) 1)).thenReturn(java.util.Optional.of(currency));

        // Act
        CurrencyEntity result = currencyService.getById((short) 1);

        // Assert
        assertNotNull(result);
        assertEquals("USD", result.getCode());
    }

    @Test
    void getById_NonExistingId_ThrowsResourceNotFoundException() {
        // Arrange
        when(currencyRepository.findById((short) 1)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> currencyService.getById((short) 1));
    }

    @Test
    void getCourseByCode_ExistingCode_ReturnsCourse() {
        // Arrange
        CurrencyCbrValue currencyCbrValue = new CurrencyCbrValue();
        currencyCbrValue.setValue(1.0);
        when(currencyCbrService.getAll()).thenReturn(Map.of("USD",  currencyCbrValue));

        // Act
        Double result = currencyService.getCourseByCode("USD");

        // Assert
        assertNotNull(result);
        assertEquals(1.0, result);
    }

    @Test
    void getCourseByCode_NonExistingCode_ThrowsResourceNotFoundException() {
        // Arrange
        when(currencyCbrService.getAll()).thenReturn(Collections.emptyMap());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> currencyService.getCourseByCode("XYZ"));
    }
}
