package org.vaadin.stepbystep.contacts;

import org.vaadin.stepbystep.person.backend.Person;

public class PersonView extends PersonDesign {

    public interface PersonSaveListener {
        void savePerson(Person person);
    }

    public interface PersonDeleteListener {
        void deletePerson(Person person);
    }

    public PersonView(PersonSaveListener saveListener,
            PersonDeleteListener deleteListener) {
        save.addClickListener(evt -> {
            throw new RuntimeException(
                    "Implement me to ensure the bean is updated and then call the save listener");
        });

        cancel.addClickListener(evt -> {
            throw new RuntimeException(
                    "Implement me to reset to the last saved version of the edited person");
        });

        delete.addClickListener(evt -> {
            throw new RuntimeException(
                    "Call the delete listener with the currently edited person");
        });
    }

    public void setPerson(Person selectedRow) {
        /*
         * TODO
         *
         * Ensure all fields (and the picture) are updated
         */
    }

}
