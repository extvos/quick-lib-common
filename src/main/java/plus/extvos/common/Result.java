package plus.extvos.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Result 通用RESTful返回数据
 *
 * @author Mingcai SHEN
 */
public class Result<T> implements Serializable {

    /**
     * Result code defines as plus.extvos.common.Code
     */
    private Integer code;

    /**
     * Message output for detail information
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;


    /**
     * Detail error traces when there is a failure.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    /**
     * Data object
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * Total num of records by query
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long total;

    /**
     * Current page num
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long page;

    /**
     * Current pageSize
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long pageSize;

    /**
     * Current record returned in data
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long count;

    @JsonIgnore
    private Map<String, String> headers;

    @JsonIgnore
    private Map<String, String> cookies;


    public static <T> Result<T> data(T t) {
        Result<T> r = new Result<>();
        r.data = t;
        if (t instanceof List) {
            r.count = (long) ((List<?>) t).size();
        }
        return r;
    }

    public static <T> Result<T> message(String m) {
        Result<T> r = new Result<>();
        r.msg = m;
        return r;
    }

    public static <T> Result<T> code(Code code) {
        Result<T> r = new Result<>();
        r.code = code.value();
        return r;
    }

    public Integer getCode() {
        return code;
    }

    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Result() {
        code = ResultCode.OK.value();
    }

    public Long getTotal() {
        return total;
    }

    public Result<T> setTotal(Long total) {
        this.total = total;
        return this;
    }

    public Long getPage() {
        return page;
    }

    public Result<T> setPage(Long page) {
        this.page = page;
        return this;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public Result<T> setPageSize(Long pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public Result<T> setCount(Long count) {
        this.count = count;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public String getError() {
        return error;
    }

    public Result<T> setError(String error) {
        this.error = error;
        return this;
    }

    public Result<T> paged(long total, long page, long pageSize) {
        if (this.data instanceof Collection) {
            this.count = (long) ((Collection<?>) this.data).size();
            this.total = total;
            this.page = page;
            this.pageSize = pageSize;
        }
        return this;
    }

    public Result<T> with(T o) {
        this.data = o;
        return this;
    }

    public Result<T> success() {
        return success(ResultCode.OK);
    }

    public Result<T> header(String key, String value) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap<>();
        }
        this.headers.put(key, value);
        return this;
    }

    public Result<T> cookie(String key, String value) {
        if (this.cookies == null) {
            this.cookies = new LinkedHashMap<>();
        }
        this.cookies.put(key, value);
        return this;
    }

    public Result<T> success(Code c) {
        code = c.value();
        return this;
    }

    public Result<T> failure() {
        return failure(ResultCode.BAD_REQUEST);
    }

    public Result<T> failure(Code c) {
        code = c.value();
        if (msg == null) {
            msg = c.desc();
        }
        return this;
    }

}

