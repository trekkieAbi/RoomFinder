package com.room.finder.constant;


import org.springframework.beans.factory.annotation.Value;


public class AppConstant {
    public final static String AUTHORIZATION_HEADER="Authorization";
    public static final Integer MAX_IMAGE_NO=10;
    public static final Integer MIN_IMAGE_NO=1;

    public static final String[] PUBLIC_URLS = { "/user/login","/user/register","/role/**","/authority/create","/role-authority/create","/v2/api-docs","/swagger-resources/**","/swagger-ui/**","/webjars/**","/authority/readAll"};

    @Value("${project.image}")
    private static String FixedPath;

    public final static String path=FixedPath;
    public final static String[] fileSupportedExtension={"JPEG","jpg","PNG"};
    public final static String EmailTemplatePage="message-email.html";

    public final static String ACCEPTED_SUBJECT="Advertisement has been successfully accepted!!";
    public final static String ACCEPTED_BODY="Congratulations,The advertisement posted by the landlord follow the guidlines of the system and pass the system validation rule," +
            "It is now visible to the public";
    public final static String REJECTED_SUBJECT="Advertisement has been reject!!!";
    public final static String REJECTED_BODY="Sorry to inform that,your advertisement has been reject!!!";
    @Value("${spring.mail.username}")
    private  String from;
    public final static String FROM="trekkieabishek94@gmail.com";

    public static final String SUBJECT_ENQUIRYMAIL="Enquiry about the room from the customer";

}
