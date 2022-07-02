package org.mustabelmo.plate.file.classes.creator.api;

import java.util.Objects;

public class FieldStructure {
    String name;
    int start;
    int end;
    String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FieldStructure that = (FieldStructure) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
