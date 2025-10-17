import java.util.Arrays;
import java.util.Random;

public class Main
{

    private static final long SEED = 42L;

    // tamanho de tabela esquisito p/ garantir que não vai dar conflito com a função de %
    // ex. se o tamanho da tabela for 1000, os numeros que tem 00 vão dar colisão pq tem padrão
    // em comum com o tamanho da tabela
    private static final int[] TAMANHOS_TABELA = {1009, 10007, 100003};
    private static final int[] TAMANHOS_DADOS = {100, 1000, 10000};

    public static void main(String[] args) {
        for (int tamanhoDados : TAMANHOS_DADOS) {
            System.out.println("==========================================================");
            System.out.printf("GERANDO CONJUNTO DE DADOS DE %,d REGISTROS...\n", tamanhoDados);
            Registro[] dados = gerarUmConjunto(tamanhoDados, SEED);
            System.out.println("Geração concluída.");
            System.out.printf("INICIANDO TESTES PARA CONJUNTO DE DADOS DE %,d REGISTROS\n", tamanhoDados);
            System.out.println("==========================================================");

            for (int tamanhoTabela : TAMANHOS_TABELA) {
                System.out.printf("\n--- Testando com Tabela de Tamanho: %,d ---\n", tamanhoTabela);

                testarEncadeamento(dados, tamanhoTabela);

                if (tamanhoDados <= tamanhoTabela * 0.95) {
                    testarSondagemQuadratica(dados, tamanhoTabela);
                    testarHashDuplo(dados, tamanhoTabela);
                } else {
                    System.out.println("AVISO: Testes de Sondagem Quadrática e Hash Duplo pulados para esta combinação.");
                    System.out.printf("       (Fator de carga %.2f >= 1.0 é inviável para endereçamento aberto)\n", (double) tamanhoDados / tamanhoTabela);
                }
            }
            dados = null;
            System.gc();
        }
    }

    private static Registro[] gerarUmConjunto(int tamanho, long seed) {
        Random rand = new Random(seed);
        Registro[] conjunto = new Registro[tamanho];
        for (int i = 0; i < tamanho; i++) {
            int codigo = 100_000_000 + rand.nextInt(900_000_000);
            conjunto[i] = new Registro(codigo);
        }
        return conjunto;
    }

    private static void testarEncadeamento(Registro[] dados, int tamanhoTabela) {
        System.out.println("\n[Teste: Encadeamento Separado]");
        TabelaHashEncadeamento tabela = new TabelaHashEncadeamento(tamanhoTabela);

        long inicioInsercao = System.nanoTime();
        for (Registro registro : dados) {
            tabela.insere(registro);
        }
        long fimInsercao = System.nanoTime();
        double tempoInsercaoMs = (fimInsercao - inicioInsercao) / 1_000_000.0;

        long inicioBusca = System.nanoTime();
        for (Registro registro : dados) {
            tabela.busca(registro.getCodigo());
        }
        long fimBusca = System.nanoTime();
        double tempoBuscaMs = (fimBusca - inicioBusca) / 1_000_000.0;

        System.out.printf("  - Tempo de Inserção: %.4f ms\n", tempoInsercaoMs);
        System.out.printf("  - Colisões na Inserção: %,d\n", tabela.getColisoesInsercao());
        System.out.printf("  - Tempo de Busca (todos os elementos): %.4f ms\n", tempoBuscaMs);

        int[] tamanhosListas = tabela.getTamanhosDasListas();
        Arrays.sort(tamanhosListas);

        System.out.print("  - 3 Maiores Listas: ");
        if (tamanhosListas.length > 0) System.out.printf("%d", tamanhosListas[tamanhosListas.length - 1]);
        if (tamanhosListas.length > 1) System.out.printf(", %d", tamanhosListas[tamanhosListas.length - 2]);
        if (tamanhosListas.length > 2) System.out.printf(", %d", tamanhosListas[tamanhosListas.length - 3]);
        System.out.println();
    }

    private static void testarSondagemQuadratica(Registro[] dados, int tamanhoTabela) {
        System.out.println("\n[Teste: Sondagem Quadrática]");
        TabelaHashSondagemQuadratica tabela = new TabelaHashSondagemQuadratica(tamanhoTabela);

        long inicioInsercao = System.nanoTime();
        for (Registro registro : dados) {
            tabela.insere(registro);
        }
        long fimInsercao = System.nanoTime();
        double tempoInsercaoMs = (fimInsercao - inicioInsercao) / 1_000_000.0;

        long inicioBusca = System.nanoTime();
        for (Registro registro : dados) {
            tabela.busca(registro.getCodigo());
        }
        long fimBusca = System.nanoTime();
        double tempoBuscaMs = (fimBusca - inicioBusca) / 1_000_000.0;

        System.out.printf("  - Tempo de Inserção: %.4f ms\n", tempoInsercaoMs);
        System.out.printf("  - Colisões na Inserção: %,d\n", tabela.getColisoesInsercao());
        System.out.printf("  - Tempo de Busca (todos os elementos): %.4f ms\n", tempoBuscaMs);

        analisarGaps(tabela.getTabela());
    }

    private static void testarHashDuplo(Registro[] dados, int tamanhoTabela) {
        System.out.println("\n[Teste: Hash Duplo]");
        TabelaHashDuplo tabela = new TabelaHashDuplo(tamanhoTabela);

        long inicioInsercao = System.nanoTime();
        for (Registro registro : dados) {
            tabela.insere(registro);
        }
        long fimInsercao = System.nanoTime();
        double tempoInsercaoMs = (fimInsercao - inicioInsercao) / 1_000_000.0;

        long inicioBusca = System.nanoTime();
        for (Registro registro : dados) {
            tabela.busca(registro.getCodigo());
        }
        long fimBusca = System.nanoTime();
        double tempoBuscaMs = (fimBusca - inicioBusca) / 1_000_000.0;

        System.out.printf("  - Tempo de Inserção: %.4f ms\n", tempoInsercaoMs);
        System.out.printf("  - Colisões na Inserção: %,d\n", tabela.getColisoesInsercao());
        System.out.printf("  - Tempo de Busca (todos os elementos): %.4f ms\n", tempoBuscaMs);

        analisarGaps(tabela.getTabela());
    }

    private static void analisarGaps(Registro[] tabela) {
        int[] gaps = new int[tabela.length];
        int gapCount = 0;
        int gapAtual = 0;

        for (int i = 0; i < tabela.length; i++) {
            if (tabela[i] == null) {
                gapAtual++;
            } else {
                if (gapAtual > 0) {
                    gaps[gapCount] = gapAtual;
                    gapCount++;
                }
                gapAtual = 0;
            }
        }
        if (gapAtual > 0) {
            gaps[gapCount] = gapAtual;
            gapCount++;
        }

        if (gapCount == 0) {
            System.out.println("  - Análise de Gaps: Nenhum gap encontrado (tabela cheia ou vazia).");
            return;
        }

        int minGap = Integer.MAX_VALUE;
        int maxGap = Integer.MIN_VALUE;
        long sumGaps = 0;

        for (int i = 0; i < gapCount; i++) {
            int gap = gaps[i];
            if (gap < minGap) minGap = gap;
            if (gap > maxGap) maxGap = gap;
            sumGaps += gap;
        }

        double mediaGap = (double) sumGaps / gapCount;

        System.out.printf("  - Análise de Gaps: Min=%d, Max=%d, Média=%.2f\n", minGap, maxGap, mediaGap);
    }
}