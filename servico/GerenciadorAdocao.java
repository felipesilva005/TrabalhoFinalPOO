package servico;
import usuarios.*;
import excecoes.*;
import animais.*;
import adocoes.*;
import java.util.List;
import java.util.ArrayList;

public class GerenciadorAdocao {
    private final List<Adocao> adocoesList = new ArrayList<>();

    public Adocao solicitar(Voluntario adotante, Animal animal) throws DadoInvalidoException, AnimalIndisponivelException, SolicitacaoDuplicadaException {
        for(Adocao a : adocoesList) {
            if(a.getAdotante() == adotante && a.getAnimal() == animal && a.getStatus() == StatusSolicitacao.EM_ANDAMENTO) {
                throw new SolicitacaoDuplicadaException("A solicitação para este animal está em andamento.");

            }
        }

        Adocao nova = new Adocao(adotante, animal);
        adocoesList.add(nova);

        return nova;
    }

    public Adocao buscarPorId(int id) throws AdocaoNaoEncontradaException {
        for(Adocao a : adocoesList) {
            if(a.getId() == id) {
                return a;

            }
        }

        throw new AdocaoNaoEncontradaException("O Usuário ID(" + id + ") não foi encontrado.");
    }

    public void aprovar(Usuario responsavel, int idAdocao) throws AcessoNegadoException, AnimalIndisponivelException, AdocaoNaoEncontradaException {
        Adocao escolhida = buscarPorId(idAdocao);
        escolhida.aprovarAdocao(responsavel);

        for (Adocao outra : adocoesList) {
            if (outra != escolhida && outra.getAnimal() == escolhida.getAnimal() && outra.getStatus() == StatusSolicitacao.EM_ANDAMENTO) {
                outra.rejeitarAdocao(responsavel);
                
            }
        }
    }

    public void rejeitar(Usuario responsavel, int idAdocao) throws AcessoNegadoException, AnimalIndisponivelException, AdocaoNaoEncontradaException {
        buscarPorId(idAdocao).rejeitarAdocao(responsavel);

    }

    public void registrarDevolucao(Usuario responsavel, int idAdocao) throws AcessoNegadoException, AnimalIndisponivelException, AdocaoNaoEncontradaException {
        buscarPorId(idAdocao).registrarDevolucao(responsavel);

    }

    public List<Adocao> solicitacoesDoVoluntario(Voluntario v) { //Histórico de Solicitação de um único usuário
        List<Adocao> resultado = new ArrayList<>();

        for (Adocao a : adocoesList) {
            if (a.getAdotante() == v) {
                resultado.add(a);

            }
        }

        return resultado;
    }

    public List<Adocao> pendentes() {
        List<Adocao> resultado = new ArrayList<>();
        for (Adocao a : adocoesList) {
            if (a.getStatus() == StatusSolicitacao.EM_ANDAMENTO) {
                resultado.add(a);

            }
        }
        return resultado;

    }

    public List<Adocao> historico() {
        return adocoesList;

    }
}
