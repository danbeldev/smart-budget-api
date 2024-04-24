package ru.pgk.smartbudget.features.currency.services.cbr;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.pgk.smartbudget.features.currency.dto.cbr.CurrencyCbrValue;
import ru.pgk.smartbudget.features.currency.services.network.CurrencyCbrNetwork;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyCbrServiceImpl implements CurrencyCbrService {

    private final CurrencyCbrNetwork currencyCbrNetwork;

    @Override
    @Cacheable(cacheNames = "CurrencyCbrService::getAll")
    public List<CurrencyCbrValue> getAll() {
        return currencyCbrNetwork.getAll().valute();
    }

    @Scheduled(cron = "0 1 3 * * *")
    @CacheEvict(cacheNames = "CurrencyCbrService::getAll", allEntries = true)
    public void clearCache() {}
}
