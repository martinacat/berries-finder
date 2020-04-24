package com.martinacat.berriesfinder.service;

import java.util.List;

import com.martinacat.berriesfinder.view.Listing;
import com.martinacat.berriesfinder.view.TotalPrice;

public interface TotalCalculationService {
    TotalPrice calculateTotal(List<Listing> listings);
}
