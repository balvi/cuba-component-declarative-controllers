package de.balvi.cuba.declarativecontrollers.web;

import java.lang.annotation.Annotation;

public interface AnnotationExecutor<A extends Annotation> {
    boolean supports(Annotation annotation);
}