package org.triadsoft.model;

import org.triadsoft.plugin.annotations.Data;

@Data
public interface Customer {
    Integer id = null;
    String name = null;
    String lastName = null;
    Address address = null;
}