import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;

import aed3.ArvoreBMais_ChaveComposta_Int_Int;
import aed3.HashExtensivel;


public class MENU {
    protected CRUD crud;
    protected Scanner console = new Scanner(System.in);
    protected Usuario jorge;
    ArvoreBMais_ChaveComposta_Int_Int arvore;
    Pergunta qux;
    List lista_perguntas;
    Pergunta[] foo;
    CRUD_perguntas CRUD;

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
            crud = new CRUD<>(Usuario.class.getConstructor(), "usuarios");
            File d = new File("dados");
            arvore = new ArvoreBMais_ChaveComposta_Int_Int(5, "dados/arvore.db");
            CRUD = new CRUD_perguntas<>(Pergunta.class.getConstructor(), "Perguntas");
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
                                    crud.CREATE(jorge);
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
                            jorge = (Usuario) crud.READ(email);
                            if (!Objects.isNull(jorge)) {

                                do {
                                    System.out.print("\nSenha: ");

                                    String senha = console.nextLine();
                                    System.out.println("SENHA = " + jorge.getSenha());
                                    if (senha.compareTo(jorge.getSenha()) == 0) {
                                        System.out.println("movendo pra tela principal\nAperte enter para continuar");
                                        console.nextLine();
                                        clearScreen();
                                        flag = true;
                                        flag2 = true;
                                        telaPrincipal(email);
                                    } else {
                                        System.out.println("Senha errada!");
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
                        crud.DELETE(email);
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

                default:
                    break;
            }

        } while (opcao != 0);
        console.nextLine();
        clearScreen();
    }

    public void listar(String email) {
        try {
            System.out.println("\nLISTAR");

            int email_hash = email.hashCode();
            lista_perguntas = CRUD.READ(email_hash);
            System.out.println("Dados: ");
            for (int i = 0; i < lista_perguntas.size(); i++)
                System.out.println((i+1) + ". " + lista_perguntas.get(i) + "\t");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO LISTAR");
        }

    }

    public void incluir(String email) {

        try {
            System.out.println("\nINCLUSÃO");

            int email_hash = email.hashCode();

                String dado;
                do {

                    System.out.print("Pergunta: ");
                    dado = console.nextLine();
                    if (dado.isEmpty()) {
                        System.out.println("Por favor, digite a pergunta");
                    }
                } while ((dado.isEmpty()));

                qux = new Pergunta(email_hash, dado);
                System.out.println(qux + "\n CONFIRMA PERGUNTA?\nSIM (0)\tNÃO (1)");
                if (console.nextInt() == 0) {

                    CRUD.CREATE(qux);
                }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO LISTAR");
        }
    }

    public void alterar(String email) {
        try {
            System.out.println("UPDATE");

            int email_hash = email.hashCode();
            lista_perguntas = CRUD.READ(email_hash);
            foo = new Pergunta[lista_perguntas.size()];
            for (int i = 0; i < foo.length; i++) {
                foo[i] = (Pergunta) lista_perguntas.get(i);
            }
            
            System.out.println("Dados: ");
            console.nextLine();
            for (int i = 0; i < lista_perguntas.size(); i++) {
                if (foo[i].isAtiva()) {
                    System.out.print("ITEM " + (i + 1) + ") \t");
                    System.out.println(foo[i]);
                }
            }
            System.out.println("selecione o item que quer mudar");
            int bar = console.nextInt();
            qux = (Pergunta) lista_perguntas.get(bar - 1);
            console.nextLine();
            do {
                
                System.out.println("Digite a nova pergunta");
                qux.pergunta = console.nextLine();
            } while (qux.pergunta.isEmpty());
            System.out.println("\n");
            System.out.println(qux + "\n CONFIRMA ALTERAÇÃO?\nSIM (0)\tNÃO (1)");
            if (console.nextInt() == 0) {
                CRUD.UPDATE(qux);
                System.out.println("Alteração bem sucedida!");
            }
            console.nextLine();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO LISTAR");
        }
    }

    public void arquivar(String email) {
        try {
            System.out.println("\nEXCLUSÃO");

            int email_hash = email.hashCode();
            lista_perguntas = CRUD.READ(email_hash);
            foo = new Pergunta[lista_perguntas.size()];
            for (int i = 0; i < foo.length; i++) {
                foo[i] = (Pergunta) lista_perguntas.get(i);
            }
            
            System.out.println("Dados: ");
            console.nextLine();
            for (int i = 0; i < lista_perguntas.size(); i++) {
                if (foo[i].isAtiva()) {
                    System.out.print("ITEM " + (i + 1) + ") \t");
                    System.out.println(foo[i]);
                }
            }

            System.out.println("selecione o item que quer mudar");
            int foo = console.nextInt();
            qux = (Pergunta) lista_perguntas.get(foo - 1);

            qux.setAtiva(false);
            System.out.println(qux + "\n CONFIRMA ALTERAÇÃO?\nSIM (0)\tNÃO (1)");
            if (console.nextInt() == 0) {
                CRUD.UPDATE(qux);
                System.out.println("Arquivação bem sucedida!");
            }
            console.nextLine();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("PROBLEMA NO LISTAR");
        }
    }
}
