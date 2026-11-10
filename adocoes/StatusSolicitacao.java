package adocoes;

public enum StatusSolicitacao {
    APROVADA("Solicitação APROVADA."),
    REJEITADA("Solicitação REJEITADA."),
    EM_ANDAMENTO("Solicitação EM ANDAMENTO."),
    DEVOLVIDA("Animal devolvido");

    private final String descricao;

    StatusSolicitacao(String descricao) {
        this.descricao = descricao;

    }

    public String getDescricao() {
        return descricao;
    }
}
