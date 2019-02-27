package br.edu.ifpb.seamplus.model.enums;

public enum SituacaoPedido {

    QUITADO("Quitado"),
    NAO_QUITADO("Não quitado");

    private final String label;

    private SituacaoPedido(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}