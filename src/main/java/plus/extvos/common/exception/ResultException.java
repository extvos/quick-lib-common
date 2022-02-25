package plus.extvos.common.exception;

import plus.extvos.common.Code;
import plus.extvos.common.ResultCode;
import plus.extvos.common.Result;

/**
 * @author Mingcai SHEN
 */
public class ResultException extends RuntimeException {
    private final Code code;
    private final Object data;

    public ResultException(Code c, String m) {
        super(m);
        code = c;
        data = null;
    }

    public ResultException(Code c, String m, Object d) {
        super(m);
        code = c;
        data = d;
    }

    /**
     * Build a Result&lt;?&gt; object with exception
     *
     * @return information as Result&lt;?&gt;
     */
    public Result<?> asResult() {
        return Result.message(getMessage()).with(data).failure(code);
    }

    /**
     * get error code
     *
     * @return Code
     */
    public Code getCode() {
        return code;
    }


    public static ResultException make(Code c, String m) {
        return new ResultException(c, m);
    }

    public static ResultException make(Code c, String m, Object d) {
        return new ResultException(c, m, d);
    }

    /**
     * build a bad request exception with given message.
     *
     * @param message for hint
     * @return a RestletException
     */
    public static ResultException badRequest(String... message) {
        return new ResultException(ResultCode.BAD_REQUEST, message.length > 0 ? message[0] : ResultCode.BAD_REQUEST.desc());
    }

    /**
     * build a unauthorized exception with given message.
     *
     * @param message for hint
     * @return a RestletException
     */
    public static ResultException unauthorized(String... message) {
        return new ResultException(ResultCode.UNAUTHORIZED, message.length > 0 ? message[0] : ResultCode.UNAUTHORIZED.desc());
    }

    /**
     * build a forbidden exception with given message.
     *
     * @param message for hint
     * @return a RestletException
     */
    public static ResultException forbidden(String... message) {
        return new ResultException(ResultCode.FORBIDDEN, message.length > 0 ? message[0] : ResultCode.FORBIDDEN.desc());
    }

    /**
     * build a not found exception with given message.
     *
     * @param message for hint
     * @return a RestletException
     */
    public static ResultException notFound(String... message) {
        return new ResultException(ResultCode.NOT_FOUND, message.length > 0 ? message[0] : ResultCode.NOT_FOUND.desc());
    }

    /**
     * build a method not allowed exception with given message.
     *
     * @param message for hint
     * @return a RestletException
     */
    public static ResultException methodNotAllowed(String... message) {
        return new ResultException(ResultCode.METHOD_NOT_ALLOWED, message.length > 0 ? message[0] : ResultCode.METHOD_NOT_ALLOWED.desc());
    }

    /**
     * build a conflict exception with given message.
     *
     * @param message for hint
     * @return a RestletException
     */
    public static ResultException conflict(String... message) {
        return new ResultException(ResultCode.CONFLICT, message.length > 0 ? message[0] : ResultCode.CONFLICT.desc());
    }

    /**
     * build a internal server error exception with given message.
     *
     * @param message for hint
     * @return a RestletException
     */
    public static ResultException internalServerError(String... message) {
        return new ResultException(ResultCode.INTERNAL_SERVER_ERROR, message.length > 0 ? message[0] : ResultCode.INTERNAL_SERVER_ERROR.desc());
    }

    /**
     * build a not implemented exception with given message.
     *
     * @param message for hint
     * @return a RestletException
     */
    public static ResultException notImplemented(String... message) {
        return new ResultException(ResultCode.NOT_IMPLEMENTED, message.length > 0 ? message[0] : ResultCode.NOT_IMPLEMENTED.desc());
    }

    /**
     * build a bad gateway exception with given message.
     *
     * @param message for hint
     * @return a RestletException
     */
    public static ResultException badGateway(String... message) {
        return new ResultException(ResultCode.BAD_GATEWAY, message.length > 0 ? message[0] : ResultCode.BAD_GATEWAY.desc());
    }

    /**
     * build a service unavailable exception with given message.
     *
     * @param message for hint
     * @return a RestletException
     */
    public static ResultException serviceUnavailable(String... message) {
        return new ResultException(ResultCode.SERVICE_UNAVAILABLE, message.length > 0 ? message[0] : ResultCode.SERVICE_UNAVAILABLE.desc());
    }

    /**
     * build a gateway timeout exception with given message.
     *
     * @param message for hint
     * @return a RestletException
     */
    public static ResultException gatewayTimeout(String... message) {
        return new ResultException(ResultCode.GATEWAY_TIMEOUT, message.length > 0 ? message[0] : ResultCode.GATEWAY_TIMEOUT.desc());
    }
}
