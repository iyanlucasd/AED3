
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
// -----------------------fim Includes-------------------------------

/**
 * CRUD Indexada
 *
 * @author Iyan Lucas Duarte Marques;
 * @version 2.0
 */

public class CRUD<T extends registro> {
    protected int countID = 0; // contador de IDs
    protected int countReg = 0; // contador de registos
    protected T bar; // variável de tipo genérico
    protected String file_path; // String pra caminho personalizado
    protected Constructor foo; // Constructor para inicializar T fora do "construtor"
    protected Map<Integer, Long> positionArray = new HashMap<Integer, Long>(); // Array de index (posição de ponteiro de
                                                                               // arquivo)

    RandomAccessFile arq; // RAF para acessar em todos os métodos
    RandomAccessFile index; // RAF para acessar o Index

    /**
     * Construtor genérico
     *
     * @param foo       Construtor genérico de objeto passado pela main (6)
     * @param file_name String com o caminho
     */
    CRUD(Constructor foo, String file_name) {
        try {
            this.foo = foo; // Alocando o construtor
            this.bar = (T) foo.newInstance(); // alocando o tipo pra classe genérica e criando objeto
            this.file_path = file_name; // set file_path
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void createIndex(int id) throws SecurityException, IOException {
        countReg++; // Aumenta um tipo de registro (é diferente do CountID)
        Log my_log = new Log("dados/log.txt"); // Criar o Log e definir o local do arquivo
        my_log.logger.setLevel(Level.WARNING); // Só registra warning pra cima
        // -----------------------fim log-------------------------------
        new File("dados/index.db").delete();
        try {
            index = new RandomAccessFile("dados/index.db", "rw");
            index.seek(0);
            index.writeInt(countReg);
            index.seek(index.length()); // escreve o novo número de registros escritos no BD
            index.writeInt(id);
            index.writeLong(positionArray.get(id));

        } catch (FileNotFoundException FNFE) {
            my_log.logger.severe("Arquivo não encontrado");

        } catch (Exception e) {
            my_log.logger.warning(e.toString());
        }
    }

    public long catchIndex(int id) throws SecurityException, IOException {
        Log my_log = new Log("dados/log.txt"); // Criar o Log e definir o local do arquivo
        my_log.logger.setLevel(Level.WARNING); // Só registra warning pra cima
        // -----------------------fim log-------------------------------
        long pos = 0;
        int idIndex = 0;
        boolean sideFlag = false;
        int numReg = 0;
        try {

            index = new RandomAccessFile("dados/index.db", "rw");
            numReg = index.readInt();
            index.seek(0);
            for (int i = 1; i <= 3; i++) {
                idIndex = index.readInt();
                System.out.println("id atual = " + idIndex + " id do obj = " +id);
                pos = index.readLong();
                if (idIndex == id) {
                    // i = numReg + 1;
                    sideFlag = true;
                }
            }

        } catch (FileNotFoundException FNFE) {
            my_log.logger.severe("Arquivo não encontrado");

        } catch (Exception e) {
            my_log.logger.warning(e.toString());
        }
        if (sideFlag == false)
            pos = -1;

        return pos;
    }

    public boolean updateIndex(int id, long pointer) throws SecurityException, IOException {
        Log my_log = new Log("dados/log.txt"); // Criar o Log e definir o local do arquivo
        my_log.logger.setLevel(Level.WARNING); // Só registra warning pra cima
        // -----------------------fim log-------------------------------
        int idIndex = 0;
        boolean flag = false;
        int numReg = 0;
        try {

            index = new RandomAccessFile("dados/index.db", "rw");
            numReg = index.readInt();
            for (int i = 1; i <= numReg; i++) {
                idIndex = index.readInt();
                if (idIndex == id) {
                    index.writeLong(pointer);
                    flag = true;
                    i = numReg + 1;
                } else {
                    index.skipBytes(8);
                }
            }

        } catch (FileNotFoundException FNFE) {
            my_log.logger.severe("Arquivo não encontrado");

        } catch (Exception e) {
            my_log.logger.warning(e.toString());
        }

        return flag;
    }

    public boolean deleteIndex(int id) throws SecurityException, IOException {
        Log my_log = new Log("dados/log.txt"); // Criar o Log e definir o local do arquivo
        my_log.logger.setLevel(Level.WARNING); // Só registra warning pra cima
        // -----------------------fim log-------------------------------
        int idIndex = 0;
        boolean flag = false;
        int numReg = 0;
        try {

            index = new RandomAccessFile("dados/index.db", "rw");
            numReg = index.readInt();
            for (int i = 1; i <= numReg; i++) {
                idIndex = index.readInt();
                if (idIndex == id) {
                    index.writeLong(-1);
                    flag = true;
                    i = numReg + 1;
                } else {
                    index.skipBytes(8);
                }
            }

        } catch (FileNotFoundException FNFE) {
            my_log.logger.severe("Arquivo não encontrado");

        } catch (Exception e) {
            my_log.logger.warning(e.toString());
        }

        return flag;
    }

    /**
     * Chamada para função create principal, serve pra eu definir o id passado pelo
     * teste Isso me permite adicionar um id personalizado dentro do Update
     * 
     * @param foobar
     * @return Método com o id auto gerado
     * @throws SecurityException
     * @throws IOException
     */
    public int create(T foobar) throws SecurityException, IOException {
        countID++; // Cria um id que ainda não foi usado
        return create(foobar, countID);
    }

    /**
     * ´ livro
     * 
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

        foobar.setID(id); // Aloca o ID no objeto
        byte[] ba = foobar.toByteArray(); // Cria um byte array do objeto genérico
        int tam = ba.length;
        long pos = -1;
        // -----------------------fim config-------------------------------

        try {
            arq = new RandomAccessFile("dados/livros.db", "rw");
            arq.seek(arq.length()); // ir para o EOF
            pos = arq.getFilePointer(); // guarda a posição
            arq.writeInt(tam); // Escreve o tamanho do objeto
            arq.write(ba); // Escreve o objeto em si

            arq.close();

        } catch (FileNotFoundException FNFE) {
            my_log.logger.severe("Arquivo não encontrado");

        } catch (FileAlreadyExistsException FAEE) {
            my_log.logger.severe("Arquivo já existe");
        } catch (Exception e) {
            my_log.logger.warning(e.toString());
        }
        positionArray.put(id, pos);
        createIndex(id);
        return countID;

    }
    // -----------------------fim create-------------------------------

    /**
     * *Pesquisa no DB o registro com o ID correspondente
     * 
     * @param id recebe o ID do objeto que quer ler
     * @return retorna o objeto com o ID ! passei as exceptions abaixo pra uma só e
     *         trato ela no LOG
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

        int tam = 0; // Tamanho do objeto
        /**
         * foobar -> objeto feito para capturar o byte array do arquivo
         */
        T foobar = (T) foo.newInstance();

        boolean flag = false; // flag do registro
        byte[] ba = new byte[0]; // byte array de tamanho 0 pra ser alocado dinamicamente

        // -----------------------fim log-------------------------------

        try {
            arq = new RandomAccessFile("dados/livros.db", "rw");
            arq.seek(catchIndex(id));
            tam = arq.readInt();
            ba = new byte[tam];
            arq.read(ba);
            foobar.fromByteArray(ba);

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
        if (!flag) {
            foobar = null;
        }
        return foobar;
    }
    // -----------------------fim READ-------------------------------

    /**
     * *O update é basicamente deletar o registro correspondente e criar um novo
     * 
     * @param foobar objeto que a pessoa quer que atualiza (com as infos já
     *               atualizadas)
     * @throws SecurityException
     * @throws IOException
     */
    public void update(T foobar) throws SecurityException, IOException {

        Log my_log = new Log("dados/log.txt"); // Criar o Log e definir o local do arquivo
        my_log.logger.setLevel(Level.WARNING); // Só registra warning pra cima
        // -----------------------fim log-------------------------------

        /**
         * ! declaração para busca
         */

        int key = foobar.getID();
        long pos = catchIndex(key);
        boolean flag = false;
        // -----------------------fim log-------------------------------

        /**
         * ! mesmo que a READ
         */
        try {
            arq = new RandomAccessFile("dados/livros.db", "rw");
            arq.seek(pos);
            int tam = arq.readInt();

            if (tam >= foobar.toByteArray().length) { // escreve o novo número de registros escritos no BD
                arq.seek(pos); // ir para o EOF
                arq.writeInt(tam); // Escreve o tamanho do objeto
                arq.write(foobar.toByteArray());
                System.out.println("Tamanho menor do que o registro");
            } else {
                // Cria um novo registro no final com o id personalizado
                delete(foobar.getID()); // chama a função de delete para mudar a lápide dos registros com o mesmo id
                create(foobar, foobar.getID());
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

    }

    // -----------------------fim update-------------------------------

    /**
     *
     * @param id
     * @throws SecurityException
     * @throws IOException
     */
    public void delete(int id) throws SecurityException, IOException {
        deleteIndex(id);
    }
}
