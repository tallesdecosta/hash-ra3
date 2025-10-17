# Análise de Desempenho de Tabelas Hash em Java

**Autor:** Gabriel Murga e Danton Talles
**Disciplina:** Estruturas de Dados

## 1. Introdução

Este trabalho implementa e analisa o desempenho de três técnicas distintas de tratamento de colisão para tabelas hash: Encadeamento Separado, Sondagem Quadrática e Hash Duplo. O objetivo é comparar empiricamente a eficiência de cada abordagem em termos de tempo de inserção, tempo de busca e número de colisões, submetendo-as a diferentes fatores de carga com grandes volumes de dados, variando de 100 mil a 10 milhões de registros.

## 2. Metodologia

Para a realização dos testes, foi desenvolvido um ambiente em Java que segue estritamente as diretrizes propostas, garantindo a reprodutibilidade e a validade dos resultados.

### 2.1. Implementações de Tabela Hash

Foram implementadas as seguintes estratégias de tratamento de colisão:

* **Encadeamento Separado:** Cada posição da tabela aponta para uma lista encadeada contendo todos os elementos cujo hash corresponde àquela posição. É uma técnica robusta a altos fatores de carga.
* **Sondagem Quadrática (Rehashing):** Uma técnica de endereçamento aberto onde, em caso de colisão, as próximas posições são sondadas em incrementos quadráticos ($1^2, 2^2, 3^2, ...$) a partir da posição original.
* **Hash Duplo (Rehashing):** Uma técnica de endereçamento aberto que utiliza uma segunda função de hash para determinar o tamanho do passo na sondagem, minimizando problemas de clustering.

### 2.2. Ambiente e Parâmetros de Teste

* **Linguagem:** Java
* **Geração de Dados:** Foram gerados três conjuntos de dados (`100.000`, `1.000.000` e `10.000.000` de registros) contendo chaves numéricas de 9 dígitos. Uma `SEED` fixa (`42L`) foi utilizada para garantir a consistência dos testes.
* **Tamanhos de Tabela:** Foram utilizados três tamanhos de tabela (`1.009`, `10.007` e `100.003`). A escolha por números primos visa otimizar a distribuição de chaves da função hash baseada no método da divisão.
* **Métricas Coletadas:**
    * Tempo de Inserção (ms)
    * Número de Colisões na Inserção
    * Tempo de Busca Total (ms)
    * Tamanho das 3 maiores listas (para Encadeamento)

## 3. Resultados

Os dados coletados nos experimentos foram consolidados nas tabelas a seguir. Os testes de rehashing não foram executados (marcado como "N/A"), pois em todos os cenários testados o número de elementos era maior ou igual à capacidade da tabela, tornando a abordagem matematicamente inviável.

**Tabela 1: Tempo de Inserção (ms)**

| Tamanho Dados | Tamanho Tabela | Fator de Carga ($\alpha$) | Encadeamento   | Sond. Quadrática | Hash Duplo |
| :------------ | :------------- | :------------------------ | :------------- | :--------------- | :--------- |
| 100.000       | 1.009          | 99.11                     | 240.9840       | N/A              | N/A        |
| 100.000       | 10.007         | 9.99                      | 24.7529        | N/A              | N/A        |
| 100.000       | 100.003        | 1.00                      | 3.4174         | N/A              | N/A        |
| 1.000.000     | 1.009          | 991.08                    | 17799.7011     | N/A              | N/A        |
| 1.000.000     | 10.007         | 99.93                     | 1324.1563      | N/A              | N/A        |
| 1.000.000     | 100.003        | 10.00                     | 307.2451       | N/A              | N/A        |
| 10.000.000    | 1.009          | 9910.80                   | 1391570.9418   | N/A              | N/A        |
| 10.000.000    | 10.007         | 999.30                    | 151212.6528    | N/A              | N/A        |
| 10.000.000    | 100.003        | 100.00                    | 19644.9390     | N/A              | N/A        |

**Tabela 2: Tempo de Busca (ms)**

| Tamanho Dados | Tamanho Tabela | Fator de Carga ($\alpha$) | Encadeamento | Sond. Quadrática | Hash Duplo |
| :------------ | :------------- | :------------------------ | :----------- | :--------------- | :--------- |
| 100.000       | 1.009          | 99.11                     | 252.1347     | N/A              | N/A        |
| 100.000       | 10.007         | 9.99                      | 24.5110      | N/A              | N/A        |
| 100.000       | 100.003        | 1.00                      | 2.9159       | N/A              | N/A        |
| 1.000.000     | 1.009          | 991.08                    | 4641.6916    | N/A              | N/A        |
| 1.000.000     | 10.007         | 99.93                     | 724.3913     | N/A              | N/A        |
| 1.000.000     | 100.003        | 10.00                     | 203.2585     | N/A              | N/A        |
| 10.000.000    | 1.009          | 9910.80                   | 561513.1425  | N/A              | N/A        |
| 10.000.000    | 10.007         | 999.30                    | 70742.4505   | N/A              | N/A        |
| 10.000.000    | 100.003        | 100.00                    | 11207.5741   | N/A              | N/A        |

**Tabela 3: Número de Colisões na Inserção**

| Tamanho Dados | Tamanho Tabela | Encadeamento      | Sond. Quadrática | Hash Duplo |
| :------------ | :------------- | :---------------- | :--------------- | :--------- |
| 100.000       | 1.009          | 4.956.545         | N/A              | N/A        |
| 100.000       | 10.007         | 499.402           | N/A              | N/A        |
| 100.000       | 100.003        | 50.175            | N/A              | N/A        |
| 1.000.000     | 1.009          | 495.155.577       | N/A              | N/A        |
| 1.000.000     | 10.007         | 49.934.834        | N/A              | N/A        |
| 1.000.000     | 100.003        | 4.996.421         | N/A              | N/A        |
| 10.000.000    | 1.009          | 49.187.931.676    | N/A              | N/A        |
| 10.000.000    | 10.007         | 4.959.628.350     | N/A              | N/A        |
| 10.000.000    | 100.003        | 496.249.281       | N/A              | N/A        |

**Tabela 4: Análise das Listas Encadeadas (3 Maiores)**

| Tamanho Dados | Tamanho Tabela | Fator de Carga ($\alpha$) | 3 Maiores Listas    |
| :------------ | :------------- | :------------------------ | :------------------ |
| 100.000       | 1.009          | 99.11                     | 131, 128, 125       |
| 100.000       | 10.007         | 9.99                      | 23, 23, 22          |
| 100.000       | 100.003        | 1.00                      | 8, 7, 7             |
| 1.000.000     | 1.009          | 991.08                    | 1097, 1087, 1086    |
| 1.000.000     | 10.007         | 99.93                     | 142, 140, 139       |
| 1.000.000     | 100.003        | 10.00                     | 30, 26, 25          |
| 10.000.000    | 1.009          | 9910.80                   | 10155, 10143, 10141 |
| 10.000.000    | 10.007         | 999.30                    | 1106, 1102, 1100    |
| 10.000.000    | 100.003        | 100.00                    | 145, 145, 142       |

## 4. Análise e Discussão

A análise dos resultados revela o comportamento característico e as limitações de cada estrutura.

* **Degradação Exponencial com o Aumento do Fator de Carga:** A descoberta mais significativa é a degradação massiva do desempenho do Encadeamento Separado sob fatores de carga extremos. No pior cenário, a inserção de 10 milhões de registros em uma tabela de tamanho 1.009 ($\alpha \approx 9911$) levou **1.391.570 ms, ou aproximadamente 23 minutos**. Isso ocorre porque a estrutura deixa de operar como uma tabela hash e se torna um vetor de poucas listas encadeadas extremamente longas. A análise das listas confirma isso, com as maiores chegando a ter mais de 10.000 elementos, transformando uma operação de complexidade média $O(1)$ em uma busca de complexidade $O(n)$.

* **Inviabilidade Prática do Endereçamento Aberto:** Os resultados confirmam a limitação teórica das técnicas de rehashing de forma conclusiva. Nenhuma das execuções com os conjuntos de dados de 100 mil, 1 milhão e 10 milhões de registros pôde ser completada com as tabelas disponíveis, pois o número de elementos sempre era maior ou igual à capacidade da tabela. O programa validou que o fator de carga não pode ser $\ge 1$, provando que a Sondagem Quadrática e o Hash Duplo são aplicáveis apenas em cenários onde o tamanho da tabela é garantidamente maior que o volume de dados.

* **Ausência de Comparativo Direto:** Devido às limitações acima, não foi possível realizar uma comparação direta de desempenho entre o Encadeamento Separado e as técnicas de Endereçamento Aberto sob as mesmas condições de alta carga. A análise se concentra, portanto, na robustez de uma abordagem versus a eficiência especializada da outra.

## 5. Conclusão

Este trabalho demonstrou na prática a troca entre flexibilidade e eficiência nas técnicas de tratamento de colisão.

O **Encadeamento Separado** provou ser a única técnica funcional em cenários de alta ocupação (fator de carga $\ge 1$). Ele oferece uma robustez indispensável quando o volume de dados é desconhecido ou pode exceder a capacidade inicial da tabela, embora seu desempenho degrade severamente para $O(n)$ em condições extremas.

As técnicas de endereçamento aberto, **Sondagem Quadrática** e **Hash Duplo**, por outro lado, são especializadas para ambientes de baixo fator de carga, onde podem oferecer maior eficiência de memória e localidade de cache. No entanto, os experimentos mostraram que elas são completamente inviáveis sem um controle rigoroso do fator de carga, falhando quando a tabela se aproxima de sua capacidade máxima.

A escolha da técnica ideal, portanto, depende diretamente das restrições da aplicação. Para sistemas onde o volume de dados é imprevisível, o Encadeamento Separado é a única opção segura. Para ambientes com memória restrita e volume de dados conhecido e limitado, o Endereçamento Aberto é a abordagem superior.
