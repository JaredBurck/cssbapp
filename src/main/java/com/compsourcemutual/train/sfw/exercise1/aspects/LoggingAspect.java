package com.compsourcemutual.train.sfw.exercise1.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* com.compsourcemutual.train.sfw.exercise1.controllers.*.*(..))")
    public void controllersPointcut() {}

    @Pointcut("execution(* com.compsourcemutual.train.sfw.exercise1.repositories.*.*(..))")
    public void repositoriesPointcut() {}

//    @Pointcut("execution(* com.compsourcemutual.train.sfw.exercise1.services.*.*(..))")
//    public void servicesPointcut() {}
//
    @AfterReturning("controllersPointcut()"
            + " || repositoriesPointcut()"
//            + " || servicesPointcut()"
    )
    public void endTraceLogging(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Logger logger = LoggerFactory.getLogger(signature.getDeclaringType());
        logger.trace("{}: end", signature.getName());
    }

    @Before("controllersPointcut()"
            + " || repositoriesPointcut()"
//            + " || servicesPointcut()"
    )
    public void startLogging(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Logger logger = LoggerFactory.getLogger(methodSignature.getDeclaringType());
        logger.trace("{}: start", methodSignature.getName());
        if (logger.isDebugEnabled())  {
            List<Parameter> parameters = Arrays.asList(methodSignature.getMethod().getParameters());
            List<Object> arguments = Arrays.asList(joinPoint.getArgs());
            logger.debug("{}: parameters: {}", methodSignature.getName(), convertArgumentsToJsonString(parameters, arguments));
        }
    }

    String convertArgumentsToJsonString(List<Parameter> parameters, List<Object> arguments) {
        String jsonString = "";

        if (parameters.size() > 0) {
            String firstParameter = "";
            for (int i = 0; i < parameters.size(); i++) {
                Parameter parameter = parameters.get(i);
                Object argument = arguments.get(i);
                jsonString = String.format("%s%s: \"%s\": %s", jsonString, firstParameter, parameter.getName(), convertArgumentToJson(argument));
                firstParameter = ", ";
            }
        } else {
            jsonString = "none";
        }

        return jsonString;
    }

    String convertArgumentToJson(Object argument) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(argument);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
/*
 * Author  : isfg
 * Modified: 4/19/2020 2:41 PM
 * Copyright 2020. All Rights Reserved by CompSource Mutual
 */
