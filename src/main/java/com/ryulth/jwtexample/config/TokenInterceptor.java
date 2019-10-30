package com.ryulth.jwtexample.config;

import com.ryulth.jwtexample.security.UnauthorizedException;
import com.ryulth.jwtexample.security.jwt.TokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenInterceptor implements HandlerInterceptor{

    private final TokenProvider tokenProvider;

    public TokenInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        try {
            final String token = request.getHeader("Authorization").split("Bearer ")[1];
            if (token != null) {
                String accessEmail = tokenProvider.getUserEmail(token,true);
                request.getSession().setAttribute("accessEmail", accessEmail);
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            response.sendError(401, "{ \"error\" : \"Authorization Format Invalid," +
                    " Must Be {TokenType} {AccessToken}\" }");
            return false;
        } catch (NullPointerException e) {
            response.sendError(401, "{ \"error\" : \"Need Access Token in Authorization Header\" }");
            return false;
        } catch (UnauthorizedException e) {
            response.sendError(401, "{ \"error\" : \"" + e.getMessage() + "\" }");
            return false;
        }
        response.sendError(401, "{ \"error\" : \"Unauthorized Access Token\" }");
        return false;
    }
}
