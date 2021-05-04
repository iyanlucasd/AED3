
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import aed3.ArvoreBMais_ChaveComposta_Int_Int;
import aed3.HashExtensivel;

public class CRUD_perguntas<T extends registro_pergunta> {
    protected int countID = 0; // contador de IDs
    protected int countReg = 0; // contador de registos
    protected T bar; // variável de tipo genérico
    protected String file_path; // String pra caminho personalizado
    protected Constructor foo; // Constructor para inicializar T fora do "construtor"
    protected Long ponteiro;

    ponteiroArvore jorge;
    ArvoreBMais_ChaveComposta_Int_Int arvore;
    HashExtensivel<ponteiroArvore> he;
    RandomAccessFile arq; // RAF para acessar em todos os métodos
    RandomAccessFile index; // RAF para acessar o Index

    /**
     * Construtor genérico
     *
     * @param foo       Construtor genérico de objeto passado pela main (6)
     * @param file_name String com o caminho
     */
    CRUD_perguntas(Constructor foo, String file_name) {
        try {
            this.foo = foo; // Alocando o construtor
            this.bar = (T) foo.newInstance(); // alocando o tipo pra classe genérica e criando objeto
            this.file_path = "dados/" + file_name + ".db"; // set file_path
            he = new HashExtensivel<>(ponteiroArvore.class.getConstructor(), 4, "dados/" + file_name + ".hash_d.db",
                    "dados/" + file_name + ".hash_c.db");
            arq = new RandomAccessFile(file_path, "rw");
            arvore = new ArvoreBMais_ChaveComposta_Int_Int(5, "dados/arvore.db");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERRO NO CONSTRUTOR");
        }

    }

    public int CREATE(T foobar) throws SecurityException, IOException {
        countID++;
        long pos = -1;
        try {
            arq.seek(arq.length()); // ir para o EOF
            pos = arq.getFilePointer(); // guarda a posição
        } catch (FileNotFoundException FNFE) {
            System.out.println("Arquivo não encontrado");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO CREATE UPPER");
        }
        return CREATE(foobar, String.valueOf(pos).hashCode());
    }

    /**
     * ´ livro
     * 
     * @throws SecurityException --> LOG
     * @throws IOException       --> LOG && RAF
     */
    public int CREATE(T foobar, int id) throws SecurityException, IOException {

        /**
         * ! configurações pré gravação
         */

        foobar.setIdPerguntas(id); // Aloca o ID no objeto
        byte[] ba = foobar.toByteArray(); // Cria um byte array do objeto genérico
        int tam = ba.length;
        long pos = -1;
        // -----------------------fim config-------------------------------

        try {

            arq.seek(arq.length()); // ir para o EOF
            pos = arq.getFilePointer(); // guarda a posição
            arq.writeInt(tam); // Escreve o tamanho do objeto
            arq.write(ba); // Escreve o objeto em si
            System.out.println("id atual = " + countID);
            he.create(new ponteiroArvore(foobar.getIdPerguntas(), pos));
            arvore.create(foobar.getIdUsuario(), foobar.getIdPerguntas());

        } catch (FileNotFoundException FNFE) {
            System.out.println("Arquivo não encontrado");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO CREATE LOWER");
        }
        System.out.println(foobar);

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
    public List READ(int id) throws Exception {
        /**
         * ! declaração para busca
         */

        int tam = 0; // Tamanho do objeto
        /**
         * foobar -> objeto feito para capturar o byte array do arquivo
         */
        T foobar = (T) foo.newInstance();
        int[] array = arvore.read(id);

        byte[] ba = new byte[0]; // byte array de tamanho 0 pra ser alocado dinamicamente
        Pergunta[] socorro = new Pergunta[array.length];
        List qux = new ArrayList<Pergunta>();
        // -----------------------fim log-------------------------------
        try {

            for (int i = 0; i < array.length; i++) {
                // 3247453
                jorge = he.read(array[i]);
                if (Objects.isNull(jorge)) {
                    foobar = null;
                } else {
                    arq.seek(jorge.getPos());
                    tam = arq.readInt();
                    ba = new byte[tam];
                    arq.read(ba);
                    foobar.fromByteArray(ba);
                    // System.out.println(foobar);
                    socorro[i] = new Pergunta();
                    socorro[i].fromByteArray(ba);
                }
            }
        } catch (FileNotFoundException FNFE) {
            FNFE.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO READ");
        }
        for (int i = 0; i < socorro.length; i++) {
            // if (socorro[i].isAtiva()) {
                qux.add(socorro[i]);
            // }
        }
        return qux;
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
            jorge = he.read(foobar.getIdPerguntas());
            long pos = jorge.getPos();

            arq.seek(pos);
            int tam = arq.readInt();

            if (tam >= foobar.toByteArray().length) { // escreve o novo número de registros escritos no BD
                arq.seek(pos); // ir para o EOF
                arq.writeInt(tam); // Escreve o tamanho do objeto
                arq.write(foobar.toByteArray());
                // System.out.println("Tamanho menor do que o registro");
            } else {
                // Cria um novo registro no final com o id personalizado
                DELETE(foobar.getIdPerguntas()); // chama a função de delete para mudar a lápide dos registros com o
                                                 // mesmo id
                CREATE(foobar, foobar.getIdPerguntas());
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
    public void DELETE(int id) throws SecurityException, IOException {
        try {
            he.delete(id);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("ERRO NO DELETE");
        }
    }
}
