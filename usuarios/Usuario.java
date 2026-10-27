package usuarios;
import excecoes.CpfInvalidoException;
import excecoes.DadoInvalidoException;
import excecoes.EmailInvalidoException;
import excecoes.SenhaInvalidaException;

public abstract class Usuario {
    private static int contId = 1;
    private final int id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;

    //Validação dos Dados:
    private static void validarNome(String nome) throws DadoInvalidoException {
        if(nome == null || nome.trim().isEmpty()) { //Checagem de nulo ou vazio.
            throw new DadoInvalidoException("O campo NOME não pode estar vazio.");

        }
    }

    private static String validarCpf(String cpf) throws CpfInvalidoException {
        if(cpf == null || cpf.trim().isEmpty()) {
            throw new CpfInvalidoException("O campo CPF não pode estar vazio");

        }

        String cpfLimpo = cpf.replaceAll("\\D", "");
        if(cpfLimpo.length() != 11) {
            throw new CpfInvalidoException("CPF inválido");

        }

        return cpfLimpo;
    }

    private static void validarEmail(String email) throws EmailInvalidoException {
        if(email == null || email.trim().isEmpty()) {
            throw new EmailInvalidoException("O campo EMAIL não pode estar vazio");

        }
        if(!email.contains("@") || !email.contains(".")) {
            throw new EmailInvalidoException("EMAIL inválido");

        }
    }

    private static void validarSenha(String senha) throws SenhaInvalidaException {
        if(senha == null || senha.trim().isEmpty()) {
            throw new SenhaInvalidaException("O campo SENHA não pode estar vazio");

        }
        if(senha.length() < 8) {
            throw new SenhaInvalidaException("A SENHA precisa ter no mínimo 8 digitos.");

        }

        boolean temMaiuscula = false;
        boolean temMinuscula = false;
        boolean temNumero = false;
        boolean temCaracterEspecial = false;
        for(char c : senha.toCharArray()) { //Transforma minha senha em uma lista de caracteres e assim verifica se tem digitos maiusculos, minusculos ou caracteres especial;
            if(Character.isUpperCase(c)) {
                temMaiuscula = true;

            }else if(Character.isLowerCase(c)) {
                temMinuscula = true;

            }else if(Character.isDigit(c)) {
                temNumero = true;

            }else {
                temCaracterEspecial = true;

            }
        }

        if(!temMaiuscula) {
            throw new SenhaInvalidaException("A SENHA precisa ter letras MAIUSCÚLOS.");

        }
        if (!temMinuscula) {
            throw new SenhaInvalidaException("A SENHA precisa ter letras MINUSCÚLOS.");

        }
        if (!temNumero) {
            throw new SenhaInvalidaException("A SENHA precisa ter NÚMEROS.");

        }
        if (!temCaracterEspecial) {
            throw new SenhaInvalidaException("A SENHA precisa ter CARACTERES ESPECIAIS.");

        }
    }
    public Usuario(String nome, String cpf, String email, String senha) throws DadoInvalidoException {
        //As validações são chamadas antes, para não correr o risco de "queimar" um id.
        validarNome(nome);
        String cpfLimpo = validarCpf(cpf);
        validarEmail(email);
        validarSenha(senha);

        this.id = contId++;
        this.nome = nome;
        this.cpf = cpfLimpo;
        this.email = email;
        this.senha = senha;
    }

    public int getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public String getCpf(){
        return cpf;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) throws EmailInvalidoException {
        validarEmail(email);
        this.email = email;
    }

    public abstract void exibirMenu();
}