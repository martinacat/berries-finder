package com.martinacat.berriesfinder.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.martinacat.berriesfinder.service.TotalCalculationService;
import com.martinacat.berriesfinder.view.Listing;
import com.martinacat.berriesfinder.view.TotalPrice;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class TotalCalculationServiceImpl implements TotalCalculationService {

    public TotalPrice calculateTotal(@NonNull List<Listing> listings) {
        Double gross = listings.stream()
                .map(Listing::getUnitPrice)
                .reduce(0.0, Double::sum);

        return new TotalPrice(gross, calculateVat(BigDecimal.valueOf(gross), BigDecimal.valueOf(20)));
    }

    private Double calculateVat(BigDecimal gross, BigDecimal vatPercentage) {
        return gross.multiply(vatPercentage)
                .divide(BigDecimal.valueOf(100), RoundingMode.UP)
                .doubleValue();
    }
}
