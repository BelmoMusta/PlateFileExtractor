package org.mustabelmo.plate.file.classes.creator.api;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Structure {
    private String className;
    private final Set<FieldStructure> fieldStructures = new LinkedHashSet<>();
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean add(FieldStructure fieldStructure) {
        boolean add = fieldStructures.add(fieldStructure);
        if (!add) {
            fieldStructure.setName(fieldStructure.getName() + "_" + atomicInteger.getAndIncrement());
            fieldStructures.add(fieldStructure);
        }
        return add;
    }

    public Set<FieldStructure> getFieldStructures() {
        return fieldStructures;
    }
}
