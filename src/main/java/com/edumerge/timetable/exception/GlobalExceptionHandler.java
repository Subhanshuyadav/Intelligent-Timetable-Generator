package com.edumerge.timetable.exception;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(ApiException.class) ResponseEntity<?> handle(ApiException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}
 @ExceptionHandler(Exception.class) ResponseEntity<?> handleAny(Exception e){return ResponseEntity.status(500).body(Map.of("error","Unexpected server error","detail",e.getMessage()==null?"":e.getMessage()));}
}
