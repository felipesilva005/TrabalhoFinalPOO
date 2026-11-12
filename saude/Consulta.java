package saude;
import usuarios.Veterinario;
import excecoes.*;
import java.time.LocalDate;

public class Consulta extends Registro {
    private final String diagnostico;

    public Consulta(LocalDate data, Veterinario vet, String diagnostico) throws DadoInvalidoException{
        this.diagnostico = exigirTexto(diagnostico, "Diagnóstico");
        super(data, vet);
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    @Override
    public String descrever(){
        return "Data da Consulta: " + getData() + ";\n" + "Diagnóstico:\n" + diagnostico + "\n" + "Dr(a). " + getVeterinario().getNome() + "." ;
    }
}
