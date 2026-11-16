package servico;
import adocao.Adocao;
import adocao.StatusSolicitacao;
import animais.*;
import usuarios.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class GeradorRelatorios {
    private final GerenciadorAnimais animais;
    private final GerenciadorAdocao adocoes;
    private final GerenciadorUsuarios usuarios;

    public GeradorRelatorios(GerenciadorAnimais animais, GerenciadorAdocao adocoes, GerenciadorUsuarios usuarios) {
        this.animais = animais;
        this.adocoes = adocoes;
        this.usuarios = usuarios;
    }

    //Todos os Animais cadastrados e resgatados.
    public int totalResgatados() {
        return animais.listarTodos().size();
        
    }

    public int totalAdocoesRealizadas() {
        int total = 0;

        for (Adocao a : adocoes.historico()) {
            if (a.getStatus() == StatusSolicitacao.APROVADA || a.getStatus() == StatusSolicitacao.DEVOLVIDA) {
                total++;

            }
        }
        return total;
    }

    public Map<Especie, Integer> animaisPorEspecie() {
        Map<Especie, Integer> contagem = new EnumMap<>(Especie.class);

        for (Animal a : animais.listarTodos()) {
            contagem.merge(a.getEspecie(), 1, Integer::sum);

        }
        return contagem;

    }

    public Especie especieMaisResgatada() {
        Especie maisResgatada = null;
        int maior = 0;

        for (Map.Entry<Especie, Integer> e : animaisPorEspecie().entrySet()) {
            if (e.getValue() > maior) {
                maior = e.getValue();
                maisResgatada = e.getKey();

            }
        }

        return maisResgatada;
    }

    // Taxa de adoção do mês: adoções concluídas no mês / animais resgatados no mês
    public double taxaAdocaoMensal(YearMonth mes) {
        int resgatados = 0;

        for (Animal a : animais.listarTodos()) {
            if (YearMonth.from(a.getDataResgate()).equals(mes)) {
                resgatados++;

            }
        }
        if (resgatados == 0) {
            return 0.0;

        }

        int adotados = 0;

        for (Adocao a : adocoes.historico()) {
            boolean concluida = a.getStatus() == StatusSolicitacao.APROVADA || a.getStatus() == StatusSolicitacao.DEVOLVIDA;

            if (concluida && a.getDataDecisao() != null && YearMonth.from(a.getDataDecisao()).equals(mes)) {
                adotados++;

            }
        }
        return adotados * 100.0 / resgatados;
    }

    public int voluntariosAtivos() {
        List<Voluntario> ativos = new ArrayList<>();

        for (Adocao a : adocoes.historico()) {
            if (!ativos.contains(a.getAdotante())) {
                ativos.add(a.getAdotante());

            }
        }
        return ativos.size();
    }

    public List<Animal> animaisEmTratamento() {
        List<Animal> resultado = new ArrayList<>();

        for (Animal a : animais.listarTodos()) {
            if (a.getEstadoSaude() != EstadoSaude.SAUDAVEL) {
                resultado.add(a);

            }
        
        
        }

        return resultado;
    }
}