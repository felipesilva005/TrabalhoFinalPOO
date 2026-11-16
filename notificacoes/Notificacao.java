package notificacoes;
import excecoes.DadoInvalidoException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notificacao {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static int contId = 1;
    private final int id;
    private final TipoNotificacao tipo;
    private final String mensagem;
    private final int destinatarioId;
    private final LocalDateTime criadaEm;
    private boolean lida;

    public Notificacao(TipoNotificacao tipo, String mensagem, int destinatarioId) throws DadoInvalidoException {
        if(tipo == null) {
            throw new DadoInvalidoException("O TIPO da notificação é obrigatório.");

        }
        if(mensagem == null || mensagem.trim().isEmpty()) {
            throw new DadoInvalidoException("A MENSAGEM é obrigatória na notificação.");

        }

        this.id = contId++;
        this.tipo = tipo;
        this.mensagem = mensagem.trim();
        this.destinatarioId = destinatarioId;
        this.criadaEm = LocalDateTime.now();
        this.lida = false;
    }

    public int getId() { 
        return id; 
    }
    public TipoNotificacao getTipo() { 
        return tipo; 
    }
    public String getMensagem() { 
        return mensagem; 
    }
    public int getDestinatarioId() {
        return destinatarioId; 
    }
    public LocalDateTime getCriadaEm() {
        return criadaEm; 
    }
    public boolean isLida() {
        return lida; 
    }

    public void marcarComoLida() {
        this.lida = true;
    }

    @Override
    public String toString() {
        String marca = lida ? "    " : "[!] ";
        return marca + "[" + tipo.getDescricao() + "] " + mensagem + " (" + criadaEm.format(FORMATO) + ")";
    }
}