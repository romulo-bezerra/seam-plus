package br.edu.ifpb.seamplus.model.enums;

public enum SituacaoPedido {

    QUITADO("Quitado"),
    NAO_QUITADO("Nao_Quitado");

    private final String label;

    private SituacaoPedido(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}