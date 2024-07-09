package com.egbas.HNG_stage_two.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResponse<T> {
    private String message;
    private String status;
    private T data;
    private HttpStatus statusCode;


    public ApiResponse(String message, String status, T data, HttpStatus statusCode) {
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.data = data;
    }

    public ApiResponse(String message, String status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }


    public ApiResponse(String message) {
        this.message = message;
    }
}
