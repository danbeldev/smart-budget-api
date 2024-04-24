package ru.pgk.smartbudget.features.currency.services.network;

import retrofit2.http.GET;
import ru.pgk.smartbudget.features.currency.dto.cbr.CurrencyCbrResponse;

public interface CurrencyCbrNetwork {

    @GET("/scripts/XML_daily.asp")
    CurrencyCbrResponse getAll();
}
