
/**
 * Includes padão
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileAlreadyExistsException;
import java.util.logging.Level;
// -----------------------fim Includes-------------------------------

/**
 * Declaração e começo da classe CRUD Genérica
 * 
 * @author Iyan Lucas Duarte Marques;
 * @version 0.2
 */

public class CRUD<T extends registro> {
    protected int countID = 0; // contador de IDs
    protected int countReg = 0; // contador de IDs
    protected T bar; // variável de tipo genérico
    protected String file_payh; // String pra caminho personalizado
    protected Constructor foo;
    protected T[] barfoo;

    RandomAccessFile arq;

    /**
     * Construtor genérico
     * 
     * @param foo       Construtor genérico de objeto passado pela main (6)
     * @param file_name String com o caminho
     */
    CRUD(Constructor foo, String file_name) {
        try {
            this.foo = foo;
            this.bar = (T) foo.newInstance(); // alocando o tipo pra classe genérica e criando objeto
            this.file_payh = file_name; // set file_path
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public int create(T foobar) throws SecurityException, IOException {
        countID++;
        return create(foobar, countID);
    }

    /**´ livro
     * @throws SecurityException --> LOG
     * @throws IOException       --> LOG && RAF
     */
    public int create(T foobar, int id) throws SecurityException, IOException {
        Log my_log = new Log("dados/log.txt"); // Criar o Log e definir o local do arquivo
        my_log.logger.setLevel(Level.WARNING); // Só registra warning pra cima
        // -----------------------fim log-------------------------------

        /**
         * ! configurações pré gravação
         */

        countReg++;
        foobar.setID(id);
        byte[] ba = foobar.toByteArray();
        int tam = ba.length;
        boolean flag = true;
        // -----------------------fim config-------------------------------

        try {
            arq = new RandomAccessFile("dados/livros.db", "rw");
            arq.seek(0);
            arq.writeInt(countReg);
            arq.seek(arq.length()); // ir para o EOF
            arq.writeBoolean(flag); // Escreve a Flag
            arq.writeInt(tam); // Escreve o tamanho do objeto
            arq.write(ba); //escreve o objeto em si

            arq.close();

        } catch (FileNotFoundException FNFE) {
            my_log.logger.severe("Arquivo não encontrado");

        } catch (FileAlreadyExistsException FAEE) {
            my_log.logger.severe("Arquivo já existe");
        } catch (Exception e) {
            my_log.logger.warning(e.toString());
        }
        return countID;
    }
    // -----------------------fim create-------------------------------

    /**
     * *Pesquisa no DB o registro com o ID correspondente
     *  TODO pular registros com flag falsa
     * @param id recebe o ID do objeto que quer ler
     * @return retorna o objeto com o ID 
     * @throws SecurityException
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public T read(int id) throws Exception {
        Log my_log = new Log("dados/log.txt"); // Criar o Log e definir o local do arquivo
        my_log.logger.setLevel(Level.WARNING); // Só registra warning pra cima
        // -----------------------fim log-------------------------------

        /**
         * ! declaração para busca
         */

        int key = 0;
        int tam = 0;
        T foobar = (T) foo.newInstance();
        T foobarfoo = (T) foo.newInstance();
        boolean sideFlag = false;
        boolean flag = false;
        byte[] ba = new byte[0];
        // -----------------------fim log-------------------------------

        try {
            arq = new RandomAccessFile("dados/livros.db", "rw");
            arq.seek(0);
            arq.readInt();
            for (int i = 0; i < countReg; i++) {                
                flag = arq.readBoolean();
                tam = arq.readInt();
                ba = new byte[tam];
                arq.read(ba);
                foobar.fromByteArray(ba);
                key = foobar.getID();
                if (key == id && flag == true) {
                    sideFlag = true;
                   foobarfoo = foobar; 
                   i = countReg;
                }
            }

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
        if (!sideFlag) {
            foobarfoo = null;
        }
        return foobarfoo;
    }
    // -----------------------fim READ-------------------------------

    public void update(T foobar) throws SecurityException, IOException {

        delete(foobar.getID());
        create(foobar, foobar.getID());

    }

    public void delete(int id) throws SecurityException, IOException {
        Log my_log = new Log("dados/log.txt"); // Criar o Log e definir o local do arquivo
        my_log.logger.setLevel(Level.WARNING); // Só registra warning pra cima
        // -----------------------fim log-------------------------------

        /**
         * ! declaração para busca
         */

        int key = 0;
        int tam = 0;
        long FileAt = 0;
        boolean flag = false;
        byte[] ba = new byte [0];
        // -----------------------fim log-------------------------------
        // System.out.println(foobar);
        try {
            arq = new RandomAccessFile("dados/livros.db", "rw");
            arq.seek(0);
            arq.readInt();

            // * Lê enquanto não achar o ID
            for (int i = 0; i < countReg; i++) {                
                FileAt = arq.getFilePointer();
                flag = arq.readBoolean();
                tam = arq.readInt();
                ba = new byte[tam];
                arq.read(ba);
                bar.fromByteArray(ba);
                key = bar.getID();
                if (flag == true && key == id) {
                    i = countReg;
                }
            }
            ba = bar.toByteArray();
            arq.seek(FileAt);
            arq.writeBoolean(false);

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
    }
}
