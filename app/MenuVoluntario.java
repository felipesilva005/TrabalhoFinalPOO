package app;
import adocao.Adocao;
import animais.Animal;
import excecoes.*;
import servico.*;
import usuarios.Voluntario;
import java.util.List;

public class MenuVoluntario {
    private final Voluntario voluntario;
    private final GerenciadorAnimais animais;
    private final GerenciadorAdocao adocoes;

    public MenuVoluntario(Voluntario voluntario, GerenciadorAnimais animais, GerenciadorAdocao adocoes) {
        this.voluntario = voluntario;
        this.animais = animais;
        this.adocoes = adocoes;
    }

    public void executar() {
        int opcao;

        do {
            voluntario.exibirMenu();
            opcao = Leitor.inteiro("> ");

            switch (opcao) {
                case 1:
                    MenuAdministrador.imprimirAnimais(animais.listarDisponiveis());
                    break;
                case 2:
                    solicitarAdocao();
                    break;

                case 3:
                    minhasSolicitacoes();
                    break;

                case 4:
                    System.out.println("Saindo da área do voluntário...");
                    break;

                default:
                    System.out.println("Opção inválida.");

            }
        } while (opcao != 4);
    }

    private void solicitarAdocao() {
        MenuAdministrador.imprimirAnimais(animais.listarDisponiveis());
        int id = Leitor.inteiro("ID do animal: ");

        try {
            Animal animal = animais.buscarPorId(id);
            Adocao nova = adocoes.solicitar(voluntario, animal);
            System.out.println("Solicitação registrada! Código: " + nova.getId());

        } catch (AnimalNaoEncontradoException | AnimalIndisponivelException | SolicitacaoDuplicadaException | DadoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());

        }
    }

    private void minhasSolicitacoes() {
        List<Adocao> minhas = adocoes.solicitacoesDoVoluntario(voluntario);

        if (minhas.isEmpty()) {
            System.out.println("Você ainda não fez solicitações.");
            return;

        }
        for (Adocao a : minhas) {
            System.out.println(a.getId() + " | " + a.getAnimal().getNome() + " | " + a.getDataSolicitacao() + " | " + a.getStatus());

        }
    }
}
