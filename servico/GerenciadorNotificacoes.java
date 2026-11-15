package servico;
import notificacoes.*;
import excecoes.DadoInvalidoException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorNotificacoes {
    private final List<Notificacao> listaNotificacoes = new ArrayList<>();

    public void enviar(Notificavel destinatario, TipoNotificacao tipo, String mensagem) throws DadoInvalidoException {
        if (destinatario == null) {
            throw new DadoInvalidoException("O DESTINATÁRIO não pode ser nulo.");

        }

        listaNotificacoes.add(new Notificacao(tipo, mensagem, destinatario.getId()));
    }

    //Notificação para vários usuários.
    public void enviarParaTodos(List<? extends Notificavel> destinatarios, TipoNotificacao tipo, String mensagem) throws DadoInvalidoException {
        for (Notificavel n : destinatarios) {
            enviar(n, tipo, mensagem);

        }
    }

    // Todas as notificações de um usuário.
    public List<Notificacao> doUsuario(Notificavel destinatario) {
        List<Notificacao> resultado = new ArrayList<>();

        for (Notificacao n : listaNotificacoes) {
            if (n.getDestinatarioId() == destinatario.getId()) {
                resultado.add(n);

            }
        }

        return resultado;
    }

    public int contarNaoLidas(Notificavel destinatario) {
        int total = 0;

        for (Notificacao n : doUsuario(destinatario)) {
            if (!n.isLida()) {
                total++;

            }
        }

        return total;
    }

    public void marcarTodasComoLidas(Notificavel destinatario) {
        for (Notificacao n : doUsuario(destinatario)) {
            n.marcarComoLida();
            
        }
    }
}