package org.acme.resteasyjackson;


public class SomeItem {

    String property;

    public String getProperty() {
        return property;
    }

    @Override
    public String toString() {
        return "SomeItem{" +
                "property='" + property + '\'' +
                '}';
    }

    public void setProperty(String property) {
        this.property = property;

    }
}
