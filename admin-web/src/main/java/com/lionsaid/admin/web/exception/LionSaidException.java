package com.lionsaid.admin.web.exception;

import lombok.*;

import java.util.Locale;

/**
 * The type Lion dance exception.
 *
 * @author sunwei
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LionSaidException extends Exception {
    private int    code;
    private Locale locale;

    public LionSaidException(String message, int code) {
        super(message);
        this.code = code;
    }

    public LionSaidException(String message, int code, Locale locale) {
        super(message);
        this.code = code;
        this.locale = locale;
    }
}
