package org.triadsoft.model;

//@Data
public interface ICustomer {
    Integer id = null;
    String name = null;
    String lastName = null;
    IAddress address = null;
}