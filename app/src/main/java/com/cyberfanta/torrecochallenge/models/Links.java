package com.cyberfanta.torrecochallenge.models;

public class Links {
    private String name;
    private String address;

    public Links(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Links() {
    }

    @Override
    public String toString() {
        return "Links{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
