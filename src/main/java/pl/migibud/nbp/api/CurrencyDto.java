package pl.migibud.nbp.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Singular;

import java.time.LocalDate;
import java.util.List;

@Builder
@JsonDeserialize(builder = CurrencyDto.CurrencyDtoBuilder.class)
public record CurrencyDto(
    @JsonProperty("table") String table,
    @JsonProperty("currency") String currency,
    @JsonProperty("code") String code,
    @JsonProperty("rates") @Singular List<RateDto> rates
) {

    @Builder
    @JsonDeserialize(builder = RateDto.RateDtoBuilder.class)
    public record RateDto(
        @JsonProperty("no") String no,
        @JsonProperty("effectiveDate") LocalDate effectiveDate,
        @JsonProperty("mid") double mid
    ) {
    }
}
