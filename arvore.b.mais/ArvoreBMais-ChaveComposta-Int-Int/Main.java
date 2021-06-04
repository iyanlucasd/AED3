import java.util.Scanner;
import java.io.File;
import aed3.ArvoreBMais_ChaveComposta_Int_Int;

public class Main {

  // Método principal apenas para testes
  public static void main(String[] args) {

    ArvoreBMais_ChaveComposta_Int_Int arvore;
    Scanner console = new Scanner(System.in);

    try {
      File d = new File("dados");
      if (!d.exists())
        d.mkdir();
      arvore = new ArvoreBMais_ChaveComposta_Int_Int(5, "dados/arvore.db");

      int opcao;
      do {
        System.out.println("\n\n-------------------------------");
        System.out.println("              MENU");
        System.out.println("-------------------------------");
        System.out.println("1 - Inserir");
        System.out.println("2 - Buscar");
        System.out.println("3 - Excluir");
        System.out.println("4 - Imprimir");
        System.out.println("0 - Sair");
        try {
          opcao = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
          opcao = -1;
        }

        switch (opcao) {
        case 1: {
          System.out.println("\nINCLUSÃO");
          System.out.print("Chave: ");
          int chave = Integer.valueOf(console.nextLine());
          System.out.print("Dado: ");
          int dado = Integer.valueOf(console.nextLine());
          arvore.create(chave, dado);
          arvore.print();
        }
          break;
        case 2: {
          System.out.println("\nBUSCA");
          System.out.print("Chave: ");
          int chave = Integer.valueOf(console.nextLine());
          int dados[] = arvore.read(chave);   
          System.out.print("Dados: ");
          for(int i=0; i<dados.length; i++)       
            System.out.print(dados[i]+" ");
        }
          break;
        case 3: {
          System.out.println("\nEXCLUSÃO");
          System.out.print("Chave: ");
          int chave = Integer.valueOf(console.nextLine());
          System.out.print("Dado: ");
          int dado = Integer.valueOf(console.nextLine());
          arvore.delete(chave, dado);
          arvore.print();
        }
          break;
        case 4: {
          arvore.print();
        }
          break;
        case 0:
          break;
        default:
          System.out.println("Opção inválida");
        }
      } while (opcao != 0);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}