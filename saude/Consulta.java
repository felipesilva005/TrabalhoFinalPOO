package saude;
import usuarios.Veterinario;
import excecoes.*;
import java.time.LocalDate;

public class Consulta extends Registro {
    private final String diagnostico;

    public Consulta(LocalDate data, Veterinario veterinario, String diagnostico) throws DadoInvalidoException{
        super(data, veterinario);
        this.diagnostico = diagnostico;

    }

    public String getDiagnostico() {
        return diagnostico;
    }

    @Override
    public String descrever(){
        return "Data da Consulta: " + getData() + ";\n" + "Diagnostico:\n" + diagnostico + "\n" + "Dr(a). " + getVeterinario().getNome() + "." ;
    }
}
