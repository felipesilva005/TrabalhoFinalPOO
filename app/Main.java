package app;
import animais.*;
import excecoes.*;
import servico.*;
import usuarios.*;

public class Main {
    public static void main(String[] args) {

        GerenciadorUsuarios usuarios = new GerenciadorUsuarios();
        GerenciadorAnimais animais = new GerenciadorAnimais();
        GerenciadorAdocao adocoes = new GerenciadorAdocao();
        GerenciadorNotificacoes notificacoes = new GerenciadorNotificacoes();
        GeradorRelatorios relatorios = new GeradorRelatorios(animais, adocoes, usuarios);
        GerenciadorEventos eventos = new GerenciadorEventos();

        //Administrador padrão.
        Administrador admin = null;
        try {
            admin = new Administrador("Administrador", "000.000.000-00", "admAbrigo@gmail.com", "Admin@1234");
            usuarios.cadastrarUsuario(admin);

        } catch (DadoInvalidoException | EmailJaCadastradoException | CpfJaCadastradoException e) {
            System.out.println("Erro ao criar administrador padrão: " + e.getMessage());
            return;

        }

        //Dados de Exemplo:
        //Usuários
        try {
            //Voluntários
            usuarios.cadastrarUsuario(new Voluntario("Felipe", "111.111.111-11", "felipe@gmail.com", "Senha@123"));
            usuarios.cadastrarUsuario(new Voluntario("Leoneide", "222.222.222-11", "leoneide@gmail.com", "Senha@123"));

            //Veterinário
            usuarios.cadastrarUsuario(new Veterinario("Vitor", "333.333.333-33", "vitor@gmail.com", "Senha@123", "CRMV-CE 1234"));

        }catch (DadoInvalidoException | EmailJaCadastradoException | CpfJaCadastradoException e) {
            System.out.println("Erro ao criar dados de exemplo: " + e.getMessage());

        }

        new MenuPrincipal(usuarios, animais, adocoes, notificacoes, relatorios, eventos).executar();
    }
}