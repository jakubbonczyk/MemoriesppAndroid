package com.example.memoriessb.DTO;

public record StudentDTO(Integer id, String name, String surname) {
    public Integer getId()      { return id; }
    public String  getName()    { return name; }
    public String  getSurname() { return surname; }
}



