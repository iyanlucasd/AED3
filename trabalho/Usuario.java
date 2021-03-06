/*

Esta classe representa um PAR CHAVE VALOR (PCV) 
para uma entidade Pessoa. Seu objetivo é representar
uma entrada de índice. 

Esse índice será secundário e indireto, baseado no
email de uma pessoa. Ao fazermos a busca por pessoa,
ele retornará o ID dessa pessoa, para que esse ID
possa ser buscado em um índice direto (que não é
apresentado neste projeto)

Um índice direto de ID precisaria ser criado por meio
de outra classe, cujos dados fossem um int para o ID
e um long para o endereço
 
Implementado pelo Prof. Marcos Kutova
v1.0 - 2021
 
*/

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Usuario implements aed3.RegistroHashExtensivel<Usuario> {

  private String email;
  private int id;
  private short TAMANHO = 132;
  private String usuario;
  private String senha;

  public Usuario() {
    this("", -1, "", "");
  }

  public Usuario(String e, int i, String u, String s) {
    try {
      this.email = e;
      this.id = i;
      this.usuario = u;
      this.senha = s;
      if (e.length() + u.length() + s.length() + 4 > TAMANHO)
        throw new Exception("Número de caracteres do email maior que o permitido. Os dados serão cortados.");
    } catch (Exception ec) {
      ec.printStackTrace();
    }
  }

  @Override
  public int hashCode() {
    return this.email.hashCode();
  }

  public int getID(){
    return id;
  }

  public void setID(int id){
    this.id = id;
  }

  public short size() {
    return this.TAMANHO;
  }
  public String getSenha() {
      return senha;
  }

  public String toString() {
    return this.email + ";" + this.id +";"+ this.usuario +";"+ this.senha;
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeUTF(email);
    dos.writeInt(id);
    dos.writeUTF(usuario);
    dos.writeUTF(senha);
    byte[] bs = baos.toByteArray();
    byte[] bs2 = new byte[TAMANHO];
    for (int i = 0; i < TAMANHO; i++)
      bs2[i] = ' ';
    for (int i = 0; i < bs.length && i < TAMANHO; i++)
      bs2[i] = bs[i];
    return bs2;
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.email = dis.readUTF();
    this.id = dis.readInt();
    this.usuario = dis.readUTF();
    this.senha = dis.readUTF();

  }

}