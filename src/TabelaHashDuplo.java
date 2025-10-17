public class TabelaHashDuplo {

    private final Registro[] tabela;
    private final int tamanho;
    private final int primoAnterior;
    private int numItens;
    private long colisoesInsercao;

    public TabelaHashDuplo(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new Registro[tamanho];
        this.primoAnterior = encontrarPrimoAnterior(tamanho);
        this.numItens = 0;
        this.colisoesInsercao = 0;
    }

    private int hash1(int chave) {
        return Math.abs(chave % tamanho);
    }

    private int hash2(int chave) {
        return primoAnterior - (Math.abs(chave % primoAnterior));
    }

    public void insere(Registro registro) {
        int chave = registro.getCodigo();
        int i = 0;

        do {
            int hashIndex = (hash1(chave) + i * hash2(chave)) % tamanho;

            if (tabela[hashIndex] == null) {
                tabela[hashIndex] = registro;
                numItens++;
                return;
            }

            if (tabela[hashIndex].getCodigo() == chave) {

                return;

            }


            colisoesInsercao++;
            i++;
        } while (i < tamanho);
    }

    public Registro busca(int codigo) {
        int i = 0;
        int h1 = hash1(codigo);
        int h2 = hash2(codigo);

        do {
            int hashIndex = (h1 + i * h2) % tamanho;

            if (tabela[hashIndex] == null) {
                return null;
            }

            if (tabela[hashIndex].getCodigo() == codigo) {
                return tabela[hashIndex];
            }

            i++;
        } while (i < tamanho);

        return null;
    }

    public long getColisoesInsercao() {
        return colisoesInsercao;
    }

    public Registro[] getTabela() {
        return tabela;
    }

    private boolean ehPrimo(int n) {
        if (n <= 1) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private int encontrarPrimoAnterior(int n) {
        for (int i = n - 1; i >= 2; i--) {
            if (ehPrimo(i)) {
                return i;
            }
        }
        return 3;
    }
}