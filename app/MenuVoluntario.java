package app;
import adocao.Adocao;
import animais.Animal;
import eventos.Evento;
import excecoes.*;
import notificacoes.Notificacao;
import servico.*;
import usuarios.Voluntario;
import java.util.List;

public class MenuVoluntario {
    private final Voluntario voluntario;
    private final GerenciadorAnimais animais;
    private final GerenciadorAdocao adocoes;
    private final GerenciadorNotificacoes notificacoes;
    private final GerenciadorEventos eventos;

    public MenuVoluntario(Voluntario voluntario, GerenciadorAnimais animais, GerenciadorAdocao adocoes, GerenciadorNotificacoes notificacoes, GerenciadorEventos e) {
        this.voluntario = voluntario;
        this.animais = animais;
        this.adocoes = adocoes;
        this.notificacoes = notificacoes;
        this.eventos = e;

    }

    public void executar() {
        int opcao;
        int novas = notificacoes.contarNaoLidas(voluntario);
        if(novas > 0) {
            System.out.println("[!] Você tem " + novas + " notificação(ões) nova(s).");
        }
        
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
                    verNotificacoes();
                    break;
                case 5:
                    participarEvento();
                    break;

                case 6:
                    historicoParticipacoes();
                    break;

                case 7:
                    System.out.println("Obrigado por utilizar o Sistema de Gerenciamento de Abrigos! \nSaindo da área do voluntário...");
                    break;

                default:
                    System.out.println("Opção inválida.");

            }
        } while (opcao != 7);
    }

    //Mostra os animais disponíveis e depois pede o ID de um animal, para solicitar a adoção.
    private void solicitarAdocao() {
        List<Animal> disponiveis = animais.listarDisponiveis();

        if(disponiveis.isEmpty()) {
            System.out.println("Não à animais disponíveis para adoção no momento.");
            return;

        }
        
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

    private void verNotificacoes() {
        List<Notificacao> minhas = notificacoes.doUsuario(voluntario);

        if (minhas.isEmpty()) {
            System.out.println("Nenhuma notificação.");
            return;

        }
        for (Notificacao n : minhas) {
            System.out.println(n);

        }

        notificacoes.marcarTodasComoLidas(voluntario);
    }
    private void participarEvento() {
        for(Evento e : eventos.listarProximos()) {
            System.out.println(e);
            
        }

        int id = Leitor.inteiro("ID do evento: ");

        try {
            eventos.inscrever(voluntario, id);
            System.out.println("Inscrição realizada!");

        } catch (EventoNaoEncontradoException | InscricaoDuplicadaException | DadoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());

        }

    }

    private void historicoParticipacoes() {
        List<Evento> minhas = eventos.participacoesDoVoluntario(voluntario);

        if(minhas.isEmpty()) {
            System.out.println("Você ainda não participou de eventos.");
            return;

        }
        for(Evento e : minhas) {
            System.out.println(e);

        }
    }
}