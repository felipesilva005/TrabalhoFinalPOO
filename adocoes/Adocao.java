package adocoes;
import animais.*;
import excecoes.*;
import usuarios.*;
import java.time.LocalDate;

public class Adocao {
    private static int contId = 1;
    private final int id;
    private final Voluntario adotante;
    private final Animal animal;
    private final LocalDate dataSolicitacao;
    private LocalDate dataDecisao;
    private StatusSolicitacao status;

    public Adocao(Voluntario adotante, Animal animal) throws DadoInvalidoException, AnimalIndisponivelException {
        if(adotante == null) {
            throw new DadoInvalidoException("O ADOTANTE não pode ser nulo.");

        }
        if(animal == null) {
            throw new DadoInvalidoException("O ANIMAL não pode ser nulo.");

        }
        if(animal.getStatusAdocao() != StatusAdocao.DISPONIVEL){
            throw new AnimalIndisponivelException("Este animal NÃO está disponível para adocão.");

        }

        this.id = contId++;
        this.adotante = adotante;
        this.animal = animal;
        this.dataSolicitacao = LocalDate.now();
        this.status = StatusSolicitacao.EM_ANDAMENTO;
    }

    public int getId() {
        return id;

    }
    public Voluntario getAdotante() {
        return adotante;

    }
    public Animal getAnimal() {
        return animal;

    }
    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;

    }
    public LocalDate getDataDecisao() {
        return dataDecisao;

    }
    public StatusSolicitacao getStatus() {
        return status;

    }

    private static void exigirAdministrador(Usuario i) throws AcessoNegadoException {
        if(!(i instanceof Administrador)) {
            throw new AcessoNegadoException("Apenas ADMINISTRADORES podem realizar esta ação.");

        }
    }

    public void aprovarAdocao(Usuario responsavel) throws AcessoNegadoException, AnimalIndisponivelException {
        exigirAdministrador(responsavel);

        if(status != StatusSolicitacao.EM_ANDAMENTO) {
            throw new AnimalIndisponivelException("Esta solicitação NÃO está em andamento");

        }

        animal.marcarComoAdotado(responsavel);

        this.status = StatusSolicitacao.APROVADA;
        this.dataDecisao = LocalDate.now();
    }

    public void rejeitarAdocao(Usuario responsavel) throws AcessoNegadoException, AnimalIndisponivelException {
        exigirAdministrador(responsavel);

        if(status != StatusSolicitacao.EM_ANDAMENTO) {
            throw new AnimalIndisponivelException("Esta solicitação NÃO está em andamento");

        }

        this.status = StatusSolicitacao.REJEITADA;
        this.dataDecisao = LocalDate.now();
    }

    public void registrarDevolucao(Usuario responsavel) throws AcessoNegadoException, AnimalIndisponivelException {
        exigirAdministrador(responsavel);

        if(status != StatusSolicitacao.APROVADA) {
            throw new AnimalIndisponivelException("Só adoções aprovadas podem ser devolvidas.");

        }

        animal.registrarDevolucao(responsavel);
        this.status = StatusSolicitacao.DEVOLVIDA;
    }


}
