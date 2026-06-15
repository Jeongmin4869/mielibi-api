package com.mielibi.mielibi.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;

    public static <T> ApiResponse<T> success(T data){
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .message(null)
                .build();
    }

    public static <T> ApiResponse<T> success(){
        return ApiResponse.<T>builder()
                .success(true)
                .data(null)
                .message(null)
                .build();
    }

    public static <T> ApiResponse<T> fail(String message){
        return ApiResponse.<T>builder()
                .success(false)
                .data(null)
                .message(message)
                .build();
    }
}
