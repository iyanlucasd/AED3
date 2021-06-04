import java.util.Objects;
import java.util.Scanner;
import java.io.File;
import aed3.HashExtensivel;

public class MENU {
    protected CRUD_usuario CRUD;
    protected HashExtensivel<Usuario> he;
    protected Scanner console = new Scanner(System.in);
    protected Usuario jorge;

    public MENU() {
    }

    public void menu() {

        try {
            CRUD = new CRUD_usuario<>(Usuario.class.getConstructor());
            File d = new File("dados");
            if (!d.exists())
                d.mkdir();
            he = new HashExtensivel<>(Usuario.class.getConstructor(), 4, "dados/pessoas.hash_d.db",
                    "dados/pessoas.hash_c.db");

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
                                jorge = new Usuario(email, -1, usuario, senha);
                                System.out.println("\n-------------------");
                                System.out.println("CONFIRMA USUÁRIO?");
                                System.out.println("SIM (0)\nNÃO (1)\n\nESCOLHA UMA ALTERNATIVA:_\t");
                                int op = console.nextInt();
                                if (op == 0) {
                                    CRUD.CREATE(jorge);
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
                            jorge = (Usuario) CRUD.READ(email.hashCode());
                            if (!Objects.isNull(jorge)) {

                                do {
                                    System.out.print("\nSenha: ");

                                    String senha = console.nextLine();
                                    System.out.println("SENHA = " + jorge.getSenha());
                                    if (senha.compareTo(jorge.getSenha()) == 0) {
                                        System.out.println("movendo pra tela principal");
                                        flag = true;
                                        flag2 = true;
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
                        CRUD.DELETE(email.hashCode());
                    }
                        break;

                    case 4:
                        he.print();
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
}
