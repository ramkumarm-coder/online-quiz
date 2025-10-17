package com.rk.online_quiz.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {

    private static final String TRACE = "trace";

    public static String servicStackTraceAsString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    public static boolean isTraceOn(WebRequest request) {
        String[] value = request.getParameterValues("trace");
        return Objects.nonNull(value) && value.length > 0 && value[0].contentEquals("true");
    }

    private static String serviceGetBasePath(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        StringBuilder basePath = new StringBuilder();
        basePath.append(scheme).append("://").append(serverName);
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            basePath.append(":").append(serverPort);
        }
        basePath.append(contextPath);

        return basePath.toString();
    }

    private static void printLog(Exception ex) {
        log.error("Exception (" + ex.getClass().getName() + ") : *********************");
        log.error(servicStackTraceAsString(ex));
        log.error("End of the Exception *************************************\n\n");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFoundException(NoResourceFoundException e) {
        return "redirect:/login";
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex, WebRequest webRequest, Model model) {
        HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
        String url = webRequest.getDescription(false).substring(4);

        if (url.contains("/api/")) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            String message = ex.getMessage();
            String basePath = serviceGetBasePath(request);

            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
            problemDetail.setTitle("Server Error");
            problemDetail.setStatus(status.value());
            problemDetail.setType(URI.create(basePath + "/errors/server-error"));
            problemDetail.setInstance(URI.create(request.getRequestURI()));

            printLog(ex);
            return new ResponseEntity<>(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            printLog(ex);
            model.addAttribute("error", ex.getMessage());
            return "error";
        }
    }
}
