package cn.yangtengfei.api.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class ServiceUnauthorizedEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(ServiceUnauthorizedEntryPoint.class);
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException arg2) throws IOException, ServletException {
        // return 401 UNAUTHORIZED status code if the user is not authenticated
        logger.debug(" *** UnauthorizedEntryPoint.commence: " + request.getRequestURI());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}