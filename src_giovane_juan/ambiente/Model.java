
/**
 *
 * @author tacla Modela o ambiente real 'simulado' composto por um labirinto com
 * paredes e por um agente
 */

package ambiente;
import comuns.CoordenadasGeo;
import comuns.Labirinto;

public class Model implements CoordenadasGeo {
    protected int maxCol; // tamanho do labirinto em X (colunas)
    protected int maxLin; // tamanho do labirinto em Y (linhas)
    protected int pos[]; // pos do agente {linha, coluna)
    
    protected Labirinto lab;   // representacao do labirinto
    protected View view;

    public Model(int linhas, int colunas) {        
        
        if (linhas <= 0) {
            linhas = 9;
        }
        if (colunas <= 0) {
            colunas = 9;
        }
        this.maxCol = colunas;
        this.maxLin = linhas;
        
        // instancia o labirinto
        lab = new Labirinto(linhas, colunas);        

        lab.porParedeHorizontal(0, 7, 0);
        lab.porParedeHorizontal(0, 1, 1);
        lab.porParedeVertical(2, 3, 0);
        lab.porParedeHorizontal(3, 5, 2);
        lab.porParedeHorizontal(3, 6, 3);
        lab.porParedeHorizontal(2, 2, 5);
        lab.porParedeHorizontal(5, 7, 5);
        lab.porParedeHorizontal(4, 7, 6);
        lab.porParedeHorizontal(4, 7, 7);
        lab.porParedeHorizontal(2, 2, 8);
        lab.porParedeVertical(6, 8, 1);
        

        // instancia a visualizacao do ambiente associando-a ao model
        view = new View(this);
    }
    
    public void desenhar() {
        view.desenhar();
    }
    
    public int[] lerPos() {
        return pos;
    }

    /**
     * @param coord int de 0 a 7 iniciando por N no sentido horário
     */
    public void ir(int coord) {
        int lin = this.pos[0];
        int col = this.pos[1];
        switch (coord) {
            case N:
                lin--;
                break;
            case NE:
                col++;
                lin--;
                break;
            case L:
                col++;
                break;
            case SE:
                col++;
                lin++;
                break;
            case S:
                lin++;
                break;
            case SO:
                col--;
                lin++;
                break;
            case O:
                col--;
                break;
            case NO:
                col--;
                lin--;
                break;
        }
        // verifica se está fora do grid
        if (col < 0 || col >= maxCol || lin < 0 || lin >= maxLin) {
            lin = pos[0];
            col = pos[1];  // fica na posicao atual
            
        }
        // verifica se bateu em algum obstaculo
        if (lab.parede[lin][col] == 1) {
            lin = pos[0];
            col = pos[1];  // fica na posicao atual
        }

        // atribui nova posicao
        this.pos[0] = lin;
        this.pos[1] = col;
    }
    
    public void setPos(int[] pos) {
        this.pos = pos;
    }

    // Sorteia as frutas no lab e deleta as frutas da posicao inicial e final
    public void sortearFrutas(){
        lab.sortFrutas();
        lab.delFrutaInPos(pos);
    }

    public Labirinto getLab() {
        return lab;
    }

    
}