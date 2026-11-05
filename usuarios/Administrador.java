package usuarios;
import animais.*;
import excecoes.*;

public class Administrador extends Usuario {
    public Administrador(String nome, String cpf, String email, String senha) throws DadoInvalidoException{
        super(nome, cpf, email, senha);

    }

    @Override
    public void exibirMenu() { 
        System.out.println("ÁREA DO ADMINISTRADOR");
    }

    public void concluirAdocao(Animal animal) throws AcessoNegadoException, AnimalIndisponivelException {
        animal.marcarComoAdotado(this);

    } 

    public void receberDevolucao(Animal animal) throws AcessoNegadoException, AnimalIndisponivelException {
        animal.registrarDevolucao(this);

    } 
}
