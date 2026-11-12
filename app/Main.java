package app;
import excecoes.*;
import servico.*;
import usuarios.*;

public class Main {
    public static void main(String[] args) {
        GerenciadorUsuarios usuarios = new GerenciadorUsuarios();
        GerenciadorAnimais animais = new GerenciadorAnimais();
        GerenciadorAdocao adocoes = new GerenciadorAdocao();

        // Administrador padrão.
        try {
            usuarios.cadastrar(new Administrador("Administrador", "000.000.000-00", "admin@abrigo.com", "Adm@1234"));

        } catch (DadoInvalidoException | EmailJaCadastradoException | CpfJaCadastradoException e) {
            System.out.println("Erro ao criar administrador padrão: " + e.getMessage());
            
        }

        new MenuPrincipal(usuarios, animais, adocoes).executar();
    }
}