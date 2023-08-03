package org.perficient.model;

import org.perficient.plugin.annotations.Data;

@Data
public interface Address {
    String street = null;
    Integer number = null;
    String comments = null;
}
