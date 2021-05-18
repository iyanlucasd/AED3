import java.io.IOException;

public interface registro {
    public int getID();

    public void setID(int n);
    
    public String getHash();

    public byte[] toByteArray() throws IOException;

    public void fromByteArray(byte[] ba) throws IOException;

    
}
