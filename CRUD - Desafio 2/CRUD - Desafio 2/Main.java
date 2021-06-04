import java.io.*;

public class Main {

  // Arquivo declarado fora de main() para ser poder ser usado por outros métodos
  private static CRUDLivro arqLivros;

  public static void main(String[] args) {

    // Livros de exemplo
    Livro l1 = new Livro(-1, "Eu, Robô", "Isaac Asimov", 14.9F);
    Livro l2 = new Livro(-1, "Eu Sou A Lenda", "Richard Matheson", 21.99F);
    Livro l3 = new Livro(-1, "Número Zero", "Umberto Eco", 34.11F);
    int id1, id2, id3;

    try {

      // Apaga os arquivos anteriores
      // Apenas para facilitar os testes (não usar no projeto)
      (new File("dados/livros/dados.db")).delete();
      (new File("dados/livros/id.diretorio.db")).delete();
      (new File("dados/livros/id.cestos.db")).delete();
      (new File("dados/livros/titulo.diretorio.db")).delete();
      (new File("dados/livros/titulo.cestos.db")).delete();

      // Abre (cria) o arquivo de livros
      arqLivros = new CRUDLivro("dados/livros");

      // Insere os três livros
      id1 = arqLivros.create(l1);
      l1.setID(id1);

      id2 = arqLivros.create(l2);
      l2.setID(id2);

      id3 = arqLivros.create(l3);
      l3.setID(id3);

      // Busca por livros
      System.out.println("\n\nBUSCA POR ID: ");
      System.out.println("\nLIVRO 3:" + arqLivros.read(id3));
      System.out.println("\nLIVRO 2:" + arqLivros.read(id2));
      System.out.println("\nLIVRO 1:" + arqLivros.read(id1));

      System.out.println("\n\nBUSCA POR TÍTULO: ");
      System.out.println("\nLIVRO:" + arqLivros.read("Eu, Robô"));
      System.out.println("\nLIVRO:" + arqLivros.read("Número Zero"));
      System.out.println("\nLIVRO:" + arqLivros.read("Eu Sou A Lenda"));

      // Altera um livro para um tamanho maior e exibe o resultado
      System.out.println("\n\nALTERAÇÃO DE AUTOR PARA MAIOR: ");
      l2.autor = "Richard Burton Matheson";
      arqLivros.update(l2);
      System.out.println("\nLIVRO 2:" + arqLivros.read(id2));

      // Altera um livro para um tamanho menor e exibe o resultado
      System.out.println("\n\nALTERAÇÃO DE AUTOR PARA MENOR: ");
      l1.autor = "I. Asimov";
      arqLivros.update(l1);
      System.out.println("\nLIVRO 1:" + arqLivros.read(id1));

      // Excluir um livro e mostra que não existe mais
      System.out.println("\n\nEXCLUSÃO: ");
      arqLivros.delete(id3);
      Livro l = arqLivros.read(id3);
      if (l == null)
        System.out.println("Livro 3 excluído");
      else
        System.out.println(l);

      System.out.println("\n\nNOVA BUSCA POR ID: ");
      System.out.println("\nLIVRO 1:" + arqLivros.read(id1));
      System.out.println("\nLIVRO 2:" + arqLivros.read(id2));
      System.out.println("\nLIVRO 3:" + arqLivros.read(id3));

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}