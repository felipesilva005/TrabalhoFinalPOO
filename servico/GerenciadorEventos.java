package servico;
import eventos.Evento;
import excecoes.*;
import usuarios.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorEventos {
    private final List<Evento> listaEventos = new ArrayList<>();

    public void criar(Usuario responsavel, Evento evento) throws AcessoNegadoException {
        if(!(responsavel instanceof Administrador)) {
            throw new AcessoNegadoException("Apenas ADMINISTRADORES podem criar eventos.");

        }

        listaEventos.add(evento);
    }

    public Evento buscarPorId(int id) throws EventoNaoEncontradoException {
        for(Evento e : listaEventos) {
            if(e.getId() == id) {

                return e;
            }
        }

        throw new EventoNaoEncontradoException("Evento com ID " + id + " não encontrado.");
    }

    public List<Evento> listarProximos() {
        List<Evento> resultado = new ArrayList<>();
        for(Evento e : listaEventos) {
            if(!e.getData().isBefore(LocalDate.now())) {
                resultado.add(e);

            }
        }

        return resultado;
    }

    public List<Evento> listarTodos() {
        return new ArrayList<>(listaEventos);

    }

    public void inscrever(Voluntario v, int idEvento) throws EventoNaoEncontradoException, InscricaoDuplicadaException, DadoInvalidoException {
        buscarPorId(idEvento).inscrever(v);

    }

    public List<Evento> participacoesDoVoluntario(Voluntario v) {
        List<Evento> resultado = new ArrayList<>();
        
        for (Evento e : listaEventos) {
            if (e.temInscrito(v)) {
                resultado.add(e);

            }
        }

        return resultado;
    }
}