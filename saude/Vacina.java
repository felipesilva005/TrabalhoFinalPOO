package saude;
import usuarios.Veterinario;
import excecoes.*;
import java.time.LocalDate;

public class Vacina extends Registro {
    private final String nome;
    private final LocalDate proximaDose;

    public Vacina(LocalDate data, Veterinario vet, String nome, LocalDate proximaDose) throws DadoInvalidoException {
        this.nome = exigirTexto(nome, "Nome da Vacina");

        if (proximaDose != null && !proximaDose.isAfter(data)) { 
            throw new DadoInvalidoException("A PRÓXIMA DOSE deve ser depois da aplicação.");
        }
        this.proximaDose = proximaDose;
        super(data, vet);
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
            return "Vacina " + nome + ".\n" + "Próxima dose:"+  getProximaDose();

        }
    }
}
