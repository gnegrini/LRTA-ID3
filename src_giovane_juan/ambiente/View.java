package ambiente;

/**
 *
 * @author tacla Desenha o ambiente (o que está representado no Model) em
 * formato texto
 */
class View {
    private final Model model;
    protected View(Model m) {
        this.model = m;
    }

    protected void desenhar() {
        for (int lin = 0; lin < model.maxLin; lin++) {
            for (int col = 0; col < model.maxCol; col++) {
                System.out.print("+---");
            }
            System.out.print("+\n");
            for (int col = 0; col < model.maxCol; col++) {
                if (model.lab.parede[lin][col] == 1) {
                    System.out.print("|XXX");  // desenha parede
                } else if (model.pos[1] == col && model.pos[0] == lin) {
                    System.out.print("| A ");  // desenha agente
                } else {
                    System.out.print("|   ");  // posicao vazia
                }
            }
            System.out.print("|");
            if (lin == (model.maxLin - 1)) {
                System.out.print("\n");
                for (int x = 0; x < model.maxCol; x++) {
                    System.out.print("+---");
                }
                System.out.println("+\n");
            }
            System.out.print("\n");
        }
    }
}
