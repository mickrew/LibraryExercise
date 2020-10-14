package main.java;
public class Author {
    private String name;
    private String surname;
    private String CF;

    public Author(String name, String surname, String CF){
        this.name=name;
        this.surname= surname;
        this.CF=CF;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCF() {
        return CF;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCF(String CF) {
        this.CF = CF;
    }
}
