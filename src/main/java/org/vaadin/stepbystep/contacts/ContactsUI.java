package org.vaadin.stepbystep.contacts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.stepbystep.person.backend.Person;
import org.vaadin.stepbystep.person.backend.PersonService;

import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.UI;

@CDIUI("")
public class ContactsUI extends UI {

    HorizontalSplitPanel splitter = new HorizontalSplitPanel();
    Grid<Person> grid = new Grid<>();
    PersonView editor = new PersonView(this::savePerson, this::deletePerson);

    @Inject
    PersonService service;

    private void savePerson(Person person) {
        Person newPerson = service.save(person);
        // Ensure new bean is edited in the future
        editor.setPerson(newPerson);

        throw new RuntimeException("Make grid use the new person instance");
    }

    private void deletePerson(Person person) {
        service.delete(person);

        throw new RuntimeException(
                "Make sure grid no longer shows the instance");

        // selectDefault();
    }

    @PostConstruct
    void load() {
        service.loadData();

        /*
         * TODO:
         *
         * Make grid to fetch items from service
         *
         * Configure columns: First name, Last name and Email address
         *
         * Add a listener that runs editor.setPerson with the selected row
         * whenever it changes
         */

        // selectDefault();
    }

    public void selectDefault() {
        grid.select(service.getEntries().get(0));
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        splitter.setSizeFull();
        grid.setSizeFull();
        editor.setSizeFull();

        splitter.setFirstComponent(grid);
        splitter.setSecondComponent(editor);

        setContent(splitter);
    }
}
