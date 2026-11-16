package app;
import adocao.Adocao;
import adocao.StatusSolicitacao;
import animais.*;
import excecoes.*;
import notificacoes.*;
import servico.*;
import usuarios.*;
import eventos.*;
import java.time.LocalDate;
import java.util.List;

public class MenuAdministrador {
    private final Administrador admin;
    private final GerenciadorUsuarios usuarios;
    private final GerenciadorAnimais animais;
    private final GerenciadorAdocao adocoes;
    private final GerenciadorNotificacoes notificacoes;
    private final GeradorRelatorios relatorios;
    private final GerenciadorEventos eventos;

    public MenuAdministrador(Administrador admin, GerenciadorUsuarios u, GerenciadorAnimais a, GerenciadorAdocao d, GerenciadorNotificacoes n, GeradorRelatorios r, GerenciadorEventos e) {
        this.admin = admin;
        this.usuarios = u;
        this.animais = a;
        this.adocoes = d;
        this.notificacoes = n;
        this.relatorios = r;
        this.eventos = e;
    }

    public void executar() {
        int opcao;
        int novas = notificacoes.contarNaoLidas(admin);
        if (novas > 0) {
            System.out.println("[!] Você tem " + novas + " notificação(ões) nova(s).");

        }
        
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
                    menuRelatorios();
                    break;

                case 8:
                    estatisticas();
                    break;

                case 9:
                    verNotificacoes();
                    break;

                case 10:
                    gerenciarEventos();
                    break;

                case 0:
                    System.out.println("Obrigado por utilizar o Sistema de Gerenciamento de Abrigos! \\nSaindo da área do administrador...");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    
            }
        } while (opcao != 0);
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
            notificacoes.enviarParaTodos(usuarios.listarTodosUsuarios(), TipoNotificacao.NOVO_ANIMAL, "Novo animal cadastrado: " + novo.getNome());

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
            Adocao adocao = adocoes.buscarPorId(id);

            if (acao == 1) {
                List<Adocao> rejeitadas = adocoes.aprovar(admin, id);
                System.out.println("Adoção aprovada!");

                notificacoes.enviar(adocao.getAdotante(), TipoNotificacao.ADOCAO, "Sua solicitação para " + adocao.getAnimal().getNome() + " foi aprovada.");
                
                for (Adocao r : rejeitadas) {
                    notificacoes.enviar(r.getAdotante(), TipoNotificacao.ADOCAO, "Sua solicitação para " + r.getAnimal().getNome() + " foi rejeitada.");
                }
            } else if (acao == 2) {
                adocoes.rejeitar(admin, id);
                System.out.println("Solicitação rejeitada.");

                notificacoes.enviar(adocao.getAdotante(), TipoNotificacao.ADOCAO, "Sua solicitação para " + adocao.getAnimal().getNome() + " foi rejeitada.");

            } else {
                System.out.println("Opção inválida.");

            }
        } catch (AcessoNegadoException | AnimalIndisponivelException | AdocaoNaoEncontradaException | DadoInvalidoException e) {
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

    private void menuRelatorios() {
        System.out.println("\n--------- RELATÓRIOS ---------");
        System.out.println("1 - Animais Resgatados");
        System.out.println("2 - Adoções Realizadas");
        System.out.println("3 - Animais em Tratamento");
        System.out.println("4 - Voluntários Ativos");
        int op = Leitor.inteiro("> ");

        switch (op) {
            case 1:
                imprimirAnimais(animais.listarTodos());
                break;

            case 2:
                for (Adocao a : adocoes.historico()) {
                    if (a.getStatus() == StatusSolicitacao.APROVADA || a.getStatus() == StatusSolicitacao.DEVOLVIDA) {
                        System.out.println(a.getId() + " | " + a.getAnimal().getNome() + " | " + a.getAdotante().getNome() + " | " + a.getDataDecisao());
                    }
                }

            break;

            case 3:
                imprimirAnimais(relatorios.animaisEmTratamento());
                break;

            case 4:
                System.out.println("Voluntários ativos: " + relatorios.voluntariosAtivos());
                break;

            default:
                System.out.println("Opção inválida.");

        }
    }

    private void estatisticas() {
        int resgatados = relatorios.totalResgatados();
        int adocoesFeitas = relatorios.totalAdocoesRealizadas();
        
        System.out.println("\n------ ESTATÍSTICAS GERAIS ------");
        System.out.println("Animais resgatados:      " + resgatados);
        System.out.println("Adoções realizadas:      " + adocoesFeitas);
        System.out.println("Em tratamento:           " + relatorios.animaisEmTratamento().size());
        System.out.println("Voluntários ativos:      " + relatorios.voluntariosAtivos());
        System.out.println("Espécie mais resgatada:  " + relatorios.especieMaisResgatada());
        System.out.printf("Taxa de adoção do mês:   %.1f%%\n", relatorios.taxaAdocaoMensal(java.time.YearMonth.now()));
    }

    private void verNotificacoes() {
        List<Notificacao> minhas = notificacoes.doUsuario(admin);

        if(minhas.isEmpty()) {
            System.out.println("Nenhuma notificação.");
            return;

        }
        for(Notificacao n : minhas) {
            System.out.println(n);

        }

        notificacoes.marcarTodasComoLidas(admin);
    }

    private void gerenciarEventos() {
        System.out.println("1 - Feira de Adoção | 2 - Campanha de Vacinação | 3 - Mutirão de Limpeza | 4 - Evento Beneficente | 5 - Listar");
        int tipo = Leitor.inteiro("> ");

        if(tipo == 5) {
            for(Evento e : eventos.listarTodos()) {
                System.out.println(e);

            }

            return;
        }

        String titulo = Leitor.texto("Título: ");
        String local = Leitor.texto("Local: ");
        LocalDate data = Leitor.data("Data (dd/MM/aaaa): ");

        try {
            Evento evento;
            switch (tipo) {
                case 1: 
                    evento = new FeiraAdocao(titulo, data, local);
                    break;

                case 2: 
                    evento = new CampanhaVacinacao(titulo, data, local);
                    break;

                case 3: 
                    evento = new MutiraoLimpeza(titulo, data, local);
                    break;

                case 4: 
                    evento = new EventoBeneficente(titulo, data, local);
                    break;

                default:
                    System.out.println("Opção inválida.");
                    return;
            
            }

            eventos.criar(admin, evento);
            System.out.println("Evento criado! ID: " + evento.getId());
            notificacoes.enviarParaTodos(usuarios.listarTodosUsuarios(), TipoNotificacao.EVENTO, "Novo evento: " + evento.getTitulo() + " em " + evento.getData());

        } catch (DadoInvalidoException | AcessoNegadoException | InscricaoDuplicadaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}