import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileAlreadyExistsException;
import java.text.Normalizer;
import java.util.Objects;

import aed3.ArvoreBMais_ChaveComposta_Int_Int;
import aed3.HashExtensivel;
import aed3.ListaInvertida;

class CRUD_usuarios extends CRUD {
    protected int countID = 0; // contador de IDs
    protected int countReg = 0; // contador de registos
    protected String file_path; // String pra caminho personalizado

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
    CRUD_usuarios(String file_name) {
        try {
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
    public int CREATE(Usuario foobar) throws SecurityException, IOException {
        countID++; // Cria um id que ainda não foi usado
        return CREATE(foobar, countID);
    }

    /**
     * Create genérico
     * 
     * @return id
     * @throws SecurityException --> LOG
     * @throws IOException       --> LOG && RAF
     */
    public int CREATE(Usuario foobar, int id) throws SecurityException, IOException {

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
    public Usuario READ(String email) throws Exception {
        /**
         * ! declaração para busca
         */

        int tam = 0; // Tamanho do objeto
        /**
         * foobar -> objeto feito para capturar o byte array do arquivo
         */
        byte[] ba = new byte[0]; // byte array de tamanho 0 pra ser alocado dinamicamente
        Usuario foobar = new Usuario();
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
    public void UPDATE(Usuario foobar) throws SecurityException, IOException {

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

class CRUD_perguntas extends CRUD {
    protected int countID = 0; // contador de IDs
    protected int countReg = 0; // contador de registos

    protected String file_path; // String pra caminho personalizado
    protected Long ponteiro;

    ponteiroArvore jorge;
    ArvoreBMais_ChaveComposta_Int_Int arvore;
    HashExtensivel<ponteiroArvore> he;
    RandomAccessFile arq; // RAF para acessar em todos os métodos
    RandomAccessFile index; // RAF para acessar o Index
    ListaInvertida lista;

    /**
     * Construtor genérico
     *
     * @param foo       Construtor genérico de objeto passado pela main (6)
     * @param file_name String com o caminho
     * @throws IOException
     */
    CRUD_perguntas(String file_name) throws IOException {

        try {

            this.file_path = "dados/" + file_name + ".db"; // set file_path
            he = new HashExtensivel<>(ponteiroArvore.class.getConstructor(), 4, "dados/" + file_name + ".hash_d.db",
                    "dados/" + file_name + ".hash_c.db");
            arvore = new ArvoreBMais_ChaveComposta_Int_Int(5, "dados/arvore.db");
            this.lista = new ListaInvertida(10, "dados/dicionario", "dados/bloco");
            arq = new RandomAccessFile(file_path, "rw");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERRO NO CONSTRUTOR");
        }

    }

    public int CREATE(Pergunta foobar) throws SecurityException, IOException {
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
    public int CREATE(Pergunta foobar, int id) throws SecurityException, IOException {

        /**
         * ! configurações pré gravação
         */

        foobar.setIdPerguntas(id); // Aloca o ID no objeto
        byte[] ba = foobar.toByteArray(); // Cria um byte array do objeto genérico
        int tam = ba.length;
        long pos = -1;
        String palavra = removerAcentos(foobar.getPalavraChave().toLowerCase());
        String[] split = palavra.split(";");

        // -----------------------fim config-------------------------------

        try {

            arq.seek(arq.length()); // ir para o EOF
            pos = arq.getFilePointer(); // guarda a posição
            arq.writeInt(tam); // Escreve o tamanho do objeto
            arq.write(ba); // Escreve o objeto em si
            System.out.println("id atual = " + countID);
            he.create(new ponteiroArvore(foobar.getIdPerguntas(), pos));
            arvore.create(foobar.getIdUsuario(), foobar.getIdPerguntas());
            for (int i = 0; i < split.length; i++) {
                if (!(split[i].isEmpty())) {
                    lista.create(split[i], id);
                }
            }

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
    public Pergunta[] READ(int id) throws Exception {
        /**
         * ! declaração para busca
         */

        int tam = 0; // Tamanho do objeto
        /**
         * foobar -> objeto feito para capturar o byte array do arquivo
         */
        Pergunta foobar = new Pergunta();
        int[] array = arvore.read(id);

        byte[] ba = new byte[0]; // byte array de tamanho 0 pra ser alocado dinamicamente
        Pergunta[] socorro = new Pergunta[array.length];

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

        return socorro;
    }

    /**
     * 
     * @param pergunta
     * @return array contendo as perguntas com as palavras chaves
     * @throws Exception
     */
    public Pergunta[] READ_Palavra(String pergunta) throws Exception {
        /**
         * ! declaração para busca
         */

        int tam = 0; // Tamanho do objeto
        /**
         * foobar -> objeto feito para capturar o byte array do arquivo
         * 
         */

        Pergunta foobar = new Pergunta();
        String palavra = removerAcentos(pergunta.toLowerCase());

        int[] array = lista.read(palavra);

        byte[] ba = new byte[0]; // byte array de tamanho 0 pra ser alocado dinamicamente
        Pergunta[] socorro = new Pergunta[array.length];

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

        return socorro;
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
    public void UPDATE(Pergunta foobar) throws SecurityException, IOException {

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
                if (foobar.isAtiva()) {
                    String palavra = removerAcentos(foobar.getPalavraChave().toLowerCase());
                    String[] split = palavra.split(";");

                    for (int i = 0; i < split.length; i++) {
                        if (!(split[i].isEmpty())) {
                            lista.create(split[i], foobar.getIdPerguntas());
                        }
                    }
                }
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

    /**
     * Deleta os pares de palavras chave da lista inversa
     * 
     * @param foobar
     */
    public void DELETE_palavra(Pergunta foobar) {
        try {
            String palavra = removerAcentos(foobar.getPalavraChave().toLowerCase());
            String[] split = palavra.split(";");
            for (int i = 0; i < split.length; i++) {
                lista.delete(split[i], foobar.getIdPerguntas());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("ERRO NO DELETE_PALAVRA || CRUD FILHOS");
            e.printStackTrace();

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

    /**
     * Remove acento e unicode transformando puro ascii
     * 
     * @param str
     * @return string sem acento
     */
    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
}

// ---------------CRUD RESPOSTAS--------------------
class CRUD_respostas extends CRUD {
    protected int countID = 0; // contador de IDs
    protected int countReg = 0; // contador de registos

    protected String file_path; // String pra caminho personalizado
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
     * @throws IOException
     */
    CRUD_respostas(String file_name) throws IOException {

        try {

            this.file_path = "dados/" + file_name + ".db"; // set file_path
            he = new HashExtensivel<>(ponteiroArvore.class.getConstructor(), 4, "dados/" + file_name + ".hash_d.db",
                    "dados/" + file_name + ".hash_c.db");
            arvore = new ArvoreBMais_ChaveComposta_Int_Int(5, "dados/arvore_resposta.db");
            arq = new RandomAccessFile(file_path, "rw");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERRO NO CONSTRUTOR || CRUD RESPOSTAS");
        }

    }

    public int CREATE(Respostas foobar) throws SecurityException, IOException {
        countID++;
        long pos = -1;
        try {
            arq.seek(arq.length()); // ir para o EOF
            pos = arq.getFilePointer(); // guarda a posição
        } catch (FileNotFoundException FNFE) {
            System.out.println("Arquivo não encontrado");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO CREATE UPPER || CRUD RESPOSTAS");
        }
        return CREATE(foobar, String.valueOf(pos).hashCode());
    }

    /**
     * ´ livro
     * 
     * @throws SecurityException --> LOG
     * @throws IOException       --> LOG && RAF
     */
    public int CREATE(Respostas foobar, int id) throws SecurityException, IOException {

        /**
         * ! configurações pré gravação
         */

        foobar.setIdResposta(id); // Aloca o ID no objeto
        byte[] ba = foobar.toByteArray(); // Cria um byte array do objeto genérico
        int tam = ba.length;
        long pos = -1;

        // -----------------------fim config-------------------------------

        try {

            arq.seek(arq.length()); // ir para o EOF
            pos = arq.getFilePointer(); // guarda a posição
            arq.writeInt(tam); // Escreve o tamanho do objeto
            arq.write(ba); // Escreve o objeto em si
            he.create(new ponteiroArvore(foobar.getIdResposta(), pos));
            arvore.create(foobar.getIdPerguntas(), foobar.getIdResposta());
            arvore.create(foobar.getIdUsuario(), foobar.getIdResposta());

        } catch (FileNotFoundException FNFE) {
            System.out.println("Arquivo não encontrado");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO CREATE LOWER || CRUD RESPOSTAS");
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
    public Respostas[] READ(int id) throws Exception {
        /**
         * ! declaração para busca
         */

        int tam = 0; // Tamanho do objeto
        /**
         * foobar -> objeto feito para capturar o byte array do arquivo
         */
        Respostas foobar = new Respostas();
        int[] array = arvore.read(id);

        byte[] ba = new byte[0]; // byte array de tamanho 0 pra ser alocado dinamicamente
        Respostas[] socorro = new Respostas[array.length];


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
                    socorro[i] = new Respostas();
                    socorro[i].fromByteArray(ba);
                }
            }
        } catch (FileNotFoundException FNFE) {
            FNFE.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO READ || CRUD RESPOSTAS");
        }

        return socorro;
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
    public void UPDATE(Respostas foobar) throws SecurityException, IOException {

        /**
         * ! declaração para busca
         */

        try {
            jorge = he.read(foobar.getIdResposta());
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
                DELETE(foobar.getIdResposta()); // chama a função de delete para mudar a lápide dos registros com o
                                                 // mesmo id
                CREATE(foobar, foobar.getIdResposta());
            }

        } catch (FileNotFoundException FNFE) {
            FNFE.printStackTrace();

        } catch (FileAlreadyExistsException FAEE) {
            FAEE.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERRO NO UPDATE || CRUD RESPOSTAS");
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
            System.out.println("ERRO NO DELETE || CRUD RESPOSTAS");
        }
    }

    public void DELETE(Respostas foobar) throws SecurityException, IOException {
        try {
            arvore.delete(foobar.getIdPerguntas(), foobar.getIdResposta());
            arvore.delete(foobar.getIdUsuario(), foobar.getIdResposta());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("ERRO NO DELETE || CRUD RESPOSTAS");
        }
    }
}
