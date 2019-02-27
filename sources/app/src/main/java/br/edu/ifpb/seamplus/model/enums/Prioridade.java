package br.edu.ifpb.seamplus.model.enums;

public enum Prioridade {

    URGENTE("Urgente"),
    NORMAL("Normal");

    private final String label;

    private Prioridade(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}