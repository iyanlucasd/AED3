import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;
import java.text.DecimalFormat;
/**
 * Declaração e começo da classe CRUD Genérica
 * Basicamente as mesmas coisas que a aula do kutova
 * 
 * @author Iyan Lucas Duarte Marques;
 * @version 1.0
 */
class Livro implements registro {
    private int IDlivro;
    protected String titulo;
    protected String autor;
    protected float preco;

    public Livro(int i, String t, String a, float p) {
        this.IDlivro = i;
        this.titulo = t;
        this.autor = a;
        this.preco = p;
    }

    public Livro() {
        this.IDlivro = -1;
        this.titulo = "";
        this.autor = "";
        this.preco = 0F;
    }

    @Override
    public int getID() {
        // TODO Auto-generated method stub
        return IDlivro;
    }

    @Override
    public void setID(int n) {
        // TODO Auto-generated method stub
        this.IDlivro = n;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        return "\nID....: " + this.IDlivro + "\nTitulo: " + this.titulo + "\nAutor: " + this.autor + "\nPreco: "
                + df.format(this.preco);
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
        DataOutputStream DAOS = new DataOutputStream(BAOS);
        DAOS.writeInt(IDlivro);
        DAOS.writeUTF(titulo);
        DAOS.writeUTF(autor);
        DAOS.writeFloat(preco);
        return BAOS.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream BAIS = new ByteArrayInputStream(ba);
        DataInputStream DAIS = new DataInputStream(BAIS);
        IDlivro = DAIS.readInt();
        titulo = DAIS.readUTF();
        autor = DAIS.readUTF();
        preco = DAIS.readFloat();

    }
}