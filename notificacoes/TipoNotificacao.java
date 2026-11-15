package notificacoes;

public enum TipoNotificacao {
    NOVO_ANIMAL("Novo animal"),
    EVENTO("Evento"),
    VACINA("Vacina"),
    ADOCAO("Adoção"),
    AVISO_GERAL("Aviso Geral");

    private final String descricao;

    TipoNotificacao(String descricao) {
        this.descricao = descricao;

    }

    public String getDescricao() {
        return descricao;
        
    }
}