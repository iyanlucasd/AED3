import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

public class Pergunta implements registro {
    private int idPerguntas;
    private int idUsuario;
    private Long criacao;
    private Short nota;
    protected String pergunta;
    private boolean ativa;
    Date tempo = new Date();

    public Pergunta() {
        this.idPerguntas = -1;
        this.idUsuario = -1;
        this.criacao = tempo.getTime();
        this.nota = 0;
        this.pergunta = "";
        this.ativa = true;
    }

    public Pergunta(int id, String pergunta) {
        this.idPerguntas = -1;
        this.idUsuario = id;
        this.criacao = tempo.getTime();
        this.nota = 0;
        this.pergunta = pergunta;
        this.ativa = true;
    }

    @Override
    public void setID(int n) {
        this.idPerguntas = n;
    }

    @Override
    public int getID() {
        return this.idPerguntas;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    @Override
    public String getHash() {
        
        return Integer.toString(idUsuario);
    }

    @Override
    public String toString() {
        return this.idPerguntas + ";" + this.idUsuario + ";" + this.criacao + ";" + this.nota + ";" + this.pergunta
                + ";" + this.ativa + ";";
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
        DataOutputStream DAOS = new DataOutputStream(BAOS);
        DAOS.writeInt(this.idPerguntas);
        DAOS.writeInt(this.idUsuario);
        DAOS.writeLong(this.criacao);
        DAOS.writeShort(this.nota);
        DAOS.writeUTF(this.pergunta);
        DAOS.writeBoolean(this.ativa);
        return BAOS.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream BAIS = new ByteArrayInputStream(ba);
        DataInputStream DAIS = new DataInputStream(BAIS);
        this.idPerguntas = DAIS.readInt();
        this.idUsuario = DAIS.readInt();
        this.criacao = DAIS.readLong();
        this.nota = DAIS.readShort();
        this.pergunta = DAIS.readUTF();
        this.ativa = DAIS.readBoolean();

    }
}
