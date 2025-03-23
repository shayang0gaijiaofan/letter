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

    public static ResponseEntity wrongFile(Object data) {
        return new ResponseEntity(ResultCode.MEDIA_TYPE_NOT_SUPPORTED.getValue(),ResultCode.MEDIA_TYPE_NOT_SUPPORTED.getDescription(),data);
    }


}
