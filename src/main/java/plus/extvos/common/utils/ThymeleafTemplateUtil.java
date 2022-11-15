package plus.extvos.common.utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import plus.extvos.common.io.Resources;

import java.io.*;
import java.util.Map;

public class ThymeleafTemplateUtil {

    public static class Template {
        private TemplateEngine engin = new TemplateEngine();
        private String tmpl;

        public Template(String t) {
            this.tmpl = t;
        }

        public Template(Reader reader) {
            StringBuilder sb = new StringBuilder();
            char[] bs = new char[1024];
            try {
                int n = 0;
                do {
                    n = reader.read(bs);
                    if (n > 0) {
                        sb.append(bs, 0, n);
                    }
                } while (n > 0);
            } catch (IOException e) {
                this.tmpl = "";
            }
            this.tmpl = sb.toString();
        }

        public Template(InputStream is) {
            this(new InputStreamReader(is));
        }

        public String render(Map<String, Object> params) {
            Context ctx = new Context();
            ctx.setVariables(params);
            return engin.process(tmpl, ctx);
        }

        public void render(Map<String, Object> params, Writer writer) {
            Context ctx = new Context();
            ctx.setVariables(params);
            engin.process(tmpl, ctx, writer);
        }
    }


    public static String render(String template, Map<String, Object> params) {
        return new Template(template).render(params);
    }

    public static void render(String template, Map<String, Object> params, Writer writer) {
        new Template(template).render(params, writer);
    }

    public static Template resource(String file) {
        try {
            return new Template(Resources.getResourceAsReader(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
