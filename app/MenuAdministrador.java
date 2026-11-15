package app;
import adocao.Adocao;
import animais.*;
import excecoes.*;
import servico.*;
import usuarios.*;
import java.time.LocalDate;
import java.util.List;

public class MenuAdministrador {
    private final Administrador admin;
    private final GerenciadorUsuarios usuarios;
    private final GerenciadorAnimais animais;
    private final GerenciadorAdocao adocoes;

    public MenuAdministrador(Administrador admin, GerenciadorUsuarios u, GerenciadorAnimais a, GerenciadorAdocao d) {
        this.admin = admin;
        this.usuarios = u;
        this.animais = a;
        this.adocoes = d;
    }

    public void executar() {
        int opcao;
        do {
            admin.exibirMenu(); 
            opcao = Leitor.inteiro("> ");
            
            switch (opcao) {
                case 1:
                    cadastrarAnimal();
                    break;

                case 2:
                    imprimirAnimais(animais.listarTodos());
                    break;

                case 3:
                    removerAnimal();
                    break;

                case 4:
                    listarUsuarios();
                    break;

                case 5:
                    decidirAdocoes();
                    break;

                case 6:
                    registrarDevolucao();
                    break;

                case 7:
                    System.out.println("Obrigado por utilizar o Sistema de Gerenciamento de Abrigos! \\nSaindo da área do administrador...");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    
            }
        } while (opcao != 7);
    }

    private void cadastrarAnimal() {
        String nome = Leitor.texto("Nome: ");
        Especie especie = Leitor.escolher("Espécie:", Especie.values());
        String raca = Leitor.texto("Raça: ");
        int idade = Leitor.inteiro("Idade: ");
        Porte porte = Leitor.escolher("Porte:", Porte.values());
        LocalDate data = Leitor.data("Data do resgate (dd/MM/aaaa): ");

        try {
            Animal novo = new Animal(nome, especie, raca, idade, porte, data);
            animais.cadastrarAnimais(admin, novo);
            System.out.println("Animal cadastrado! ID: " + novo.getId());

        } catch (DadoInvalidoException | AcessoNegadoException | AnimalNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());

        }
    }

    private void removerAnimal() {
        int id = Leitor.inteiro("ID do animal: ");

        try {
            animais.removerAnimais(admin, id);
            System.out.println("Animal removido.");

        } catch (AcessoNegadoException | AnimalNaoEncontradoException | RemocaoNaoPermitidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarUsuarios() {
        for (Usuario u : usuarios.listarTodosUsuarios()) {
            System.out.println(u.getId() + " | " + u.getNome() + " | " + u.getEmail() + " | " + u.getClass().getSimpleName());
            //Lista os usúarios e suas funções através do getClass().getSimpleName, voltadando o nome da classe que o usuário está cadastrado.

        }
    }

    private void decidirAdocoes() {
        List<Adocao> pendentes = adocoes.pendentes();

        if (pendentes.isEmpty()) {
            System.out.println("Nenhuma solicitação em andamento.");
            return;

        }
        for (Adocao a : pendentes) {
            System.out.println(a.getId() + " | " + a.getAdotante().getNome() + " quer " + a.getAnimal().getNome() + " | " + a.getDataSolicitacao());

        }

        int id = Leitor.inteiro("ID da solicitação: ");
        int acao = Leitor.inteiro("1 - Aprovar | 2 - Rejeitar: ");

        try {
            if (acao == 1) {
                adocoes.aprovar(admin, id);
                System.out.println("Adoção aprovada!");

            } else if (acao == 2) {
                adocoes.rejeitar(admin, id);
                System.out.println("Solicitação rejeitada.");

            } else {
                System.out.println("Opção inválida.");

            }
        } catch (AcessoNegadoException | AnimalIndisponivelException | AdocaoNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());

        }
    }

    private void registrarDevolucao() {
        int id = Leitor.inteiro("ID da adoção a devolver: ");

        try {
            adocoes.registrarDevolucao(admin, id);
            System.out.println("Devolução registrada.");

        } catch (AcessoNegadoException | AnimalIndisponivelException | AdocaoNaoEncontradaException e) {
            System.out.println("Erro: " + e.getMessage());

        }
    }

    public static void imprimirAnimais(List<Animal> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum animal encontrado.");
            return;

        }

        System.out.println("ID | Nome | Espécie | Idade | Saúde | Status");

        for (Animal a : lista) {
            System.out.println(a.getId() + " | " + a.getNome() + " | " + a.getEspecie() + " | " + a.getIdade() + " ano(s) | " + a.getEstadoSaude() + " | " + a.getStatusAdocao());
        }
    }
}
