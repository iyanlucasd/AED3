import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileAlreadyExistsException;
import java.util.logging.Level;

public class CRUD<T extends registro> {
    protected int countID = 0;
    protected T bar;
    protected Livro foobar = new Livro();
    protected String file_payh;

    RandomAccessFile arq;

    CRUD(Constructor foo, String file_name) {
        try {
            this.bar = (T) foo.newInstance();
            this.file_payh = file_name;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public int create(T objeto) throws SecurityException, IOException {
        countID++;
        Log my_log = new Log("dados/log.txt");
        my_log.logger.setLevel(Level.WARNING);

        byte[] ba = objeto.toByteArray();
        int tam = ba.length;
        boolean flag = true;

        try {
            arq = new RandomAccessFile("dados/livros.db", "rw");

            arq.writeBoolean(flag);
            arq.write(tam);
            arq.write(ba);

            arq.close();

        } catch (FileNotFoundException FNFE) {
            FNFE.printStackTrace();
            my_log.logger.severe("Arquivo não encontrado");

        } catch (FileAlreadyExistsException FAEE) {
            FAEE.printStackTrace();
            my_log.logger.severe("Arquivo já existe");
        } catch (Exception e) {
            my_log.logger.warning(e.toString());
        }
        return countID;
    }

    public Livro read(int id) throws SecurityException, IOException {
        Log my_log = new Log("dados/log.txt");
        my_log.logger.setLevel(Level.WARNING);

        int key = 0;
        int tam = 0;
        boolean flag = false;
        byte[] ba;

        try {
            arq = new RandomAccessFile("dados/livros.db", "rw");
            arq.seek(0);
            flag = arq.readBoolean();
            tam = arq.readInt();
            ba = new byte[tam];
            arq.read(ba);
            foobar.fromByteArray(ba);
            key = foobar.getID();

            arq.close();

        } catch (FileNotFoundException FNFE) {
            FNFE.printStackTrace();
            my_log.logger.severe("Arquivo não encontrado");

        } catch (FileAlreadyExistsException FAEE) {
            FAEE.printStackTrace();
            my_log.logger.severe("Arquivo já existe");
        } catch (Exception e) {
            my_log.logger.warning(e.toString());
        }

        return foobar;
    }

    public void update(T bar) {
        //
    }

    public void delete(int id3) {
        //
    }
}
