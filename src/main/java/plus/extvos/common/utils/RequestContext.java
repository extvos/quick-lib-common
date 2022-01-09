package plus.extvos.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Mingcai SHEN
 */
public class RequestContext {

    private final Logger log = LoggerFactory.getLogger(RequestContext.class);

    private final HttpServletRequest request;

    private static String LANGUAGE_HEADER = "Accept-Language";

    protected RequestContext(HttpServletRequest req) {
        request = req;
    }

    public static RequestContext probe() {
        return new RequestContext(((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest());
    }

    public String getBrowser() {
        return request.getHeader("User-Agent");
    }

    public String getIpAddress() {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0) {
            ip = request.getRemoteAddr();
        }
        if (ip == null) {
            ip = "";
        }
        return ip;
    }

    public String getRequestURI() {
        return request.getRequestURI();
    }

//    public static HttpServletRequest getHttpServletRequest() {
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) getRequestAttributes();
//        return requestAttributes.getRequest();
//    }

    public HttpSession getHttpSession() {
        return request.getSession();
    }


    public Locale getLanguageLocaleByHttpHeader() {
//        HttpServletRequest request = getHttpServletRequest();

        String header = request.getHeader(LANGUAGE_HEADER);
//        log.debug("getLanguageLocaleByHttpHeader:> Header: {}", header);
        if (null != header && !header.isEmpty()) {
            Locale locale = Locale.forLanguageTag(header.replace("_", "-"));
//            log.debug("getLanguageLocaleByHttpHeader:> Locale: {}", locale);
            if (locale != null) {
                return locale;
            }
        }
        return Locale.getDefault();
    }

    public static void setLanguageHeaderName(String languageHeaderName) {
        LANGUAGE_HEADER = languageHeaderName;
    }
}
