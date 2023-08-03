package org.perficient;

import org.perficient.model.Person;
import org.perficient.model.PersonImpl;

public class PersonApp {
    public static void main(String[] args) {

        PersonImpl person1 = PersonImpl.builder()
                .firstName("dsfsfdf")
                .age(18)
                .lastName("sdfgsdfg")
                .build();

    }
}
