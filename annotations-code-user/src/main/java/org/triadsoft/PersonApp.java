package org.triadsoft;

import org.triadsoft.model.AddressImpl;
import org.triadsoft.model.CustomerImpl;
import org.triadsoft.model.PersonImpl;

public class PersonApp {
    public static void main(String[] args) {

        AddressImpl address = new AddressImpl();
        address.setComments("asdfasdfasf");
        address.setNumber(1);

        CustomerImpl customer = new CustomerImpl(1, "Customer 1", "Last Name", address);

        PersonImpl person1 = PersonImpl.builder()
                .firstName("dsfsfdf")
                .age(18)
                .lastName("sdfgsdfg")
                .address(address)
                .build();
    }
}
