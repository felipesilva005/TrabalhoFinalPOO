package animais;

public enum EstadoSaude {
    SAUDAVEL("O animal está perfeitamente saudável."),
    SENDO_ACOMPANHADO("O animal está realizando acompanhamento veterinário."),
    DOENTE("O animal está doente.");

    private final String descricao;

    EstadoSaude(String descricao) {
        this.descricao = descricao;

    }

    public String getDescricao() {
        return descricao;
    }
}
