import usuarios.*;
import excecoes.DadoInvalidoException;

public class Main {
    public static void main(String[] args) {
        //Teste 1:
        try {
            Usuario a = new Voluntario("A", "123.456.789-09", "felipe@email.com", "Senha@123");
            a.redefinirSenha("12345678909", "Nova@1234");
             

        } catch (DadoInvalidoException e) {
            System.out.println("ERRO!! " + e.getMessage());

        }  

        System.out.println("Tudo Certo...");
    }
}