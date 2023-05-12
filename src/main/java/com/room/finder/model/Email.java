package com.room.finder.model;

import com.room.finder.constant.AppConstant;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    String to;
    String from;
    String subject;
    String text;
    String template= AppConstant.EmailTemplatePage;
    Map<String,Object> properties;
}
