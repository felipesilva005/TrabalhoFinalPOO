package app;
import animais.*;
import excecoes.*;
import saude.*;
import servico.GerenciadorAnimais;
import usuarios.Veterinario;
import java.time.LocalDate;

public class MenuVeterinario {
    private final Veterinario vet;
    private final GerenciadorAnimais animais;

    public MenuVeterinario(Veterinario vet, GerenciadorAnimais animais) {
        this.vet = vet;
        this.animais = animais;

    }

    public void executar() {
        int opcao;

        do {
            vet.exibirMenu();
            opcao = Leitor.inteiro("> ");

            switch (opcao) {
                case 1:
                    MenuAdministrador.imprimirAnimais(animais.listarTodos());
                    break;
                case 2:
                    registrarAtendimento();
                    break;

                case 3:
                    atualizarSaude();
                    break;

                case 4:
                    verHistorico();
                    break;

                case 5:
                    System.out.println("Saindo da área do veterinário...");
                    break;

                default:
                    System.out.println("Opção inválida.");

            }
        } while (opcao != 5);
    }

    private Animal escolherAnimal() throws AnimalNaoEncontradoException {
        MenuAdministrador.imprimirAnimais(animais.listarTodos());
        int id = Leitor.inteiro("ID do animal: ");
        return animais.buscarPorId(id);

    }

    private void registrarAtendimento() {
        try {
            Animal animal = escolherAnimal();

            System.out.println("1 - Consulta | 2 - Vacina | 3 - Medicamento | 4 - Cirurgia");
            int tipo = Leitor.inteiro("> ");
            LocalDate data = Leitor.data("Data (dd/MM/aaaa): ");
            Registro registro;

            switch (tipo) {
                case 1: 
                    registro = new Consulta(data, vet, Leitor.texto("Diagnóstico: "));
                    break;

                case 2:
                    String nome = Leitor.texto("Nome da vacina: ");
                    String resp = Leitor.texto("Tem reforço? (s/n): ");
                    LocalDate proxima = resp.equalsIgnoreCase("s") ? Leitor.data("Data do reforço (dd/MM/aaaa): ") : null;
                    registro = new Vacina(data, vet, nome, proxima);
                    break;

                case 3: 
                    String nomeMedicamento = Leitor.texto("Medicamento: ");
                    String dosagem = Leitor.texto("Dosagem: ");
                    registro = new Medicamento(data, vet, nomeMedicamento, dosagem);
                    break;

                case 4: 
                    registro = new Cirurgia(data, vet, Leitor.texto("Procedimento: "));
                    break;

                default: 
                    System.out.println("Opção inválida.");
                    return;
         
            }

            vet.registrarAtendimento(animal, registro);
            System.out.println("Atendimento registrado!");

        } catch (AnimalNaoEncontradoException | AcessoNegadoException | VacinaDuplicadaException | DadoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());

        }
    }

    private void atualizarSaude() {
        try {
            Animal animal = escolherAnimal();
            EstadoSaude novo = Leitor.escolher("Novo estado de saúde:", EstadoSaude.values());
            vet.atualizarEstadoSaude(animal, novo);
            System.out.println("Estado atualizado. Status de adoção: " + animal.getStatusAdocao());

        } catch (AnimalNaoEncontradoException | AcessoNegadoException | DadoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());

        }
    }

    private void verHistorico() {
        try {
            Animal animal = escolherAnimal();

            if (animal.getHistoricoMedico().isEmpty()) {
                System.out.println("Sem registros médicos.");
                return;

            }
            for (RegistroMedico r : animal.getHistoricoMedico()) {
                System.out.println("- " + r.descrever());

            }
        } catch (AnimalNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
            
        }
    }
}