package plus.extvos.common.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import plus.extvos.common.utils.RequestContext;

import java.util.Locale;

/**
 * @author shenmc
 */
@Component
public class LocaleMessage {

    @Autowired
    private MessageSource messageSource;

    /**
     * return the resolved the message
     *
     * @param key a exception code
     * @return the resolved message if the lookup was successful, otherwise the default message("")
     */
    public String getMessage(String key) {
        return getMessage(key, key);

    }


    /**
     * @param key        exception code
     * @param defaultMsg the default message
     * @return the resolved message if the lookup was successful,otherwise the default message passed as a parameter
     */
    public String getMessage(String key, String defaultMsg) {
        return messageSource.getMessage(key, null, defaultMsg, getLocale());
    }

    /**
     * @param key        exception code
     * @param args       a default message to return if the lookup fails
     * @param defaultMsg the default message
     * @return the resolved message if the lookup was successful,otherwise the default message passed as a parameter
     */
    public String getMessage(String key, Object[] args, String defaultMsg) {
        return messageSource.getMessage(key, args, defaultMsg, getLocale());
    }

    /**
     * get the locale from HttpRequestUtils
     *
     * @return locale
     */
    public Locale getLocale() {
        Locale locale = RequestContext.probe().getLanguageLocaleByHttpHeader();
//        log.info("The locale[country:{},language:{}]", locale.getCountry(), locale.getLanguage());
        return locale;
    }
}
