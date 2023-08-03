package org.perficient.model;

import org.perficient.plugin.annotations.Builder;
import org.perficient.plugin.annotations.Constructor;
import org.perficient.plugin.annotations.Data;

@Data
@Constructor
@Builder
public interface Person {
    Integer age = null;
    String lastName = null;
    String firstName = null;
}
