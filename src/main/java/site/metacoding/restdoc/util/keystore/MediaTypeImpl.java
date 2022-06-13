package site.metacoding.restdoc.util.keystore;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;

public class MediaTypeImpl extends MediaType {

    public static final String APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";

    public MediaTypeImpl(MediaType other, Charset charset) {
        super(other, charset);
    }

}
