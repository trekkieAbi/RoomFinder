package com.room.finder.controller;

import com.room.finder.constant.ErrorConstant;
import com.room.finder.exception.EmailAlreadyExistsException;
import com.room.finder.exception.ErrorResponse;
import com.room.finder.exception.UserNameAlreadyExistsException;
import com.room.finder.exception.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationErrors(MethodArgumentNotValidException e){
        List<ObjectError> result=e.getBindingResult().getAllErrors();
        Map<String,Object> message=new HashMap<>();
        message.put("status",HttpStatus.BAD_REQUEST);
        message.put("message",result);
        return new ResponseEntity<>(message,new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(UserNotFoundException exception){
        Map<String,Object> message=new HashMap<>();
 ErrorResponse response=new ErrorResponse(HttpStatus.NOT_FOUND.value(),exception.getMessage());
 message.put("Status",response.getStatusCode());
 message.put("message",exception.getMessage());
 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);


    }
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String,Object>> handleGeneralExceptions(Exception ex){
        List<String> errors=Collections.singletonList(ex.getMessage());
        Map<String,Object> message=new HashMap<>();
        message.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        message.put("message",errors);
        return new ResponseEntity<>(message,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public final ResponseEntity<Map<String,Object>> handleRuntimeExceptions(RuntimeException ex){
        List<String> errors=Collections.singletonList(ex.getMessage());
        Map<String,Object> message=new HashMap<>();
        message.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        message.put("message",errors);
        return new ResponseEntity<>(message,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String,Object> getErrorsMap(List<FieldError> errors){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("errors",errors);
        return errorResponse;
    }
    @ExceptionHandler(value = EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEmailAlreadyExistsException(EmailAlreadyExistsException e){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
    }
    @ExceptionHandler(value = UserNameAlreadyExistsException.class)
    public ErrorResponse handleUsernameAlreadyExistsException(UserNameAlreadyExistsException existsException){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), existsException.getMessage());

    }
    @ExceptionHandler(value = {AccessDeniedException.class, AuthenticationException.class})
    public ResponseEntity<Object> handleAccessDeniedException(){
        Map<String,Object> message=new HashMap<>();
        message.put("Status",HttpStatus.FORBIDDEN.value());
        message.put("message", ErrorConstant.ACCESS_DENIED);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<Map<String,Object>> handleJwtExpiredException(ExpiredJwtException expiredJwtException){
        Map<String,Object> message=new HashMap<>();
        message.put("status",HttpStatus.UNAUTHORIZED);
        message.put("message",ErrorConstant.JWT_EXPIRED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
    @ExceptionHandler(value = MalformedJwtException.class)
    public ResponseEntity<Map<String,Object>> handleMalformedJwtTokenException(MalformedJwtException exception){
        Map<String,Object> message=new HashMap<>();
        message.put("status",HttpStatus.UNAUTHORIZED);
        message.put("message",ErrorConstant.MALFORMED_TOKEN);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);

    }

}
