import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import aed3.*;

public class CRUD_usuario<T extends RegistroHashExtensivel> {
    protected HashExtensivel<Usuario> he;
    protected Scanner console = new Scanner(System.in);
    protected int countID = 0;
    protected T jorge;
    protected Constructor foo; // Constructor para inicializar T fora do "construtor"
    protected String file_path; // String pra caminho personalizado

    CRUD_usuario(Constructor foo) throws NoSuchMethodException, SecurityException, Exception {
        try {
            this.foo = foo; // Alocando o construtor
            this.jorge = (T) foo.newInstance(); // alocando o tipo pra classe genérica e criando objeto
            this.he = new HashExtensivel<>(Usuario.class.getConstructor(), 4, "dados/pessoas.hash_d.db",
                    "dados/pessoas.hash_c.db");
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            ((Throwable) e).printStackTrace();
        }
    } // end construtor

    public Boolean CREATE(T foobar) {
        countID++;
        return CREATE(foobar, countID);
    }

    public Boolean CREATE(T foobar, int id) {
        foobar.setID(id);
        Boolean flag = false;
        try {
            flag = he.create((Usuario) foobar);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("erro no CREATE");
        }
        return flag;
    }

    public T READ(int hash) {
        try {
            jorge = (T) he.read(hash);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Erro no READ");
        }

        return jorge;
    }

    public void UPDATE(T jorge) {
        
    }

    public void DELETE(int hash){
        try {
            if(he.delete(hash))
                System.out.println("DELETADO com sucésso");
            else
                System.out.println("ERRO em deletar");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Erro no DELETE");
        }
    }

} // end CRUD