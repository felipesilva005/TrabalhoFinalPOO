package eventos;
import java.time.LocalDate;
import excecoes.DadoInvalidoException;
import excecoes.InscricaoDuplicadaException;

public class EventoBeneficente extends Evento {  
    public EventoBeneficente(String titulo, LocalDate data, String local) throws DadoInvalidoException, InscricaoDuplicadaException {
        super(titulo, data, local);
        
    }

    @Override
    public String descricaoTipo() {
        return "Evento Beneficente";

    }
}