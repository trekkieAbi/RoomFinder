package com.room.finder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Enquiry {
    private Integer id;
    private String enquiryText;
    private Integer roomId;
    private Integer customerId;

}
