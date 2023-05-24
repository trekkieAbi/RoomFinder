package com.room.finder.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentRangeSearchDto {
    private Integer startingRent;
    private Integer endingRent;
}
