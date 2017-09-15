package de.balvi.cuba.declarativecontrollers.web.browse

import spock.lang.Specification

class AnnotatableAbstractLookupSpec extends Specification {

    AnnotatableAbstractLookup browse
    BrowseAnnotationExecutorService browseAnnotationExecutorService

    def setup() {

        browseAnnotationExecutorService = Mock(BrowseAnnotationExecutorService)

        browse = new AnnotatableAbstractLookup(
                browseAnnotationExecutorService: browseAnnotationExecutorService
        )
    }

    def "init triggers the execution of the annotations"() {

        when:
        browse.init([:])

        then:
        1 * browseAnnotationExecutorService.executeInit(browse, [:])
    }

    def "ready triggers the execution of the annotations"() {

        when:
        browse.ready()

        then:
        1 * browseAnnotationExecutorService.executeReady(browse, _)
    }

    def "ready saves the parameters and passes them to the executorService"() {

        given:
        def params = [my:'param']
        browse.init(params)

        when:
        browse.ready()

        then:
        1 * browseAnnotationExecutorService.executeReady(browse, params)
    }


}