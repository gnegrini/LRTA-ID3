
package comuns;

public class Labirinto {
    public int parede[][]; // armazena posicao das paredes marcando-as com 1
    private final int maxCol;     // maximo de colunas
    private final int maxLin;     // maximo de linhas
    
    public Labirinto(int maxColunas, int maxLinhas) {
        maxCol = maxColunas;
        maxLin = maxLinhas;
        parede = new int[maxCol][maxLin];
    }
    
   /*
    * Constroi parede horizontal
    * @param ini: coluna inicial entre 0 e número máximo de colunas - 1
    * @param fim: coluna final (> coluna inicial)
    * @param linha: em qual linha por a parede (entre 0 e máx. de linhas - 1)
    */
    public void porParedeHorizontal(int ini, int fim, int linha) {
        if (fim >= ini && ini >= 0 && fim < maxCol && linha >= 0 && linha < maxLin) {
            for (int c = ini; c <= fim; c++) {
                parede[linha][c] = 1;
            }
        }
    }
    /*
    * Constroi parede vertical
    * @param ini: linha inicial entre 0 e  máximo de linhas - 1
    * @param fim: linha final (> linha inicial)
    * @param linha: em qual coluna por a parede (entre 0 e máx. de colunas - 1)
    */
    public void porParedeVertical(int ini, int fim, int coluna) {
        if (fim >= ini && ini >= 0 && fim < maxLin && coluna >= 0 && coluna < maxCol) {
            for (int l = ini; l <= fim; l++) {
                parede[l][coluna] = 1;
            }
        }
    }
    
}
