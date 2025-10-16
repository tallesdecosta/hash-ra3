public class TabelaHashSondagemQuadratica {

    private final Registro[] tabela;
    private final int tamanho;
    private int numItens;
    private long colisoesInsercao;

    public TabelaHashSondagemQuadratica(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new Registro[tamanho];
        this.numItens = 0;
        this.colisoesInsercao = 0;
    }

    private int hash(int chave) {
        return Math.abs(chave % tamanho);
    }

    public void insere(Registro registro) {
        int chave = registro.getCodigo();
        int i = 0;

        do {
            int hashIndex = (hash(chave) + i * i) % tamanho;

            if (tabela[hashIndex] == null) {
                tabela[hashIndex] = registro;
                numItens++;
                return;
            }

            if (tabela[hashIndex].getCodigo() == chave) {
                return; // Chave duplicada, não faz nada.
            }

            // O slot está ocupado por outra chave, isso é uma colisão.
            colisoesInsercao++;
            i++;
        } while (i < tamanho);
    }

    public Registro busca(int codigo) {
        int i = 0;

        do {
            int hashIndex = (hash(codigo) + i * i) % tamanho;

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
}