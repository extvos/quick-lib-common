package plus.extvos.common;

import com.fasterxml.jackson.core.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import plus.extvos.common.exception.ResultException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.stream.Collectors;


/**
 * @author Mingcai SHEN
 */
@RestControllerAdvice
public class ResultAdvice implements ResponseBodyAdvice<Object> {

    private static final Logger log = LoggerFactory.getLogger(ResultAdvice.class);

//    public ResultAdvice() {
//        log.debug("ResultAdvice::> constructed!");
//    }

    /**
     * Generic Exception process
     * @param request servlet request
     * @param e exception object
     * @param handlerMethod handler method
     * @return Result for response
     */
    @ExceptionHandler(value = {NestedRuntimeException.class, ResultException.class, MethodArgumentNotValidException.class})
    public Result<?> exception(HttpServletRequest request, Exception e, HandlerMethod handlerMethod) {
        log.warn("exception:> {} {} ({}) > {}",
                request.getMethod(), request.getRequestURI(), handlerMethod.getMethod().getName(), e.getMessage());
        if (e instanceof ResultException) {
            return ((ResultException) e).asResult();
        }
        if (e instanceof HttpMessageNotReadableException) {
            return Result.message("Invalid request data format").failure(ResultCode.BAD_REQUEST);
        }
        if (e instanceof JsonParseException) {
            return Result.message("Invalid json data").failure(ResultCode.BAD_REQUEST);
        }
        if (e instanceof ConversionNotSupportedException) {
            return Result.message("Invalid request queries").failure(ResultCode.BAD_REQUEST);
        }
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ee = (MethodArgumentNotValidException) e;
            String errorMsg = ee.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";\n"));
            return Result.message(errorMsg).failure(ResultCode.BAD_REQUEST);
        }
        Result<?> r = Result.message("Unknown internal server error").failure(ResultCode.INTERNAL_SERVER_ERROR);
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        r.setError(writer.toString());
        return r;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug("ResultAdvice::supports:> {} {}", returnType.getMethod().getName(), converterType.getName());
        Method method = returnType.getMethod();
        assert method != null;
        return method.getReturnType() == Result.class;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        log.debug("beforeBodyWrite:> {} {}: {} ({})",
                request.getMethod(), request.getURI(), body != null ? body.getClass().getSimpleName() : "null",
                selectedContentType.toString());
        // Set response status code according the result status code.
        if (body instanceof Result) {
            Result<?> rs = (Result<?>) body;
            log.debug("beforeBodyWrite:> {},{}", (Result<?>) body, ((Result<?>) body).getCode());
            if (rs.getHeaders() != null) {
                rs.getHeaders().forEach((k, v) -> {
                    response.getHeaders().add(k, v);
                });
            }
            if (rs.getCookies() != null) {
                rs.getCookies().forEach((k, v) -> {
                    Cookie ck = new Cookie(k, v);
//                    resp.addCookie(ck);
                });
            }
            int code = ((Result<?>) body).getCode() / 100;
            response.setStatusCode(HttpStatus.valueOf(code));
        }
        return body;
    }

}
