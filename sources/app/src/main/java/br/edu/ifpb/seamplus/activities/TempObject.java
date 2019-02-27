package br.edu.ifpb.seamplus.activities;

public class TempObject {

    private long id;
    private String string1;
    private String string2;

    public TempObject() {}

    public TempObject(String string1, String string2) {
        this.string1 = string1;
        this.string2 = string2;
    }

    public TempObject(long id, String string1, String string2) {
        this.id = id;
        this.string1 = string1;
        this.string2 = string2;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

}