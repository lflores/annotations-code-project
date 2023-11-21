package org.triadsoft.model;

import org.triadsoft.plugin.processors.annotations.Data;

@Data
public interface Address {
    String street = null;
    Integer number = null;
    String comments = null;
}
