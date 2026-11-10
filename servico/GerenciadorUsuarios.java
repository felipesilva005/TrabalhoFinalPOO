package servico;
import usuarios.*;
import excecoes.*;
import java.util.List;
import java.util.ArrayList;

public class GerenciadorUsuarios {
    private final List<Usuario> UsuariosList = new ArrayList<>();

    //Cadastro de usuários.
    public void cadastrarUsuario(Usuario novo) throws EmailJaCadastradoException, CpfJaCadastradoException{
        for (Usuario i : UsuariosList) {
            if (i.getEmail().equalsIgnoreCase(novo.getEmail())) { //Verifica o email ignorando o Case Sensitive.
                throw new EmailJaCadastradoException("Este email já está cadastrado.");

            }
            if (i.getCpf().equals(novo.getCpf())) {
                throw new CpfJaCadastradoException("Este CPF já está cadastrado.");

            }
        }

        UsuariosList.add(novo);
    }

    //Filtros de busca;
    public Usuario buscarPorEmail(String email) throws UsuarioNaoEncontradoException {
        for(Usuario i : UsuariosList) {
            if(i.getEmail().equalsIgnoreCase(email.trim())) {
                return i;

            }
        }

        throw new UsuarioNaoEncontradoException("O USUÁRIO não foi encontrado.");
    }
    
    public Usuario buscarPorId(int id) throws UsuarioNaoEncontradoException {
        for(Usuario i : UsuariosList) {
            if(i.getId() == id) {
                return i;

            }
        }

        throw new UsuarioNaoEncontradoException("O Usuário ID(" + id + ") não foi encontrado.");
    }

    //Login e caso de redefinição de senha.

    public Usuario login(String email, String senha) throws LoginInvalidoException {
        /*O try/catch captura a exceção de usuário não encontrado e a converte em login inválido. Assim, a mensagem
         é a mesma para e-mail inexistente e senha incorreta, impedindo também de "vazar" um e-mail já cadastrado.*/
        try {
            Usuario i = buscarPorEmail(email);
            if(i.autenticar(senha)) {
                return i;
            }
        } catch (UsuarioNaoEncontradoException e) {
        
        }

        throw new LoginInvalidoException("Login INVÁLIDO, verifique seu email e sua senha e tente novamente.");
    }

    public void redefinirSenha(String email, String cpf, String novaSenha) throws UsuarioNaoEncontradoException, DadoInvalidoException {
        Usuario i = buscarPorEmail(email);
        i.redefinirSenha(cpf, novaSenha);

    }

    public List<Usuario> listarTodosUsuarios() {
        return UsuariosList;
    }
}
