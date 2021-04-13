import java.util.Objects;
import java.util.Scanner;
import java.io.File;
import aed3.HashExtensivel;

public class UI {
    protected HashExtensivel<Usuario> he;
    protected Scanner console = new Scanner(System.in);
    protected int countID = 0;
    protected Usuario jorge;

    public UI() {

    }

    public void MENU() {

        try {
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
                            countID++;
                            System.out.print("Usuário: ");
                            String usuario = console.nextLine();
                            System.out.print("Senha: ");
                            String senha = console.nextLine();
                            if (he.create(new Usuario(email, countID, usuario, senha))) {
                                System.out.println("\n-------------------");
                                System.out.println("CONFIRMA USUÁRIO?");
                                // he.print();
                                System.out.println("SIM (0)\nNÃO (1)\n\nESCOLHA UMA ALTERNATIVA:_\t");
                                int op = console.nextInt();
                                if (op == 1) {
                                    he.delete(email.hashCode());
                                    System.out.println("OPERAÇÃO CANCELADA");
                                }
                                flag = true;
                            }
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
                        jorge = he.read(email.hashCode());
                        if (!Objects.isNull(jorge)) {
                            // System.out.print("Dados: " + jorge);
                            do {
                                System.out.print("\nSenha: ");
                                // console.nextLine();
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
                    he.delete(email.hashCode());
                    he.print();
                }
                    break;

                case 0:
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
