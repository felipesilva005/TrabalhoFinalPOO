package animais;

public enum StatusAdocao {
    DISPONIVEL("Este animal está disponível para adoção."),
    EM_PROCESSO("Este animal está em processo de adoção."),
    NAO_DISPONIVEL("Este animal não está disponível para adoção."),
    ADOTADO("Este animal já foi adotado!");

    private final String descricao;

    StatusAdocao(String descricao) {
        this.descricao = descricao;

    }

    public String getDescricao() {
        return descricao;
    }
}
