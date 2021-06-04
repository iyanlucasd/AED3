
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;

import aed3.ArvoreBMais_ChaveComposta_Int_Int;

public class MENU {
    CRUD_usuarios usuariosCrud;
    protected Scanner console = new Scanner(System.in);
    protected Usuario jorge;
    ArvoreBMais_ChaveComposta_Int_Int arvore;
    Pergunta qux;
    Pergunta[] lista_perguntas;
    Pergunta[] foo;
    Respostas[] bar;
    CRUD_perguntas perguntaCrud;
    CRUD_respostas respostasCrud;

    public MENU() {
    }

    /**
     * CLEAR no terminal
     */
    public void clearScreen() {
        for (int i = 0; i < 100; ++i)
            System.out.println();
    }

    /**
     * Construtor
     */
    public void menu() {

        try {
            usuariosCrud = new CRUD_usuarios("usuarios");
            File d = new File("dados");
            arvore = new ArvoreBMais_ChaveComposta_Int_Int(5, "dados/arvore.db");
            perguntaCrud = new CRUD_perguntas("Perguntas");
            respostasCrud = new CRUD_respostas("Respostas");
            if (!d.exists())
                d.mkdir();

            int opcao;
            do {
                System.out.println("\n\n-------------------------------");
                System.out.println("              MENU");
                System.out.println("-------------------------------");
                System.out.println("1 - Acesso ao Sistema");
                System.out.println("2 - Novo usuário (primeiro acesso)");
                System.out.println("\n0 - Sair");
                try {
                    opcao = console.nextInt();
                } catch (NumberFormatException e) {
                    opcao = -1;
                }

                switch (opcao) {
                    case 2: {
                        boolean flag = false;
                        do {

                            System.out.println("\nNOVO USUÁRIO");
                            System.out.print("E-mail: ");
                            console.nextLine();
                            String email = console.nextLine();

                            if (email.isBlank()) {
                                flag = false;
                                System.out.println("EMAIL INVÁLIDO!");
                            } else {
                                System.out.print("Usuário: ");
                                String usuario = console.nextLine();
                                System.out.print("Senha: ");
                                String senha = console.nextLine();
                                jorge = new Usuario(-1, usuario, email, senha);
                                System.out.println("\n-------------------");
                                System.out.println("CONFIRMA USUÁRIO?");
                                System.out.println("SIM (0)\nNÃO (1)\n\nESCOLHA UMA ALTERNATIVA:_\t");
                                int op = console.nextInt();
                                if (op == 0) {
                                    usuariosCrud.CREATE(jorge);
                                } else {
                                    System.out.println("OPERAÇÃO CANCELADA");
                                }
                                flag = true;
                            }

                        } while (!flag);

                    }
                        break;
                    case 1: {
                        boolean flag = false;
                        boolean flag2 = false;
                        do {
                            System.out.println("ACESSO AO SISTEMA");
                            console.nextLine();
                            System.out.print("E-mail: ");
                            String email = console.nextLine();
                            jorge = usuariosCrud.READ(email);
                            if (!Objects.isNull(jorge)) {

                                do {
                                    System.out.print("\nSenha: ");

                                    String senha = console.nextLine();

                                    if (senha.compareTo(jorge.getSenha()) == 0) {
                                        System.out.println("movendo pra tela principal\nAperte enter para continuar");
                                        console.nextLine();
                                        clearScreen();
                                        flag = true;
                                        flag2 = true;
                                        telaPrincipal(email);
                                    } else {
                                        System.out.println("Senha errada!");
                                        System.out.println("Deseja sair?");
                                        System.out.println("\n0) Sim\n1) Não");
                                        if (console.nextInt() == 0) {
                                            flag = true;
                                            flag2 = true;
                                        }
                                    }
                                } while (!flag2);
                            } else {
                                System.out.println("jorge não existe");
                            }
                        } while (!flag);
                    }
                        break;
                    case 3: {
                        System.out.println("\nEXCLUSÃO");

                        System.out.print("E-mail: ");
                        String email = console.nextLine();
                        usuariosCrud.DELETE(email);
                    }
                        break;

                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            } while (opcao != 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        console.close();
    }

    /**
     * Tela de Perguntas
     * 
     * @param email
     */
    public void telaPrincipal(String email) {
        int opcao = 0;

        do {
            System.out.println("PERGUNTAS 1.0");
            System.out.println("===============");
            System.out.println("\nINÍCIO\n");
            System.out.println("1) Criação de perguntas");
            System.out.println("2) Consultar/responder perguntas");
            System.out.println("3) Notificações: 0");
            System.out.println("\n0) Sair");
            System.out.print("Opção: _ ");
            try {
                opcao = console.nextInt();
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    int subOpcao = 0;
                    do {
                        console.nextLine();
                        clearScreen();
                        System.out.println("PERGUNTAS 1.0");
                        System.out.println("===============");
                        System.out.println("\nINÍCIO > CRIAÇÃO DE PERGUNTAS\n");
                        System.out.println("1) Listar");
                        System.out.println("2) Incluir");
                        System.out.println("3) Alterar");
                        System.out.println("4) Arquivar");
                        System.out.println("\n0) Retornar ao menu anterior\n");
                        System.out.print("Opção: _ ");

                        subOpcao = console.nextInt();
                        switch (subOpcao) {
                            case 1:

                                console.nextLine();
                                clearScreen();
                                listar(email);
                                break;
                            case 2:

                                console.nextLine();
                                clearScreen();
                                incluir(email);
                                break;
                            case 3:

                                alterar(email);
                                System.out.println("\n\naperte enter para continuar");
                                break;
                            case 4:

                                arquivar(email);
                                clearScreen();
                                break;
                            case 0:
                                console.nextLine();
                                clearScreen();
                                break;

                            default:
                                break;
                        }

                    } while (subOpcao != 0);
                    break;
                case 2:
                    do {

                        console.nextLine();
                        clearScreen();
                        System.out.println("PERGUNTAS 1.0");
                        System.out.println("===============");
                        System.out.println("\nINÍCIO > CONSULTAR/RESPONDER PERGUNTAS\n");
                        System.out.println("1) Busca por palavra chave");
                        System.out.println("2) Responder perguntas");
                        System.out.println("\n0) Retornar ao menu anterior\n");
                        System.out.print("Opção: _ ");

                        subOpcao = console.nextInt();
                        switch (subOpcao) {
                            case 1:
                                busca_chave();
                                break;

                            default:
                                break;
                        }
                        break;
                    } while (subOpcao != 0);
                default:
                    break;
            }

        } while (opcao != 0);
        console.nextLine();
        clearScreen();
    }

    /**
     * busca por palavra chave
     */
    public void busca_chave() {
        try {
            console.nextLine();
            System.out.println(
                    "Busque as perguntas por palavra chave separadas por ponto e vírgula\nEx: política;Brasil;eleições");
            System.out.println("Palavras chave: _");
            String key = console.nextLine();
            String[] splString = key.split(";");
            System.out.println("\n\nDados: ");
            ArrayList<Pergunta> busca = new ArrayList<Pergunta>();
            ArrayList<Pergunta> newList = new ArrayList<Pergunta>();
            for (int i = 0; i < splString.length; i++) {
                foo = perguntaCrud.READ_Palavra(splString[i]);
                for (int j = 0; j < foo.length; j++) {
                    if (foo[j].isAtiva() && !(busca.contains(foo[j]))) {
                        busca.add(foo[j]);
                    }
                }
            }
            for (Pergunta element : busca) {

                if (!newList.contains(element)) {

                    newList.add(element);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                System.out.println((i + 1) + ") ");
                System.out.println(
                        "\n+--------------------------------------------------------------------------------+\n"
                                + newList.get(i).pergunta
                                + "\n+--------------------------------------------------------------------------------+\n");
            }
            System.out.println("selecione a pergunta que deseja visualizar?\n");
            int select = console.nextInt();
            clearScreen();
            System.out.println(newList.get(select - 1));
            Pergunta Jorjao = newList.get(select - 1);
            System.out.println("RESPOSTAS\n---------");
            listarResposta(Jorjao.getIdPerguntas());
            System.out.println("\n1) Responder\n2) Avaliar\n\n0) Retornar\n\nOpção:_");
            int resp = console.nextInt();
            switch (resp) {
                case 0:

                    break;
                case 1:
                    clearScreen();
                    menuRespostas(Jorjao);
                    break;
                case 2:
                    System.out.println("digite a nota!");
                    short nota = console.nextShort();
                    qux = newList.get(select - 1);
                    qux.setNota(nota);
                    perguntaCrud.UPDATE(qux);
                    break;
                default:
                    break;
            }
            console.nextLine();
        } catch (Exception e) {
            System.out.println("ERRO NO BUSCA CHAVE || MENU");
            e.printStackTrace();
        }

    }

    /**
     * 
     * @param jorjao
    */
    public void menuRespostas(Pergunta jorjao) {
        int subOpcao = 0;
        do {

            System.out.println("PERGUNTAS 1.0");
            System.out.println("===============");
            System.out.println("\nINÍCIO > PERGUNTAS > RESPOSTAS\n");
            System.out.println(jorjao + "\n");
            System.out.println("1) Listar suas respostas");
            System.out.println("2) Incluir uma resposta");
            System.out.println("3) Alterar uma resposta");
            System.out.println("4) Arquivar uma resposta");
            System.out.println("\n0) Retornar ao menu anterior\n");
            System.out.print("Opção: _ ");

            subOpcao = console.nextInt();
            switch (subOpcao) {
                case 1:
                    System.out.println("\nLISTAR");
                    listarResposta(jorjao.getIdPerguntas(), jorge.getID());
                    console.nextLine();
                    console.nextLine();
                    clearScreen();
                    break;
                case 2:
                    incluirResposta(jorjao.getIdPerguntas(), jorge.getID(), jorge.getNome());
                    console.nextLine();
                    clearScreen();
                    break;
                case 3:
                    alterarResposta(jorjao.getIdPerguntas(), jorge.getID());
                    console.nextLine();
                    clearScreen();
                    break;
                case 4:
                    arquivarResposta(jorjao.getIdPerguntas(), jorge.getID());
                    console.nextLine();
                    clearScreen();
                    break;
                case 0:

                    break;
                default:
                    break;
            }

        } while (subOpcao != 0);
        clearScreen();
        console.nextLine();
    }

    public void listar(String email) {
        try {
            System.out.println("\nLISTAR");

            int email_hash = email.hashCode();
            lista_perguntas = perguntaCrud.READ(email_hash);
            System.out.println("Dados: \n");
            for (int i = 0; i < lista_perguntas.length; i++)
                System.out.println((i + 1) + ". " + lista_perguntas[i] + "\t");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO LISTAR");
        }

    }
     /**
      * 
      * @param idPergunta
      */
    public void listarResposta(int idPergunta) {
        try {
            Respostas[] array;
            array = respostasCrud.READ(idPergunta);
            System.out.println("Dados: \n");
            for (int i = 0; i < array.length; i++)
                System.out.println((i + 1) + ". " + array[i] + "\t");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO LISTAR RESPOSTA");
        }

    }
    /**
     * 
     * @param idPergunta
     * @param idUsuario
     */
    public void listarResposta(int idPergunta, int idUsuario) {
        try {
            Respostas[] array;
            array = respostasCrud.READ(idPergunta);
            System.out.println("Dados: \n");
            ArrayList<Respostas> novo = new ArrayList<Respostas>();

            for (int i = 0; i < array.length; i++) {
                if (array[i].getIdUsuario() == idUsuario) {
                    novo.add(array[i]);
                }
            }
            for (int j = 0; j < novo.size(); j++) {
                System.out.println((j + 1) + ". " + novo.get(j) + "\t");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO LISTAR RESPOSTA");
        }

    }

    public void incluir(String email) {

        try {
            System.out.println("\nINCLUSÃO");

            int email_hash = email.hashCode();

            String dado;
            String palavras;
            do {

                System.out.print("Digite a pergunta: ");
                dado = console.nextLine();
                if (dado.isEmpty()) {
                    System.out.println("Por favor, digite a pergunta");
                }
            } while ((dado.isEmpty()));
            do {

                System.out.print(
                        "Digite as palavras chave separadas por ponto e vírgula\nEx: política;Brasil;eleições\n");
                palavras = console.nextLine();
                if (palavras.isEmpty()) {
                    System.out.println("Por favor, digite as palavras chave");
                }
            } while ((palavras.isEmpty()));

            qux = new Pergunta(email_hash, dado, jorge.getNome(), palavras);
            System.out.println(qux + "\n CONFIRMA PERGUNTA?\nSIM (0)\tNÃO (1)");
            if (console.nextInt() == 0) {

                perguntaCrud.CREATE(qux);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO INCLUIR");
        }
    }
    /**
     * 
     * @param idPergunta
     * @param idUsuario
     * @param usuario
     */
    public void incluirResposta(int idPergunta, int idUsuario, String usuario) {

        try {
            System.out.println("\nINCLUSÃO");

            String dado;

            do {

                System.out.print("Digite a resposta: ");
                dado = console.nextLine();
                if (dado.isEmpty()) {
                    System.out.println("Por favor, digite a pergunta");
                }
            } while ((dado.isEmpty()));

            Respostas nova = new Respostas(idPergunta, idUsuario, dado, usuario);
            System.out.println(nova + "\n CONFIRMA RESPOSTA?\nSIM (0)\tNÃO (1)");
            if (console.nextInt() == 0) {

                respostasCrud.CREATE(nova);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO INCLUIR RESPOSTA");
        }
    }

    public void alterar(String email) {
        try {
            System.out.println("UPDATE");

            int email_hash = email.hashCode();
            foo = perguntaCrud.READ(email_hash);

            System.out.println("Dados: ");
            console.nextLine();
            ArrayList<Pergunta> caboOsNomes = new ArrayList<Pergunta>();
            for (int i = 0; i < foo.length; i++) {
                if (foo[i].isAtiva()) {
                    caboOsNomes.add(foo[i]);
                }
            }
            for (int j = 0; j < caboOsNomes.size(); j++) {

                System.out.print("ITEM " + (j + 1) + ") \t");
                System.out.println(caboOsNomes.get(j));
            }
            System.out.println("selecione o item que quer mudar");
            int bar = console.nextInt();
            qux = caboOsNomes.get(bar - 1);
            console.nextLine();
            do {

                System.out.println("Digite a nova pergunta");
                qux.pergunta = console.nextLine();
            } while (qux.pergunta.isEmpty());
            String novaPalavra;
            do {

                System.out.println("Digite a nova palavra chave");
                novaPalavra = console.nextLine();
            } while (qux.pergunta.isEmpty());
            qux.setPalavraChave(novaPalavra);
            System.out.println("\n");
            System.out.println(qux + "\n CONFIRMA ALTERAÇÃO?\nSIM (0)\tNÃO (1)");
            if (console.nextInt() == 0) {
                perguntaCrud.DELETE_palavra(qux);
                perguntaCrud.UPDATE(qux);
                System.out.println("Alteração bem sucedida!");
            }
            console.nextLine();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO LISTAR");
        }
    }
    /**
     * 
     * @param idPergunta
     * @param idUsuario
     */
    public void alterarResposta(int idPergunta, int idUsuario) {
        try {
            System.out.println("UPDATE");

            bar = respostasCrud.READ(idPergunta);

            System.out.println("Dados: ");
            Respostas[] array;
            array = respostasCrud.READ(idPergunta);
            System.out.println("Dados: \n");
            ArrayList<Respostas> novo = new ArrayList<Respostas>();

            for (int i = 0; i < array.length; i++) {
                if (array[i].getIdUsuario() == idUsuario && array[i].isAtiva()) {
                    novo.add(array[i]);
                }
            }
            for (int j = 0; j < novo.size(); j++) {
                System.out.println((j + 1) + ". " + novo.get(j) + "\t");
            }
            System.out.println("selecione o item que quer mudar");
            int bar = console.nextInt();
            Respostas nova = novo.get(bar - 1);
            console.nextLine();
            do {

                System.out.println("Digite a nova pergunta");
                nova.resposta = console.nextLine();
            } while (nova.resposta.isEmpty());
            System.out.println("\n");
            System.out.println(nova + "\n CONFIRMA ALTERAÇÃO?\nSIM (0)\tNÃO (1)");
            if (console.nextInt() == 0) {
                respostasCrud.UPDATE(nova);
                System.out.println("Alteração bem sucedida!");
            }
            console.nextLine();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO ALTERAR RESPOSTA");
        }
    }

    public void arquivar(String email) {
        try {
            System.out.println("\nEXCLUSÃO");

            int email_hash = email.hashCode();
            foo = perguntaCrud.READ(email_hash);

            System.out.println("Dados: ");
            console.nextLine();
            ArrayList<Pergunta> caboOsNomes = new ArrayList<Pergunta>();
            for (int i = 0; i < foo.length; i++) {
                if (foo[i].isAtiva()) {
                    caboOsNomes.add(foo[i]);
                }
            }
            for (int j = 0; j < caboOsNomes.size(); j++) {

                System.out.print("ITEM " + (j + 1) + ") \t");
            }

            System.out.println("selecione o item que quer mudar");
            int bar = console.nextInt();
            qux = caboOsNomes.get(bar - 1);
            perguntaCrud.DELETE_palavra(qux);
            qux.setAtiva(false);
            System.out.println(qux + "\n CONFIRMA ALTERAÇÃO?\nSIM (0)\tNÃO (1)");
            if (console.nextInt() == 0) {
                perguntaCrud.UPDATE(qux);
                System.out.println("Arquivação bem sucedida!");
            }
            console.nextLine();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO LISTAR");
        }
    }
    /**
     * 
     * @param idPergunta
     * @param idUsuario
     */
    public void arquivarResposta(int idPergunta, int idUsuario) {
        try {
            System.out.println("\nEXCLUSÃO");

            Respostas[] array;
            array = respostasCrud.READ(idPergunta);
            System.out.println("Dados: \n");
            ArrayList<Respostas> novo = new ArrayList<Respostas>();

            for (int i = 0; i < array.length; i++) {
                if (array[i].getIdUsuario() == idUsuario && array[i].isAtiva()) {
                    novo.add(array[i]);
                }
            }
            for (int j = 0; j < novo.size(); j++) {
                System.out.println((j + 1) + ". " + novo.get(j) + "\t");
            }

            System.out.println("selecione o item que quer mudar");
            int bar = console.nextInt();
            Respostas nova = novo.get(bar - 1);
            nova.setAtiva(false);
            System.out.println(nova + "\n CONFIRMA ALTERAÇÃO?\nSIM (0)\tNÃO (1)");
            if (console.nextInt() == 0) {
                respostasCrud.UPDATE(nova);
                respostasCrud.DELETE(nova);
                System.out.println("Arquivação bem sucedida!");
            }
            console.nextLine();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO ARQUIVAR RESPOSTAS");
        }
    }
}
