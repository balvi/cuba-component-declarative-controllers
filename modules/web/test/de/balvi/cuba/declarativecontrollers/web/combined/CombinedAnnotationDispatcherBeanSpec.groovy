package de.balvi.cuba.declarativecontrollers.web.combined

import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse.BrowseAnnotationExecutor
import de.balvi.cuba.declarativecontrollers.web.entitycombined.AnnotatableAbstractCombined
import de.balvi.cuba.declarativecontrollers.web.entitycombined.CombinedAnnotationDispatcher
import de.balvi.cuba.declarativecontrollers.web.entitycombined.CombinedAnnotationDispatcherBean
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class CombinedAnnotationDispatcherBeanSpec extends Specification {
    CombinedAnnotationDispatcher combinedAnnotationExecutorService
    BrowseAnnotationExecutor executor
    BrowseAnnotationExecutor executor2

    def setup() {
        executor = Mock()
        executor2 = Mock()

        combinedAnnotationExecutorService = new CombinedAnnotationDispatcherMock([executor, executor2])
    }

    void 'executeInit does nothing if the browser has no annotations'() {

        given:
        AnnotatableAbstractCombined browse = Mock()

        when:
        combinedAnnotationExecutorService.executeInit(browse, [:])

        then:
        0 * executor.init(_, _, _)
    }

    void 'executeInit executes init if an Annotation is supported'() {

        given:
        AnnotatableAbstractCombined browse = new MyCombinedWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true

        when:
        combinedAnnotationExecutorService.executeInit(browse, [:])

        then:
        1 * executor.init(_, _, _)
        0 * executor2.init(_, _, _)
    }

    void 'executeInit executes init on every AnnotationExecutor that supports the Annotation'() {

        given:
        AnnotatableAbstractCombined browse = new MyCombinedWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true
        executor2.supports(_ as ToString) >> true

        when:
        combinedAnnotationExecutorService.executeInit(browse, [:])

        then:
        1 * executor.init(_, _, _)
        1 * executor2.init(_, _, _)
    }

    void 'executeInit does nothing if there is no AnnotationExecutor for this Annotation'() {

        given:
        AnnotatableAbstractCombined browse = new MyCombinedWithAnnotation()

        and:
        executor.supports(_ as EqualsAndHashCode) >> false

        when:
        combinedAnnotationExecutorService.executeInit(browse, [:])

        then:
        0 * executor.init(_, _, _)
    }


    void 'executeReady does nothing if the browser has no annotations'() {

        given:
        AnnotatableAbstractCombined browse = Mock()

        when:
        combinedAnnotationExecutorService.executeReady(browse, [:])

        then:
        0 * executor.ready(_, _, _)
    }

    void 'executeReady executes init if an Annotation is supported'() {

        given:
        AnnotatableAbstractCombined browse = new MyCombinedWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true

        when:
        combinedAnnotationExecutorService.executeReady(browse, [:])

        then:
        1 * executor.ready(_, _, _)
        0 * executor2.ready(_, _, _)
    }

    void 'executeReady executes init on every AnnotationExecutor that supports the Annotation'() {

        given:
        AnnotatableAbstractCombined browse = new MyCombinedWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true
        executor2.supports(_ as ToString) >> true

        when:
        combinedAnnotationExecutorService.executeReady(browse, [:])

        then:
        1 * executor.ready(_, _, _)
        1 * executor2.ready(_, _, _)
    }

    void 'executeReady does nothing if there is no AnnotationExecutor for this Annotation'() {

        given:
        AnnotatableAbstractCombined browse = new MyCombinedWithAnnotation()

        and:
        executor.supports(_ as EqualsAndHashCode) >> false

        when:
        combinedAnnotationExecutorService.executeReady(browse, [:])

        then:
        0 * executor.ready(_, _, _)
    }
}

class CombinedAnnotationDispatcherMock extends CombinedAnnotationDispatcherBean {

    Collection<BrowseAnnotationExecutor> annotationExecutorCollection

    CombinedAnnotationDispatcherMock(Collection<BrowseAnnotationExecutor> annotationExecutorCollection) {
        this.annotationExecutorCollection = annotationExecutorCollection
    }

    @Override
    protected Collection<BrowseAnnotationExecutor> getCombinedAnnotationExecutors() {
        return annotationExecutorCollection
    }
}

@ToString
class MyCombinedWithAnnotation extends AnnotatableAbstractCombined {
}
