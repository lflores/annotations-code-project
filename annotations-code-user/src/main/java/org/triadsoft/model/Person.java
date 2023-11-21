package org.triadsoft.model;

import org.triadsoft.plugin.processors.annotations.Builder;
import org.triadsoft.plugin.processors.annotations.Constructor;
import org.triadsoft.plugin.processors.annotations.Data;

@Data
@Constructor
@Builder
public interface Person {
    Integer age = null;
    String lastName = null;
    String firstName = null;
    Integer identityType = null;
    String identityNumber = null;
    Address address = null;
}
