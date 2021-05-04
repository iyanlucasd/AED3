/*
TESTE DE TABELA HASH EXTENSÍVEL

Este programa principal serve para demonstrar o uso
da tabela hash extensível como um índice indireto.
Aqui, cada elemento do índice será composto pelo par
(email, ID) representado por meio de um objeto da classe
Usuario (PCV = par chave valor).

Para funcionamento como índice direto, precisaríamos de 
mais uma classe que contivesse o par (ID, endereço).
Mas isso fica por sua conta ;)

Implementado pelo Prof. Marcos Kutova
v1.1 - 2021
*/

public class Main {

  // Método principal apenas para testes
  public static void main(String[] args) {
    UI interfaceUsuario = new UI();
    interfaceUsuario.MENU();
  }
}