package ru.pgk.smartbudget.confs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.pgk.smartbudget.features.currency.services.network.CurrencyCbrNetwork;

@Configuration
public class RetrofitConf {

    @Bean
    public Retrofit retrofitCbr() {
        return new Retrofit.Builder()
                .baseUrl("https://www.cbr.ru")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Bean
    public CurrencyCbrNetwork currencyCbrNetwork(Retrofit retrofit) {
        return retrofit.create(CurrencyCbrNetwork.class);
    }
}
