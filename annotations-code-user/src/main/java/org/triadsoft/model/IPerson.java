package org.triadsoft.model;

//@Data
//@Constructor
//@Builder
public interface IPerson {
    Integer age = null;
    String lastName = null;
    String firstName = null;
    Integer identityType = null;
    String identityNumber = null;
    IAddress address = null;
}