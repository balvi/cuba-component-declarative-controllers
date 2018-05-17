package de.balvi.cuba.declarativecontrollers.web.combined

import com.haulmont.cuba.core.global.Messages
import com.haulmont.cuba.core.sys.AppContext
import com.haulmont.cuba.gui.components.FieldGroup
import com.haulmont.cuba.gui.components.ListComponent
import com.haulmont.cuba.gui.components.Table
import com.haulmont.cuba.gui.data.CollectionDatasource
import com.haulmont.cuba.gui.data.Datasource
import de.balvi.cuba.declarativecontrollers.web.entitycombined.AnnotatableAbstractCombined
import de.balvi.cuba.declarativecontrollers.web.entitycombined.CombinedAnnotationDispatcher
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class AnnotatableAbstractCombinedSpec extends Specification {

    AnnotatableAbstractCombined sut
    CombinedAnnotationDispatcher combinedAnnotationExecutorService

    ApplicationContext applicationContext = Mock(ApplicationContext)
    Messages messages = Mock(Messages)

    Table tableMock = Mock(Table)
    CollectionDatasource dsMock = Mock(CollectionDatasource)
    FieldGroup fieldGroupMock = Mock(FieldGroup)
    Datasource editDsMock = Mock(Datasource)

    def setup() {

        combinedAnnotationExecutorService = Mock(CombinedAnnotationDispatcher)

        sut = new AnnotatableAbstractCombinedMock(
                combinedAnnotationDispatcher: combinedAnnotationExecutorService,
                tableMock: tableMock,
                fieldGroupMock: fieldGroupMock
        )

        AppContext.Internals.setApplicationContext(applicationContext)
        applicationContext.getBean(Messages) >> messages

        tableMock.getDatasource() >> dsMock
        fieldGroupMock.getDatasource() >> editDsMock

    }

    def cleanup() {
        AppContext.Internals.setApplicationContext(null)
    }

    def "init triggers the execution of the annotations"() {

        when:
        sut.init([:])

        then:
        1 * combinedAnnotationExecutorService.executeInit(sut, [:])
    }

    def "ready triggers the execution of the annotations"() {

        when:
        sut.ready()

        then:
        1 * combinedAnnotationExecutorService.executeReady(sut, _)
    }

    def "ready saves the parameters and passes them to the executorService"() {

        given:
        def params = [my: 'param']
        sut.init(params)

        when:
        sut.ready()

        then:
        1 * combinedAnnotationExecutorService.executeReady(sut, params)
    }


}

class AnnotatableAbstractCombinedMock extends AnnotatableAbstractCombined {


    Table tableMock
    FieldGroup fieldGroupMock

    @Override
    protected ListComponent getTable() {
        tableMock
    }

    @Override
    protected FieldGroup getFieldGroup() {
        fieldGroupMock
    }

    @Override
    protected void initBrowseCreateAction() {
    }

    @Override
    protected void initBrowseEditAction() {
    }

    @Override
    protected void initBrowseRemoveAction() {
    }

    @Override
    protected void initShortcuts() {
    }

    @Override
    protected void disableEditControls() {
    }

// getter setter
    Table getTableMock() {
        tableMock
    }

    void setTableMock(Table tableMock) {
        this.tableMock = tableMock
    }

    FieldGroup getFieldGroupMock() {
        return fieldGroupMock
    }

    void setFieldGroupMock(FieldGroup fieldGroupMock) {
        this.fieldGroupMock = fieldGroupMock
    }
}