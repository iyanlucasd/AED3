import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pergunta implements registro_pergunta {
    private int idPerguntas;
    private int idUsuario;
    private String Usuario;
    private Long criacao;
    private Short nota;
    protected String pergunta;
    private String palavraChave;
    private boolean ativa;
    Date tempo = new Date();

    public Pergunta() {
        this.idPerguntas = -1;
        this.idUsuario = -1;
        this.criacao = tempo.getTime();
        this.nota = 0;
        this.pergunta = "";
        this.palavraChave = "";
        this.ativa = true;
    }

    public Pergunta(int id, String pergunta, String Usuario) {
        this.idPerguntas = -1;
        this.idUsuario = id;
        this.Usuario = Usuario;
        this.criacao = tempo.getTime();
        this.nota = 0;
        this.pergunta = pergunta;
        this.palavraChave = "";
        this.ativa = true;
    }

    public Pergunta(int id, String pergunta, String Usuario, String palavra) {
        this.idPerguntas = -1;
        this.idUsuario = id;
        this.Usuario = Usuario;
        this.criacao = tempo.getTime();
        this.nota = 0;
        this.pergunta = pergunta;
        this.palavraChave = palavra;
        this.ativa = true;
    }

    @Override
    public void setIdPerguntas(int n) {
        this.idPerguntas = n;
    }

    @Override
    public int getIdPerguntas() {
        return this.idPerguntas;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setNota(Short nota) {
        this.nota = nota;
    }

    public Short getNota() {
        return nota;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public String getPalavraChave() {
        return palavraChave;
    }

    public void setPalavraChave(String palavraChave) {
        this.palavraChave = palavraChave;
    }

    @Override
    public String toString() {
        String string;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        if (ativa) {
            string = "\nCriada em " + dateFormat.format(this.criacao) + " por " + this.Usuario
                    + "\n+--------------------------------------------------------------------------------+\n"
                    + this.pergunta
                    + "\n+--------------------------------------------------------------------------------+\n"
                    + "Palavras Chaves: " + this.palavraChave + "\n" + "Nota: " + this.nota;

        } else {
            string = "(Arquivada)\n" + "\nCriada em " + dateFormat.format(this.criacao) + " por " + this.Usuario
                    + "\n+--------------------------------------------------------------------------------+\n"
                    + this.pergunta
                    + "\n+--------------------------------------------------------------------------------+\n" + "\n"
                    + "Palavras Chaves: " + this.palavraChave + "\n" + "Nota: " + this.nota;
        }

        return string + "\n";
    }

    public void toPrint() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(dateFormat.format(this.tempo));
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
        DataOutputStream DAOS = new DataOutputStream(BAOS);
        DAOS.writeInt(this.idPerguntas);
        DAOS.writeInt(this.idUsuario);
        DAOS.writeUTF(this.Usuario);
        DAOS.writeLong(this.criacao);
        DAOS.writeShort(this.nota);
        DAOS.writeUTF(this.pergunta);
        DAOS.writeUTF(this.palavraChave);
        DAOS.writeBoolean(this.ativa);
        return BAOS.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream BAIS = new ByteArrayInputStream(ba);
        DataInputStream DAIS = new DataInputStream(BAIS);
        this.idPerguntas = DAIS.readInt();
        this.idUsuario = DAIS.readInt();
        this.Usuario = DAIS.readUTF();
        this.criacao = DAIS.readLong();
        this.nota = DAIS.readShort();
        this.pergunta = DAIS.readUTF();
        this.palavraChave = DAIS.readUTF();
        this.ativa = DAIS.readBoolean();

    }
}
