package usuarios;
import excecoes.DadoInvalidoException;

public class Voluntario extends Usuario {
    public Voluntario(String nome, String cpf, String email, String senha) throws DadoInvalidoException{
        super(nome, cpf, email, senha);

    }

    @Override
    public void exibirMenu() { 
        System.out.println("ÁREA DO VOLUNTÁRIO");
    }
}
