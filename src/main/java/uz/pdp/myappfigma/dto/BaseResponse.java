package uz.pdp.myappfigma.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BaseResponse<T>(T result, ErrorData error, boolean success,Integer statusCode) {

    public BaseResponse(T result) {
        this(result, null, true,200);
    }

    public BaseResponse(ErrorData error) {
        this(null, error, false,404);
    }
    public BaseResponse(T result,Integer statusCode){
        this(result,null,true,statusCode);
    }

    public BaseResponse(ErrorData error, Integer statusCode){
        this(null,error,false,statusCode);
    }

    public BaseResponse(ErrorData error, boolean success) {
        this(null,error,success,400);
    }
}
