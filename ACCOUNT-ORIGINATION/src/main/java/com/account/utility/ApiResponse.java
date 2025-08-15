package com.account.utility;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponse <T>{
    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse(int status,String message){
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(String message, T data){
        return new ApiResponse<>(200, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(int staus,String message){
        return new ApiResponse<>(staus, message);
    }

}
