package de.balvi.cuba.declarativecontrollers.web.annotationexecutor;

import java.lang.annotation.Annotation;

public interface AnnotationExecutor<A extends Annotation> {
    boolean supports(Annotation annotation);
}