package org.triadsoft.model;

import org.triadsoft.plugin.processors.annotations.Data;

@Data
public interface IAddress {
    String street = null;
    Integer number = null;
    String comments = null;
}
