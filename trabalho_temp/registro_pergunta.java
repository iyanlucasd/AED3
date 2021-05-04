import java.io.IOException;

public interface registro_pergunta {
    public int getIdPerguntas();

    public void setIdPerguntas(int n);
    
    public void setIdUsuario(int idUsuario);

    public int getIdUsuario();

    public boolean isAtiva();

    public void setAtiva(boolean ativa);

    public byte[] toByteArray() throws IOException;

    public void fromByteArray(byte[] ba) throws IOException, CloneNotSupportedException, Exception;
}
