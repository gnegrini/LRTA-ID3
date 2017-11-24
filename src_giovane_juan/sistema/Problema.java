/* 
 * @author Tacla
 * Classe para representar um problema no labirinto; sao representacoes que
 * ficam na 'mente' do agente
 */
package sistema;

import comuns.*;

public class Problema implements CoordenadasGeo {

    protected int estIni[];     // estado inicial [linha, coluna] = pos do agente         
    protected int estObj[];    // estado objetivo [linha, coluna]
    protected int estAtu[];     // estado atual [linha, coluna]  
    protected Labirinto creLab;           // crença do agente sobre como eh o labirinto
    protected int maxLin, maxCol;
    protected float heuristica[][];      

    public Problema() {      
        this.maxLin = 9;
        this.maxCol = 9;
        this.estAtu = new int[2];
        this.estIni = new int[2];
        this.estObj = new int[2];
        this.heuristica = new float[maxLin][maxCol];
    }

    public void setCreLab(Labirinto creLab) {
        this.creLab = creLab;
    }    
    
    /*
     * O que o agente crê sobre o labirinto
     */
    public void criarLabirinto() {

        creLab = new Labirinto(maxLin, maxCol);
        
	// Cria crença identica ao labirinto real    
        creLab.porParedeHorizontal(0, 7, 0);
        creLab.porParedeHorizontal(0, 1, 1);
        creLab.porParedeVertical(2, 3, 0);
        creLab.porParedeHorizontal(3, 5, 2);
        creLab.porParedeHorizontal(3, 6, 3);
        creLab.porParedeHorizontal(2, 2, 5);
        creLab.porParedeHorizontal(5, 7, 5);
        creLab.porParedeHorizontal(4, 7, 6);
        creLab.porParedeHorizontal(4, 7, 7);
        creLab.porParedeHorizontal(2, 2, 8);
        creLab.porParedeVertical(6, 8, 1);      
    }

    /*
     * Define estado inicial
     */
    protected void defEstIni(int linha, int coluna) {
        estIni[0] = linha;
        estIni[1] = coluna;
    }

    /*
     * Define estado objetivo
     */
    protected void defEstObj(int linha, int coluna) {
        estObj[0] = linha;
        estObj[1] = coluna;
    }
    /*
     * Define estado atual
     */

    protected void defEstAtu(int linha, int coluna) {
        estAtu[0] = linha;
        estAtu[1] = coluna;
    }
    /*
     * Funcao sucessora: recebe um estado '(lin, col)' e calcula o estado sucessor
     * que resulta da execucao da acao = {N, NE, L, SE, S, SO, O, NO}
     */

    protected int[] suc(int[] estado, int acao) {
        // @todo - OK!
        // calcular estado sucessor a partir do estado e acao
        int[] suc =  new int[2];
        int lin = estado[0];
        int col = estado[1];
        switch (acao){
            case 0: 
                    //N
                    lin = lin-1;
                    break;
            case 1: 
                    //NE
                    lin = lin-1;
                    col = col+1;
                    break;
            case 2: 
                    //L
                    col = col+1;
                    break;
            case 3:
                    //SE
                    lin = lin+1;
                    col = col+1;
                    break;
            case 4:
                    //S
                    lin = lin+1;
                    break;
            case 5:
                    //SO
                    lin = lin+1;
                    col = col-1;
                    break;
            case 6:
                    //O
                    col = col-1;
                    break;
            case 7:
                    //NO
                    col = col-1;
                    lin = lin-1;
                    break;
        }

        suc[0] = lin;     
        suc[1] = col;
        return suc;
    }

    /*
     * Retorna as acoesPossiveis possiveis de serem executadas em um estado 
     * O valor retornado é um vetor de inteiros. Se o valor da posicao é -1
     * então a ação correspondente não pode ser executada, caso contrario valera 1.
     * Por exemplo, 
     * [-1, -1, -1, 1, 1, -1, -1, -1] indica apenas que S e SO podem ser executadas.
     */
    protected int[] acoesPossiveis(int[] estado) {
        int acoes[] = new int[8];     //acao[] = {"N","NE","L","SE","S","SO","O","NO"}
        //@todo - OK!
        int linha = estado [0];
        int coluna = estado [1];

		if (linha != 0 && (creLab.parede[linha-1][coluna])!=1){ //checa N
			acoes[0] = 1;
		} else {
			acoes[0] = -1;
		}
		if (linha != 0 && coluna<(maxCol-1)&& (creLab.parede[linha-1][coluna+1])!=1 ){ //checa NE
			acoes[1] = 1;
		} else {
			acoes[1] = -1;
		}	
		if (coluna<(maxCol-1) && (creLab.parede[linha][coluna+1])!=1){ //checa L
			acoes[2] = 1;
		} else {
			acoes[2] = -1;
		}
		if (linha < (maxLin-1) && coluna<(maxCol-1) && (creLab.parede[linha+1][coluna+1])!=1){ //checa SE
			acoes[3] = 1;
		} else {
			acoes[3] = -1;
		}
		if (linha < (maxLin-1) && creLab.parede[linha+1][coluna]!=1){ //checa S
			acoes[4] = 1;
		} else {
			acoes[4] = -1;
		}		
                if (linha < (maxLin-1) && coluna!=0 && ((creLab.parede[linha+1][coluna-1])!=1)){ //checa S0
			acoes[5] = 1;
		} else {
			acoes[5] = -1;
		}
		if (coluna!=0 && (creLab.parede[linha][coluna-1])!=1 ){ //checa O
			acoes[6] = 1;
		} else {
			acoes[6] = -1;
		}
		if (linha!=0 && coluna!=0 && (creLab.parede[linha-1][coluna-1])!=1 ){ //checa N0
			acoes[7] = 1;
		} else {
			acoes[7] = -1;
		}
        return acoes;
    }

    /*
     * Retorna true quando estado atual = estado objetivo, caso contrario retorna falso
     */
    protected boolean testeObjetivo() {
        //@todo fazer o teste - OK!
        return (estAtu[0] == estObj[0] && estAtu[1] == estObj[1]);
    }

    protected void calcularHeuristica(boolean manh) {
        int row, col;
        
        // Calcula a distância de Manhattan para cada celula e atualiza a heuristica
        if (manh) {
            for (row = 0; row <maxLin; row++){
                for (col = 0; col < maxCol; col++) {
                    heuristica[row][col]=Math.abs(row-estObj[0])+Math.abs(col-estObj[1]);
                }
            }                
        
        } else {
            for (row = 0; row <maxLin; row++){
                for (col = 0; col < maxCol; col++) {
                    heuristica[row][col]=0;
                }
            }
        }
    }

    // Retorna o custo estimado de uma posicao
    protected float getValorHeuristico(int[] posicao){
        int linha = posicao[0];
        int coluna = posicao[1];
        return heuristica[linha][coluna];
    }
    
    protected void updateValorHeuristico(int[] posicao, float valor){
        heuristica[posicao[0]][posicao[1]] = valor;
    }

    void imprimirMatrizHeuristica() {
        for (int i = 0; i < heuristica.length; i++) {
            for (int j = 0; j < heuristica[i].length; j++) {
                System.out.print(heuristica[i][j] + "  ");
            }
            System.out.println();
        }
    System.out.println();
    }

    void imprimirPosFrutas() {
        Fruta [][] posFrutas = creLab.getPosFrutas();
        
        for (int i = 0; i < posFrutas.length; i++) {
            for (int j = 0; j < posFrutas[i].length; j++) {
                System.out.print(posFrutas[i][j] + "  ");
            }
            System.out.println();
        }    
    }
}
