package de.balvi.cuba.declarativecontrollers.web.browse

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import spock.lang.Specification

class BrowseAnnotationDispatcherBeanSpec extends Specification {
    BrowseAnnotationDispatcher browseAnnotationExecutorService
    BrowseAnnotationExecutor executor
    BrowseAnnotationExecutor executor2

    def setup() {
        executor = Mock()
        executor2 = Mock()

        browseAnnotationExecutorService = new BrowseAnnotationDispatcherMock([executor, executor2])
    }

    void 'executeInit does nothing if the browser has no annotations'() {

        given:
        AnnotatableAbstractLookup browse = Mock()

        when:
        browseAnnotationExecutorService.executeInit(browse, [:])

        then:
        0 * executor.init(_, _, _)
    }

    void 'executeInit executes init if an Annotation is supported'() {

        given:
        AnnotatableAbstractLookup browse = new MyBrowseWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true

        when:
        browseAnnotationExecutorService.executeInit(browse, [:])

        then:
        1 * executor.init(_, _, _)
        0 * executor2.init(_, _, _)
    }

    void 'executeInit executes init on every AnnotationExecutor that supports the Annotation'() {

        given:
        AnnotatableAbstractLookup browse = new MyBrowseWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true
        executor2.supports(_ as ToString) >> true

        when:
        browseAnnotationExecutorService.executeInit(browse, [:])

        then:
        1 * executor.init(_, _, _)
        1 * executor2.init(_, _, _)
    }

    void 'executeInit does nothing if there is no AnnotationExecutor for this Annotation'() {

        given:
        AnnotatableAbstractLookup browse = new MyBrowseWithAnnotation()

        and:
        executor.supports(_ as EqualsAndHashCode) >> false

        when:
        browseAnnotationExecutorService.executeInit(browse, [:])

        then:
        0 * executor.init(_, _, _)
    }










    void 'executeReady does nothing if the browser has no annotations'() {

        given:
        AnnotatableAbstractLookup browse = Mock()

        when:
        browseAnnotationExecutorService.executeReady(browse, [:])

        then:
        0 * executor.ready(_, _, _)
    }

    void 'executeReady executes init if an Annotation is supported'() {

        given:
        AnnotatableAbstractLookup browse = new MyBrowseWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true

        when:
        browseAnnotationExecutorService.executeReady(browse, [:])

        then:
        1 * executor.ready(_, _, _)
        0 * executor2.ready(_, _, _)
    }

    void 'executeReady executes init on every AnnotationExecutor that supports the Annotation'() {

        given:
        AnnotatableAbstractLookup browse = new MyBrowseWithAnnotation()

        and:
        executor.supports(_ as ToString) >> true
        executor2.supports(_ as ToString) >> true

        when:
        browseAnnotationExecutorService.executeReady(browse, [:])

        then:
        1 * executor.ready(_, _, _)
        1 * executor2.ready(_, _, _)
    }

    void 'executeReady does nothing if there is no AnnotationExecutor for this Annotation'() {

        given:
        AnnotatableAbstractLookup browse = new MyBrowseWithAnnotation()

        and:
        executor.supports(_ as EqualsAndHashCode) >> false

        when:
        browseAnnotationExecutorService.executeReady(browse, [:])

        then:
        0 * executor.ready(_, _, _)
    }
}

class BrowseAnnotationDispatcherMock extends BrowseAnnotationDispatcherBean {

    Collection<BrowseAnnotationExecutor> annotationExecutorCollection

    BrowseAnnotationDispatcherMock(Collection<BrowseAnnotationExecutor> annotationExecutorCollection) {
        this.annotationExecutorCollection = annotationExecutorCollection
    }

    @Override
    protected Collection<BrowseAnnotationExecutor> getBrowseAnnotationExecutors() {
        return annotationExecutorCollection
    }
}

@ToString
class MyBrowseWithAnnotation extends AnnotatableAbstractLookup {
}
