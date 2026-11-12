package app;
import excecoes.*;
import servico.*;
import usuarios.*;

public class MenuPrincipal {
    private final GerenciadorUsuarios usuarios;
    private final GerenciadorAnimais animais;
    private final GerenciadorAdocao adocao;

    public MenuPrincipal(GerenciadorUsuarios u, GerenciadorAnimais a, GerenciadorAdocao d) {
        this.usuarios = u;
        this.animais = a;
        this.adocao = d;
    }

    public void executar() {
        int opcao;

        do { //Do While para executar pelo menos uma vez.
            System.out.println("\n---------------------------------------------");
            System.out.println("              MENU PRINCIPAL");
            System.out.println("---------------------------------------------\n");
            System.out.println("1 - Fazer Login");
            System.out.println("2 - Realizar Cadastro");
            System.out.println("3 - Esqueci a Senha");
            System.out.println("4 - Visualizar Animais Disponíveis");
            System.out.println("5 - Encerrar Programa");
            opcao = Leitor.inteiro("> ");

            switch (opcao) {
                case 1:
                    fazerLogin();
                    break;

                case 2:
                    cadastrar();
                    break;

                case 3:
                    esqueciSenha();
                    break;

                case 4:
                    MenuAdministrador.imprimirAnimais(animais.listarDisponiveis());
                    break;
                case 5:
                    System.out.println("Obrigado por utilizar o Sistema de Gerenciamento de Abrigos! \nSaindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");

            }
        } while (opcao != 5);
    }

    private void fazerLogin() {
        String email = Leitor.texto("E-mail: ");
        String senha = Leitor.texto("Senha: ");
        try {
            Usuario logado = usuarios.login(email, senha);
            System.out.println("Bem-vindo(a), " + logado.getNome() + "!");
            direcionar(logado);

        } catch (LoginInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());

        }
    }

    // Decide o menu que será aberto a paerrir do tipo de usuário
    private void direcionar(Usuario u) {
        if(u instanceof Administrador) {
            Administrador admin = (Administrador) u;
            //new MenuAdministrador(admin, usuarios, animais, adocao).executar();

        }/*else if(u instanceof Veterinario) {
            Veterinario vet = (Veterinario) u;
            new MenuVeterinario(vet, animais).executar();

        }else if(u instanceof Voluntario) {
            Voluntario vol = (Voluntario) u;
            new MenuVoluntario(vol, animais, adocao).executar();

        }*/
    }

    private void cadastrar() {
        System.out.println("Cadastre-se como:\n 1 - Voluntário \n2 - Veterinário");
        int tipo = Leitor.inteiro("> ");

        if(tipo != 1 && tipo != 2) {
            System.out.println("Opção inválida.");

        }

        String nome = Leitor.texto("Nome: ");
        String cpf = Leitor.texto("CPF: ");
        String email = Leitor.texto("E-mail: ");
        String senha = Leitor.texto("Senha: ");
        String crmv = (tipo == 2) ? Leitor.texto("CRMV: ") : null;

        try {
            Usuario novo = (tipo == 1) ? new Voluntario(nome, cpf, email, senha) : new Veterinario(nome, cpf, email, senha, crmv);
            usuarios.cadastrar(novo);
            System.out.println("Cadastro realizado! ID: " + novo.getId());

        } catch (DadoInvalidoException | EmailJaCadastradoException | CpfJaCadastradoException e) {
            System.out.println("Erro: " + e.getMessage());

        }
    }

    private void esqueciSenha() {
        String email = Leitor.texto("E-mail da conta: ");
        String cpf = Leitor.texto("CPF para confirmar: ");
        String nova = Leitor.texto("Nova senha: ");

        try {
            usuarios.redefinirSenha(email, cpf, nova);
            System.out.println("Senha redefinida com sucesso!");
            
        } catch (UsuarioNaoEncontradoException | DadoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());

        }
    }
}