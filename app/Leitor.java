package app;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

//Trato o scanner como uma classe própria assim validando os dados digitados pelos usuários
public class Leitor {
    private static Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String texto(String mensagem) {
        System.out.print(mensagem);
        return sc.nextLine();
    }

    public static int inteiro(String mensagem) {
        while(true) {
            try {
                return Integer.parseInt(texto(mensagem).trim());

            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");

            }
        }
    }

    public static LocalDate data(String mensagem) {
        while(true) {
            try {
                return LocalDate.parse(texto(mensagem).trim(), FORMATO);

            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato dd/MM/aaaa.");

            }
        }
    }

    // Mostra os valores de um enum numerados e devolve o escolhido
    public static <T extends Enum<T>> T escolher(String titulo, T[] valores) {
        System.out.println(titulo);

        for(int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + " - " + valores[i]);

        }
        while(true) {
            int op = inteiro("-> ");

            if(op >= 1 && op <= valores.length) {
                return valores[op - 1];

            }

            System.out.println("Opção inválida.");
        }
    }
}