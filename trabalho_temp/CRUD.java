
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Objects;

import aed3.HashExtensivel;

public class CRUD<T extends registro> {
    protected int countID = 0; // contador de IDs
    protected int countReg = 0; // contador de registos
    protected T bar; // variável de tipo genérico
    protected String file_path; // String pra caminho personalizado
    protected Constructor foo; // Constructor para inicializar T fora do "construtor"
    protected Long ponteiro;

    ponteiroHash jorge;
    HashExtensivel<ponteiroHash> he;
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
            this.file_path = "dados/" + file_name + ".db"; // set file_path
            he = new HashExtensivel<>(ponteiroHash.class.getConstructor(), 4, "dados/" + file_name + ".hash_d.db",
                    "dados/" + file_name + ".hash_c.db");
            arq = new RandomAccessFile(file_path, "rw");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERRO NO CONSTRUTOR");
        }

    }
    /**
     * 
     * @param foobar
     * @return
     * @throws SecurityException
     * @throws IOException
     */
    public int CREATE(T foobar) throws SecurityException, IOException {
        countID++; // Cria um id que ainda não foi usado
        return CREATE(foobar, countID);
    }

    /**
     * Create genérico
     * @return id
     * @throws SecurityException --> LOG
     * @throws IOException       --> LOG && RAF
     */
    public int CREATE(T foobar, int id) throws SecurityException, IOException {

        /**
         * ! configurações pré gravação
         */
        System.out.println(foobar);

        foobar.setID(id); // Aloca o ID no objeto
        byte[] ba = foobar.toByteArray(); // Cria um byte array do objeto genérico
        int tam = ba.length;
        long pos = -1;
        // -----------------------fim config-------------------------------

        try {

            arq.seek(arq.length()); // ir para o EOF
            pos = arq.getFilePointer(); // guarda a posição
            arq.writeInt(tam); // Escreve o tamanho do objeto
            arq.write(ba); // Escreve o objeto em si

            he.create(new ponteiroHash(foobar.getHash(), pos));

        } catch (FileNotFoundException FNFE) {
            System.out.println("Arquivo não encontrado");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO CREATE");
        }

        return id;

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
    public T READ(String email) throws Exception {
        /**
         * ! declaração para busca
         */

        int tam = 0; // Tamanho do objeto
        /**
         * foobar -> objeto feito para capturar o byte array do arquivo
         */
        T foobar = (T) foo.newInstance();
        byte[] ba = new byte[0]; // byte array de tamanho 0 pra ser alocado dinamicamente

        // -----------------------fim log-------------------------------

        try {
            // he.print();

            jorge = he.read(email.hashCode());
            if (Objects.isNull(jorge)) {
                foobar = null;
            } else {
                arq.seek(jorge.getPos());
                tam = arq.readInt();
                ba = new byte[tam];
                arq.read(ba);
                foobar.fromByteArray(ba);
            }

        } catch (FileNotFoundException FNFE) {
            FNFE.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO READ");
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
    public void UPDATE(T foobar) throws SecurityException, IOException {

        /**
         * ! declaração para busca
         */

        try {
            jorge = he.read(foobar.getHash().hashCode());
            long pos = jorge.getPos();

            arq.seek(pos);
            int tam = arq.readInt();

            if (tam >= foobar.toByteArray().length) { // escreve o novo número de registros escritos no BD
                arq.seek(pos); // ir para o EOF
                arq.writeInt(tam); // Escreve o tamanho do objeto
                arq.write(foobar.toByteArray());
                System.out.println("Tamanho menor do que o registro");
            } else {
                // Cria um novo registro no final com o id personalizado
                DELETE(foobar.getHash()); // chama a função de delete para mudar a lápide dos registros com o mesmo id
                CREATE(foobar, foobar.getID());
            }

        } catch (FileNotFoundException FNFE) {
            FNFE.printStackTrace();

        } catch (FileAlreadyExistsException FAEE) {
            FAEE.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERRO NO UPDATE");
        }

    }

    // -----------------------fim update-------------------------------

    /**
     *
     * @param id
     * @throws SecurityException
     * @throws IOException
     */
    public void DELETE(String email) throws SecurityException, IOException {
        try {
            he.delete(email.hashCode());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("ERRO NO DELETE");
        }
    }
}
