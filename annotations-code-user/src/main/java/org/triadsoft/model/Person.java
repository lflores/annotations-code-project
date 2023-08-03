package org.triadsoft.model;

import org.triadsoft.plugin.annotations.Builder;
import org.triadsoft.plugin.annotations.Constructor;
import org.triadsoft.plugin.annotations.Data;

@Data
@Constructor
@Builder
public interface Person {
    Integer age = null;
    String lastName = null;
    String firstName = null;
    Integer identityType = null;
    String identityNumber = null;

}
