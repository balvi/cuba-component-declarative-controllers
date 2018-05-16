package de.balvi.cuba.declarativecontrollers.web.combined

import de.balvi.cuba.declarativecontrollers.web.entitycombined.AnnotatableAbstractCombined
import de.balvi.cuba.declarativecontrollers.web.entitycombined.CombinedAnnotationDispatcher
import spock.lang.Specification

class AnnotatableAbstractCombinedSpec extends Specification {

    AnnotatableAbstractCombined browse
    CombinedAnnotationDispatcher combinedAnnotationExecutorService

    def setup() {

        combinedAnnotationExecutorService = Mock(CombinedAnnotationDispatcher)

        browse = new AnnotatableAbstractCombined(
                combinedAnnotationDispatcher: combinedAnnotationExecutorService
        )
    }

    def "init triggers the execution of the annotations"() {

        when:
        browse.init([:])

        then:
        1 * combinedAnnotationExecutorService.executeInit(browse, [:])
    }

    def "ready triggers the execution of the annotations"() {

        when:
        browse.ready()

        then:
        1 * combinedAnnotationExecutorService.executeReady(browse, _)
    }

    def "ready saves the parameters and passes them to the executorService"() {

        given:
        def params = [my: 'param']
        browse.init(params)

        when:
        browse.ready()

        then:
        1 * combinedAnnotationExecutorService.executeReady(browse, params)
    }


}