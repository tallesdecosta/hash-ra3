public class TabelaHashEncadeamento {

    private final Node[] tabela;
    private final int tamanho;
    private long colisoesInsercao;

    public TabelaHashEncadeamento(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new Node[tamanho];
        this.colisoesInsercao = 0;
    }

    private int hash(int chave) {
        return Math.abs(chave % tamanho);
    }

    public void insere(Registro registro) {
        int chave = registro.getCodigo();
        int hashIndex = hash(chave);
        Node novoNode = new Node(registro);

        if (tabela[hashIndex] == null) {
            tabela[hashIndex] = novoNode;
            return;
        }

        Node atual = tabela[hashIndex];
        while (true) {
            if (atual.registro.getCodigo() == chave) {
                return;
            }
            if (atual.next == null) {
                break; 
            }
            colisoesInsercao++;
            atual = atual.next;
        }


        colisoesInsercao++;
        atual.next = novoNode;
    }

    public Registro busca(int codigo) {
        int hashIndex = hash(codigo);
        Node atual = tabela[hashIndex];

        while (atual != null) {
            if (atual.registro.getCodigo() == codigo) {
                return atual.registro;
            }
            atual = atual.next;
        }
        return null;
    }

    public long getColisoesInsercao() {
        return colisoesInsercao;
    }

    public int[] getTamanhosDasListas() {
        int[] tamanhos = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            int count = 0;
            Node atual = tabela[i];
            while (atual != null) {
                count++;
                atual = atual.next;
            }
            tamanhos[i] = count;
        }
        return tamanhos;
    }

}
