package org.mustabelmo.validation.examples.entite;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter

public class ValidationExample extends AbstractValidation{

    private String toto, roro;
    private String bar;

    private String foo;
    private String fieldWithNoAccessor;
    
    private Date date;
    
    
    public Date getDate() {
        return date;
    }
    
    public String getToto() {
        return toto;
    }

    public void setToto(String toto) {
        this.toto = toto;
    }

    public String getBar() {
        return bar;
    }

    public String getFoo() {
        return foo;
    }

    public void lol() {
    }
    public String methodWithoutField() {
        return "";
    }

    public String getRoro() {
        return roro;
    }
    public short calculAge() {
        return 0;
    }

    public String[] ageAsArray() {
        return new String[]{};
    }

    private List<String> strings;
    public List<String> getStrings() {
        return strings;
    }
}
