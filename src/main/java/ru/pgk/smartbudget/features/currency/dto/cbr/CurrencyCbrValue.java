package ru.pgk.smartbudget.features.currency.dto.cbr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyCbrValue implements Serializable {

    @JsonProperty("Value")
    private double value;
}
