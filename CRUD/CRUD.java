
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileAlreadyExistsException;
import java.util.logging.Level;
// -----------------------fim Includes-------------------------------

/**
 * Declaração e começo da classe CRUD Genérica
 *
 * @author Iyan Lucas Duarte Marques;
 * @version 1.1
 */

public class CRUD<T extends registro> {
    protected int countID = 0;                      // contador de IDs
    protected int countReg = 0;                     // contador de IDs
    protected T bar;                                // variável de tipo genérico
    protected String file_path;                     // String pra caminho personalizado
    protected Constructor foo;                      // Constructor para inicializar T fora do "construtor"

    RandomAccessFile arq;                           // RAF para acessar em todos os métodos

    /**
     * Construtor genérico
     *
     * @param foo           Construtor genérico de objeto passado pela main (6)
     * @param file_name     String com o caminho
     */
    CRUD(Constructor foo, String file_name) {
        try {
            this.foo = foo;                         // Alocando o construtor
            this.bar = (T) foo.newInstance();       // alocando o tipo pra classe genérica e criando objeto
            this.file_path = file_name;             // set file_path
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
    /**
     *  Chamada para função create principal, serve pra eu definir o id passado pelo teste
     *  Isso me permite adicionar um id personalizado dentro do Update
     * @param foobar
     * @return Método com o id auto gerado
     * @throws SecurityException
     * @throws IOException
     */
    public int create(T foobar) throws SecurityException, IOException {
        countID++;                                 // Cria um id que ainda não foi usado
        return create(foobar, countID);
    }

    /**´ livro
     * @throws SecurityException --> LOG
     * @throws IOException       --> LOG && RAF
     */
    public int create(T foobar, int id) throws SecurityException, IOException {
        Log my_log = new Log("dados/log.txt");  // Criar o Log e definir o local do arquivo
        my_log.logger.setLevel(Level.WARNING);  // Só registra warning pra cima
        // -----------------------fim log-------------------------------

        /**
         * ! configurações pré gravação
         */

        countReg++;                             // Aumenta um tipo de registro (é diferente do CountID)
        foobar.setID(id);                       // Aloca o ID no objeto
        byte[] ba = foobar.toByteArray();       // Cria um byte array do objeto genérico
        int tam = ba.length;
        boolean flag = true;                    // lápide pra falar que o registro está ativo
        // -----------------------fim config-------------------------------

        try {
            arq = new RandomAccessFile("dados/livros.db", "rw");
            arq.seek(0);
            arq.writeInt(countReg);             // escreve o novo número de registros escritos no BD
            arq.seek(arq.length());             // ir para o EOF
            arq.writeBoolean(flag);             // Escreve a Flag
            arq.writeInt(tam);                  // Escreve o tamanho do objeto
            arq.write(ba);                      // Escreve o objeto em si

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
     * @param id recebe o ID do objeto que quer ler
     * @return retorna o objeto com o ID
     * ! passei as exceptions abaixo pra uma só e trato ela no LOG
     * @throws SecurityException
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public T read(int id) throws Exception {
        Log my_log = new Log("dados/log.txt");  // Criar o Log e definir o local do arquivo
        my_log.logger.setLevel(Level.WARNING);  // Só registra warning pra cima
        // -----------------------fim log-------------------------------

        /**
         * ! declaração para busca
         */

        int key = 0;                            // ID do objeto
        int tam = 0;                            // Tamanho do objeto
        /**
         * foobar -> objeto feito para capturar o byte array do arquivo
         * foobarfoo -> objeto auxiliar feito para capturar do foobar o registro certo
         */
        T foobar = (T) foo.newInstance();
        T foobarfoo = (T) foo.newInstance();

        boolean flag = false;                   // flag do registro

        /**
         * ! Então kutova, eu fiz essa variável pseudo-gambiarra aqui... (sideFlag)
         * ! Mas ela é mais pra ver se não encontrou ninguém, deixar o retorno null
         * ! Visto que no seu teste, pede pro livro ser deletadaS
         */
        boolean sideFlag = false;

        byte[] ba = new byte[0];               // byte array de tamanho 0 pra ser alocado dinamicamente
        // -----------------------fim log-------------------------------

        try {
            arq = new RandomAccessFile("dados/livros.db", "rw");
            arq.seek(0);
            arq.readInt();

            for (int i = 0; i < countReg; i++) {
                flag = arq.readBoolean();       // lê a lápide
                tam = arq.readInt();            // lê o tamanho do registro
                ba = new byte[tam];             // aloca o tamanho no vetor de bytes
                arq.read(ba);                   // enche o vetor de bytes com o registro do objeto
                foobar.fromByteArray(ba);       // seta as variáveis do objeto baseado no registro
                key = foobar.getID();           // pega o ID do objeto

                /**
                 * Verifica se o id é o mesmo
                 * se sim verifica se é um registro válido
                 * passa o conteúdo do objeto principal para o auxiliar
                 * seta a flag pra mostrar que achou algum registro compatível
                 * para o for
                 */
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

    /**
     * *O update é basicamente deletar o registro correspondente e criar um novo
     * @param foobar objeto que a pessoa quer que atualiza (com as infos já atualizadas)
     * @throws SecurityException
     * @throws IOException
     */
    public void update(T foobar) throws SecurityException, IOException {

        Log my_log = new Log("dados/log.txt");  // Criar o Log e definir o local do arquivo
        my_log.logger.setLevel(Level.WARNING);  // Só registra warning pra cima
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

        /**
         * ! mesmo que a READ
         */
        try {
            arq = new RandomAccessFile("dados/livros.db", "rw");
            arq.seek(0);
            arq.readInt();

            for (int i = 0; i < countReg; i++) {
                FileAt = arq.getFilePointer();
                flag = arq.readBoolean();
                tam = arq.readInt();
                ba = new byte[tam];
                arq.read(ba);
                bar.fromByteArray(ba);
                key = bar.getID();
                if (flag == true && key == foobar.getID()) {
                    i = countReg;
                }
            }
            ba = bar.toByteArray();

            if (tam >=  foobar.toByteArray().length) {           // escreve o novo número de registros escritos no BD
                arq.seek(FileAt);                   // ir para o EOF
                arq.writeBoolean(flag);             // Escreve a Flag
                arq.writeInt(tam);                  // Escreve o tamanho do objeto
                arq.write(foobar.toByteArray());
                System.out.println("Tamanho menor do que o registro");
            } else {
                                                    // Cria um novo registro no final com o id personalizado
                delete(foobar.getID());             // chama a função de delete para mudar a lápide dos registros com o mesmo id
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
        Log my_log = new Log("dados/log.txt");  // Criar o Log e definir o local do arquivo
        my_log.logger.setLevel(Level.WARNING);  // Só registra warning pra cima
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

        /**
         * ! mesmo que a READ
         */
        try {
            arq = new RandomAccessFile("dados/livros.db", "rw");
            arq.seek(0);
            arq.readInt();

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
