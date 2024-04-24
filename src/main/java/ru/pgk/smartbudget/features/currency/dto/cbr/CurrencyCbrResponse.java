package ru.pgk.smartbudget.features.currency.dto.cbr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyCbrResponse implements Serializable {

    @JsonProperty("Valute")
    private Map<String, CurrencyCbrValue> valute;
}