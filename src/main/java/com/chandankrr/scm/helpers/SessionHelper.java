package com.chandankrr.scm.helpers;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
@Slf4j
public class SessionHelper {

    public static void removeMessage() {
        try {
            HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest()
                    .getSession();
            session.removeAttribute("message");
        } catch (Exception e) {
            log.info("Error in SessionHelper: ", e);
        }
    }

}
