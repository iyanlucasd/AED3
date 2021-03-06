import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Usuario implements registro{
    
    private int idUsuario;
    private String nome;
    private String email;
    private String senha;

    public Usuario(){
        this.idUsuario = -1;
        this.nome = "";
        this.email = "";
        this.senha = "";
    }
    public Usuario(int id, String nome, String email, String senha){
        this.idUsuario = id;
        this.nome = nome;
        this.email =  email;
        this.senha = senha;
    }
    
    @Override
    public void setID(int n) {
        this.idUsuario = n;
    }

    @Override
    public int getID() {
        return this.idUsuario;
    }

    @Override
    public String getHash() {
        return this.email;
    }

    public void setEmail(String s) {
        this.email = s;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getSenha() {
        return senha;
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.idUsuario +";"+this.nome+";"+this.email+";"+this.senha+";";
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
        DataOutputStream DAOS = new DataOutputStream(BAOS);
        DAOS.writeInt(getID());
        DAOS.writeUTF(this.nome);
        DAOS.writeUTF(this.email);
        DAOS.writeUTF(this.senha);
        return BAOS.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream BAIS = new ByteArrayInputStream(ba);
        DataInputStream DAIS = new DataInputStream(BAIS);
        setID(DAIS.readInt());
        this.nome = DAIS.readUTF();
        this.email = DAIS.readUTF();
        this.senha = DAIS.readUTF();

    }
}
