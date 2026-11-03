package usuarios;
import excecoes.DadoInvalidoException;

public class Veterinario extends Usuario {
    private final String crmv;

    private static void validarCrmv(String crmv) throws DadoInvalidoException {
        if(crmv == null || crmv.trim().isEmpty()) { //Checagem de nulo ou vazio.
            throw new DadoInvalidoException("O campo CRMV não pode estar vazio.");

        }
    }
    public Veterinario(String nome, String cpf, String email, String senha, String crmv) throws DadoInvalidoException{
        validarCrmv(crmv);

        super(nome, cpf, email, senha);
        this.crmv = crmv;
    }

    public String getCrmv(){
        return crmv;
    }

    @Override
    public void exibirMenu() { 
        System.out.println("ÁREA DO VETERINÁRIO");
    }
}