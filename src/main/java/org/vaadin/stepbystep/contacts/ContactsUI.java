package org.vaadin.stepbystep.contacts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.stepbystep.person.backend.Person;
import org.vaadin.stepbystep.person.backend.PersonService;

import com.vaadin.cdi.CDIUI;
import com.vaadin.data.provider.DataProvider;
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

        editor.setPerson(newPerson);
        grid.getDataProvider().refreshItem(newPerson);
    }

    private void deletePerson(Person person) {
        service.delete(person);

        /*
         * Would be nice, but not critical, to have a fine-grained way of
         * informing that a single item has been removed.
         */
        grid.getDataProvider().refreshAll();

        selectDefault();
    }

    @PostConstruct
    void load() {
        service.loadData();

        grid.addSelectionListener(evt -> {
            Person selectedPerson = evt.getFirstSelectedItem().orElse(null);
            if (selectedPerson == null) {
                selectDefault();
            } else {
                editor.setPerson(selectedPerson);
            }
        });

        DataProvider<Person, Void> container = DataProvider.fromCallbacks(
                query -> service.getEntries().stream().skip(query.getOffset())
                        .limit(query.getLimit()),
                query -> service.getEntries().size());
        grid.setDataProvider(container);

        grid.addColumn(Person::getFirstName).setCaption("First name");
        grid.addColumn(Person::getLastName).setCaption("Last name");
        grid.addColumn(Person::getEmail).setCaption("Email address");

        selectDefault();
    }

    public void selectDefault() {
        grid.getSelectionModel().select(service.getEntries().get(0));
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