package br.edu.ifpb.seamplus.model.enums;

public enum Sexo {

    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    OUTRO("Outro");

    private final String label;

    private Sexo(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}