import aed3.ListaInvertida;

public class teste {
    public static void main(String[] args) {
        try {
            
            ListaInvertida lista = new ListaInvertida(10, "dicionario", "bloco");
    
            lista.create("cenoura;legumes", 2);
            lista.create("batata;legumes", 1);
            lista.create("batata;legumes", 3);
            lista.create("batata;legumes", 4);
            int[] array = lista.read("batata;legumes");
            array[0] =1;
            System.out.println("cheguei");

            for (int i = 0; i < array.length; i++) {

                System.out.println(array[i]);
                
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
