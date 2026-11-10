package saude;
import excecoes.*;
import usuarios.Veterinario;
import java.time.LocalDate;

public abstract class Registro implements RegistroMedico{
    private static int contId = 1;
    private final int id;
    private final LocalDate data;
    private final Veterinario veterinario;

    protected Registro(LocalDate data, Veterinario veterinario) throws DadoInvalidoException {
        if(data == null) {
            throw new DadoInvalidoException("A DATA não pode ser nula.");

        }
        if(data.isAfter(LocalDate.now())) {
            throw new DadoInvalidoException("Data INVÁLIDA.");

        }
        if(veterinario == null) {
            throw new DadoInvalidoException("O VETERINÁRIO não pode ser nulo.");

        }

        this.id = contId++;
        this.data = data;
        this.veterinario = veterinario;
    }
    
    public int getId() {
        return id;
    }

    @Override
    public LocalDate getData() {
        return data;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    protected static String exigirTexto(String valor, String campo) throws DadoInvalidoException {
        if(valor == null || valor.trim().isEmpty()) {
            throw new DadoInvalidoException("O Campo " + campo + " NÃO pode está vazio.");

        }

        return valor.trim();
    }

}