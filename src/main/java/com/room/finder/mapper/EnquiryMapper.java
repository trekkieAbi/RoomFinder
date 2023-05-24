package com.room.finder.mapper;

import com.room.finder.model.Enquiry;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnquiryMapper {
   Integer makeEnquiry(Enquiry enquiry);
}
