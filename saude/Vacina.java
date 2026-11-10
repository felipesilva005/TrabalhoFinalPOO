package saude;
import usuarios.Veterinario;
import excecoes.*;
import java.time.LocalDate;

public class Vacina extends Registro {
    private final String nome;
    private final LocalDate proximaDose;

    public Vacina(LocalDate data, Veterinario veterinario, String nome, LocalDate proximaDose) throws DadoInvalidoException {
        super(data, veterinario);
        this.nome = exigirTexto(nome, "Nome da Vacina");
        this.proximaDose = proximaDose;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getProximaDose() {
        return proximaDose;
    }

    @Override 
    public String descrever(){
        if(proximaDose == null) {
            return "Vacina " + nome + ".\n" + "Próxima dose: DOSE ÚNICA.";
            
        }else {
            return "Vacina " + nome + ".\n" + "Próxima dose:"+  getData();

        }
    }
}
