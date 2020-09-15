package de.balvi.cuba.declarativecontrollers.web.screen

import com.haulmont.cuba.gui.screen.ScreenOptions
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.screen.ScreenAnnotationExecutor
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import spock.lang.Specification

class ScreenAnnotationDispatcherBeanSpec extends Specification {
    ScreenAnnotationDispatcher screenAnnotationExecutorService
    ScreenAnnotationExecutor executor
    ScreenAnnotationExecutor executor2

    def setup() {
        executor = Mock()
        executor2 = Mock()

        screenAnnotationExecutorService = new ScreenAnnotationDispatcherMock([executor, executor2])
    }

    void 'executeInit does nothing if the browser has no annotations'() {

        given:
        AnnotatableScreen screen = Mock()
        ScreenOptions screenOptions = Mock()

        when:
        screenAnnotationExecutorService.executeInit(screen, screenOptions)

        then:
        0 * executor.init(_, _, _)
    }

    void 'executeInit executes init if an Annotation is supported'() {

        given:
        AnnotatableScreen screen = new MyBrowseWithAnnotation()
        ScreenOptions screenOptions = Mock()

        and:
        executor.supports(_ as ToString) >> true

        when:
        screenAnnotationExecutorService.executeInit(screen, screenOptions)

        then:
        1 * executor.init(_, _, _)
        0 * executor2.init(_, _, _)
    }

    void 'executeInit executes init on every AnnotationExecutor that supports the Annotation'() {

        given:
        AnnotatableScreen screen = new MyBrowseWithAnnotation()
        ScreenOptions screenOptions = Mock()

        and:
        executor.supports(_ as ToString) >> true
        executor2.supports(_ as ToString) >> true

        when:
        screenAnnotationExecutorService.executeInit(screen, screenOptions)

        then:
        1 * executor.init(_, _, _)
        1 * executor2.init(_, _, _)
    }

    void 'executeInit does nothing if there is no AnnotationExecutor for this Annotation'() {

        given:
        AnnotatableScreen browse = new MyBrowseWithAnnotation()
        ScreenOptions screenOptions = Mock()

        and:
        executor.supports(_ as EqualsAndHashCode) >> false

        when:
        screenAnnotationExecutorService.executeInit(browse, screenOptions)

        then:
        0 * executor.init(_, _, _)
    }










    void 'executeBeforeShow does nothing if the browser has no annotations'() {

        given:
        AnnotatableScreen screen = Mock()
        ScreenOptions screenOptions = Mock()

        when:
        screenAnnotationExecutorService.executeBeforeShow(screen, screenOptions)

        then:
        0 * executor.beforeShow(_, _, _)
    }

    void 'executeBeforeShow executes init if an Annotation is supported'() {

        given:
        AnnotatableScreen screen = new MyBrowseWithAnnotation()
        ScreenOptions screenOptions = Mock()

        and:
        executor.supports(_ as ToString) >> true

        when:
        screenAnnotationExecutorService.executeBeforeShow(screen, screenOptions)

        then:
        1 * executor.beforeShow(_, _, _)
        0 * executor2.beforeShow(_, _, _)
    }

    void 'executeBeforeShow executes init on every AnnotationExecutor that supports the Annotation'() {

        given:
        AnnotatableScreen screen = new MyBrowseWithAnnotation()
        ScreenOptions screenOptions = Mock()

        and:
        executor.supports(_ as ToString) >> true
        executor2.supports(_ as ToString) >> true

        when:
        screenAnnotationExecutorService.executeBeforeShow(screen, screenOptions)

        then:
        1 * executor.beforeShow(_, _, _)
        1 * executor2.beforeShow(_, _, _)
    }

    void 'executeBeforeShow does nothing if there is no AnnotationExecutor for this Annotation'() {

        given:
        AnnotatableScreen screen = new MyBrowseWithAnnotation()
        ScreenOptions screenOptions = Mock()

        and:
        executor.supports(_ as EqualsAndHashCode) >> false

        when:
        screenAnnotationExecutorService.executeBeforeShow(screen, screenOptions)

        then:
        0 * executor.beforeShow(_, _, _)
    }
}

class ScreenAnnotationDispatcherMock extends ScreenAnnotationDispatcherBean {

    Collection<ScreenAnnotationExecutor> annotationExecutorCollection

    ScreenAnnotationDispatcherMock(Collection<ScreenAnnotationExecutor> annotationExecutorCollection) {
        this.annotationExecutorCollection = annotationExecutorCollection
    }

    @Override
    protected Collection<ScreenAnnotationExecutor> getScreenAnnotationExecutors() {
        return annotationExecutorCollection
    }
}

@ToString
class MyBrowseWithAnnotation extends AnnotatableScreen {
}
