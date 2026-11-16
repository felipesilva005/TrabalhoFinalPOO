package app;
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

        new MenuPrincipal(usuarios, animais, adocoes, notificacoes, relatorios, eventos).executar();
    }
}