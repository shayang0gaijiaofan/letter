package com.jude.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseEntity<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResponseEntity ok(Object data){
        return new ResponseEntity(ResultCode.SUCCESS.getValue(),ResultCode.SUCCESS.getDescription(),data);
    }

    public static ResponseEntity err(Integer code,String message){
        return new ResponseEntity(code,message,null);
    }
}
