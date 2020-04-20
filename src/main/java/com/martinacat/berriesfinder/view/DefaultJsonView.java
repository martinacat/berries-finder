package com.martinacat.berriesfinder.view;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DefaultJsonView {
    private final List<Listing> results;
    private final TotalPrice total;
}
