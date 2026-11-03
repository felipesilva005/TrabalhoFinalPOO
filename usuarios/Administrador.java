package usuarios;
import excecoes.DadoInvalidoException;

public class Administrador extends Usuario {
    public Administrador(String nome, String cpf, String email, String senha) throws DadoInvalidoException{
        super(nome, cpf, email, senha);

    }

    @Override
    public void exibirMenu() { 
        System.out.println("ÁREA DO ADMINISTRADOR");
    }
}
