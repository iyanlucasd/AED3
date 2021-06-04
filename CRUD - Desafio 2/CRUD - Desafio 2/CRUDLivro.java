import aed3.CRUD;
import aed3.HashExtensivel;

public class CRUDLivro extends CRUD<Livro> {

  String nomePasta;
  HashExtensivel<ParTituloId> indiceIndireto;

  public CRUDLivro(String nome) throws Exception {
    super(Livro.class.getConstructor(), nome);
    this.nomePasta = nome;
    indiceIndireto = new HashExtensivel<>(ParTituloId.class.getConstructor(), 4,
        this.nomePasta + "/titulo.diretorio.db", this.nomePasta + "/titulo.cestos.db");
  }

  @Override
  public int create(Livro objeto) throws Exception {
    int id = super.create(objeto);
    indiceIndireto.create(new ParTituloId(objeto.getTitulo(), id));
    return id;
  }

  // Usaremos o método read por ID da superclasse
  // Assim, não precisamos implementar algo como:
  // @Override
  // public Livro read(int id) throws Exception {
  // return super.read(id);
  // }

  // Método para leitura por título (específico para Livros)
  public Livro read(String t) throws Exception {
    ParTituloId ptid = indiceIndireto.read(t.hashCode());
    return super.read(ptid.getID());
  }

  // A atualização aqui ficou um pouco mais complicada, porque precisamos do
  // título antigo do livro para alterar o índice indireto. Assim, precisamos,
  // em primeiro lugar, recuperar o título antigo
  @Override
  public boolean update(Livro objetoNovo) throws Exception {
    Livro objetoAntigo = super.read(objetoNovo.getID());
    if (objetoAntigo != null && super.update(objetoNovo)) {
      indiceIndireto.delete(objetoAntigo.getTitulo().hashCode());
      indiceIndireto.create(new ParTituloId(objetoNovo.getTitulo(), objetoNovo.getID()));
      return true;
    }
    return false;
  }

  @Override
  public boolean delete(int id) throws Exception {
    Livro objeto = super.read(id);
    if (objeto != null && super.delete(id)) {
      indiceIndireto.delete(objeto.getTitulo().hashCode());
      return true;
    }
    return false;
  }

}