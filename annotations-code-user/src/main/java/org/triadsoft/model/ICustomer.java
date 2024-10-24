package org.triadsoft.model;

import org.triadsoft.plugin.processors.annotations.Constructor;
import org.triadsoft.plugin.processors.annotations.Data;

@Data
@Constructor
public interface ICustomer {
    Integer id = null;
    String name = null;
    String lastName = null;
    IAddress address = null;
}