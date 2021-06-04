<h1 align="center">CRUD - Stack Overflow!</h1>

![Badge](https://img.shields.io/badge/AED3-Parte2-%237159c1?style=for-the-badge&logo=ghost)

<!--ts-->

- [Parte 1](##1ªEtapa)
- [Parte 2](##2ªEtapa)
  - [Comentários](##Comentários)
  - [Implementados](##Implementado)
  - [Arquivos](##Arquivos)
  - [Métodos](##Métodos)
- [Parte 3](##3ªEtapa)
  - [Comentários](##Comentários3ª)
  - [Implementados](##Implementado3ª)
  - [Arquivos](##Arquivos3ª)
  - [Métodos](##Métodos3ª)
- [Parte 4](##4ªEtapa)
  - [Comentários](##Comentários4ª)
  - [Implementados](##Implementado4ª)
  - [Arquivos](##Arquivos4ª)
  - [Métodos](##Métodos4ª)
- [Autores](#Autores)
<!--te-->

## 1ªEtapa

---

> **Professor!** \
> Boa noite!, primeiramente deixe eu explicar a situação que ocorreu conosco, Iyan e Samir.\
> Nosso grupo está desfalcado, há somente nós dois, visto que as outra pessoas ou saíram do curso ou simplesmente não tem mais contato. O irmão do Samir foi hospitalizado no Risoleta Neves e ele precisou ficar de acompanhante por tempo integral, me deixando responsável pelo projeto.\
> Fiz o que pude tentando não atrapalhar o Sr. no prazo que tínhamos. Para nossa felicidade o irmão já passa bem e dessa vez fizemos nosso melhor! Corrigimos todos os erros e o .md começa aqui na segunda parte!

> **_Meu sincero obrigado, Iyan Lucas :3_**

## <img src="TorreWaffle.png">

## 2ªEtapa

## Comentários

- [x] ~~Faltaram todas as operações do CRUD. O índice baseado em e-mails serve apenas para o acesso. Na próxima etapa, o CRUD indexado (índice direto) será essencial. Usem o CRUD que fizeram no desafio 2 para isso.~~ **Feito!**

- [x] ~~O índice secundário deve usar um objeto contendo apenas o par [e-mail, ID]. O ID será definido pelo CRUD.~~ **Feito!**

- [x] ~~Vocês não devem inserir o objeto no índice antes da confirmação de cadastro. Apenas quando o usuário confirmar é que devem chamar o método create() da tabela hash.~~ **Feito!**

## Implementado

    Tudo foi implementado, todas as opções da tela de perguntas requeridas pela etapa 2

## Arquivos

- `\aed3` --> Arquivos disponibilizados pelo Professor
  - `\aed3\ArvoreBMais_ChaveComposta_Int_Int.java` --> Arquivo da árvore B+
  - `\aed3\HashExtensivel.java` --> Arquivo do Hash
  - `\aed3\RegistroHashExtensivel.java` --> Arquivo do registro do Hash
- `\dados` --> Arquivos .db que guardam os dados que a gente gera no programa
- `CRUD.java` --> CRUD genérico
- `CRUD_perguntas.java` --> CRUD genérico (para perguntas, como especificado na ultima aula)
- **!DEPRECATED** `Log.java` --> Arquivo que gera um arquivo de log separado para debuggar (não utilizo mais, me rendi ao System.out genérico)
- `Main.java` --> Arquivo para executar no _Repl.it_ (basicamente só chama o arquivo `MENU.java`)
- `MENU.java` --> Arquivo responsável pelo menu apresentado ao usuário e para as chamadas do crud
- `Pergunta.java` --> Declaração da classe Pergunta
- `ponteiroArvore.java` --> Declaração da classe ponteiroArvore utilizada na Árvore B+ genérica
- `ponteiroArvore.java` --> Declaração da classe ponteiroHash utilizada no Hash Extensível genérico
- `README.md` --> Registro escrito
- `registro_pergunta.java` --> Interface da classe Pergunta
- `registro.java` --> Interface da classe Usuário
- `Usuario.java` --> Declaração da classe Usuário

## Métodos

> ### CRUD e CRUD_pergunta

```java
    /**
     * Construtor genérico
     *
     * @param foo       Construtor genérico de objeto passado pela main (6)
     * @param file_name String com o caminho
     */
    CRUD(Constructor foo, String file_name)

        /**
     *
     * @param foobar
     * @return id
     * @throws SecurityException
     * @throws IOException
     */
    public int CREATE(T foobar) throws SecurityException, IOException

    /**
     * Create genérico
     * @return id
     * @throws SecurityException --> LOG
     * @throws IOException       --> LOG && RAF
     */
    public int CREATE(T foobar, int id) throws SecurityException, IOException

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
    public T READ(String email) throws Exception

    /**
     * *O update verifica se o registro correspondente é menor ou menor
     * *aí ele ou cria um novo se maior ou modifica
     *
     * @param foobar objeto que a pessoa quer que atualiza (com as infos já
     *               atualizadas)
     * @throws SecurityException
     * @throws IOException
     */
    public void UPDATE(T foobar) throws SecurityException, IOException

    /**
     * Deleta no Hash no CRUD
     * ou Atualiza o Status no pergunta
     *
     * @param id
     * @throws SecurityException
     * @throws IOException
     */
    public void DELETE(String email) throws SecurityException, IOException

    /**
     * Remove acento e unicode transformando puro ascii
     * @param str
     * @return string sem acento
     */
    public static String removerAcentos(String str)

    /**
     * Deleta os pares de palavras chave da lista inversa
     * 
     * @param foobar
     */
    public void DELETE_palavra(Pergunta foobar)
```

> ### Menu

```java
    /**
     * CLEAR no terminal
     */
    public void clearScreen()

    /**
     * Menu principal
     */
    public void menu()

    /**
     * Tela de Perguntas que redireciona para os metodos da pergunta
     * @param email
     */
    public void telaPrincipal(String email)

    /**
     * busca por palavra chave
     */
    public void busca_chave()

```
---
## 3ªEtapa

## Comentários3ª

- [x] ~~Vocês ainda estão com um problema aqui. É preciso ter uma única classe CRUD (e não uma para usuários, outra para perguntas, etc.). Para fazer isso, vocês não podem usar métodos, atributos, etc. específicos dentro dessa classe. O índice interno ao CRUD é um índice baseado no ID e apenas isso. Cada classe de entidade deve ter um método getID() que possa ser usado de forma genérica no CRUD. O índice indireto de emails, portanto, deve ser usado fora do CRUD (no programa principal).~~ **Feito!** *(Utilizamos o CRUD como classe genérica e duas subclasses que a estendem)*

- [x] ~~Ao listar as perguntas para alteração/arquivamento, vocês devem excluir da numeração aquelas arquivadas. Mas o número deve continuar sequencialmente (e não pular as excluídas). Para isso, vocês precisarão criar um vetor de IDs de perguntas apenas com as válidas.~~ **Feito!**

- [x] ~~A árvore B deve ficar fora do CRUD, porque também deve ser usado o CRUD genérico. Assim, ela também deverá ser gerenciada no programa principal.~~ **Feito!** *(vide comentário 1)*

- [x] ~~Não é uma boa ideia chamar um objeto de "crud" e outro de "CRUD", ainda mais quando vocês tem uma classe desse tipo. Usem "crudUsuario", "crudPerguntas", etc. ou algo assim. Vocês terão outras árvores. Seria melhor dar um nome mais significativo para a árvore de perguntas também.~~ **Feito!** *(Mudamos os nomes para perguntaCrud e usuarioCrud)*

## Implementado3ª

    Tudo foi implementado, todas as opções da tela de perguntas requeridas pela etapa 3

## Arquivos3ª
```diff
> `\aed3` --> Arquivos disponibilizados pelo Professor
  > `\aed3\ArvoreBMais_ChaveComposta_Int_Int.java` --> Arquivo da árvore B+
  > `\aed3\HashExtensivel.java` --> Arquivo do Hash
  > `\aed3\RegistroHashExtensivel.java` --> Arquivo do registro do Hash
+ > `\aed3\ListaInvertida.java` --> Arquivo da lista invertida
> `\dados` --> Arquivos .db que guardam os dados que a gente gera no programa
! `CRUD.java` --> classe CRUD genérico
- **!DEPRECATED** `CRUD_perguntas.java` --> CRUD genérico (para perguntas, como especificado na ultima aula)
+ `CRUD_filhos.java` --> subclasse do CRUD genérico (contém as subclasses CRUD_Usuários && CRUD_perguntas que extendem CRUD)
- **!DEPRECATED** `Log.java` --> Arquivo que gera um arquivo de log separado para debuggar (não utilizo mais, me rendi ao System.out genérico)
> `Main.java` --> Arquivo para executar no _Repl.it_ (basicamente só chama o arquivo `MENU.java`)
! `MENU.java` --> Arquivo responsável pelo menu apresentado ao usuário e para as chamadas do crud **Modificado** adaptado os metodos para palavras chave
! `Pergunta.java` --> Declaração da classe Pergunta **Modificado** adicionado palavras chaves e nome de usuario 
> `ponteiroArvore.java` --> Declaração da classe ponteiroArvore utilizada na Árvore B+ genérica
> `ponteiroArvore.java` --> Declaração da classe ponteiroHash utilizada no Hash Extensível genérico
> `README.md` --> Registro escrito
> `registro_pergunta.java` --> Interface da classe Pergunta
> `registro.java` --> Interface da classe Usuário
> `Usuario.java` --> Declaração da classe Usuário
```

## Métodos3ª


> ### Menu

```java
    /**
     * busca por palavra chave
     */
    public void busca_chave()

```
> ### CRUD e CRUD_pergunta

```java
    /**
     * Remove acento e unicode transformando puro ascii
     * @param str
     * @return string sem acento
     */
    public static String removerAcentos(String str)

    /**
     * Deleta os pares de palavras chave da lista inversa
     * 
     * @param foobar
     */
    public void DELETE_palavra(Pergunta foobar)
```


---
## 4ªEtapa

## Comentários4ª

- [x] **Vocês continuam com várias implementações do CRUD. Vocês chegaram a ver o código que deixei como exemplo, usando herança? Apesar das suas classes CRUD de filhos estenderem a classe principal, vocês estão implementando tudo novamente.** *(Professor, não conseguimos fazer essa correção porque teriamos que refazer todo os CRUD, visto o volume de trabalho enorme, seja no estágio quanto na faculdade, não tivemos tempo para realizar)*

- [x] **O controle de novos IDs de usuários e de perguntas também está errado. Vocês não podem nem usar um número arbitrário nem pegar a posição do arquivo. O certo é criar um contador no cabeçalho do arquivo.** *(Idem comentário 1)*

- [x] ~~Quando uma pergunta é arquivada, a lista (nas operações de alteração ou exclusão) deve ser sequencial contínua, isto é, sem pular números.~~ **Feito!** 


## Implementado4ª

    Tudo da Resposta foi implementado, todas as opções da tela de Respostas requeridas pela etapa 4

## Arquivos4ª
```diff
> `\aed3` --> Arquivos disponibilizados pelo Professor
  > `\aed3\ArvoreBMais_ChaveComposta_Int_Int.java` --> Arquivo da árvore B+
  > `\aed3\HashExtensivel.java` --> Arquivo do Hash
  > `\aed3\RegistroHashExtensivel.java` --> Arquivo do registro do Hash
  > `\aed3\ListaInvertida.java` --> Arquivo da lista invertida
> `\dados` --> Arquivos .db que guardam os dados que a gente gera no programa
> `CRUD.java` --> classe CRUD genérico
+ `CRUD_filhos.java` --> subclasse do CRUD genérico (contém as subclasses CRUD_Usuários && CRUD_perguntas que extendem CRUD)
> `Main.java` --> Arquivo para executar no _Repl.it_ (basicamente só chama o arquivo `MENU.java`)
> `MENU.java` --> Arquivo responsável pelo menu apresentado ao usuário e para as chamadas do crud **Modificado** adaptado os metodos para palavras chave
> `Pergunta.java` --> Declaração da classe Pergunta **Modificado** adicionado palavras chaves e nome de usuario 
+ `Respostas.java` --> Declaração da classe Pergunta **Modificado** adicionado palavras chaves e nome de usuario 
> `ponteiroArvore.java` --> Declaração da classe ponteiroArvore utilizada na Árvore B+ genérica
> `ponteiroArvore.java` --> Declaração da classe ponteiroHash utilizada no Hash Extensível genérico
> `README.md` --> Registro escrito
> `registro_pergunta.java` --> Interface da classe Pergunta
> `registro.java` --> Interface da classe Usuário
> `Usuario.java` --> Declaração da classe Usuário
```

## Métodos4ª
> ### Menu

```java
    /**
     * 
     * @param jorjao
    */
    public void menuRespostas(Pergunta jorjao)

      /**
      * 
      * @param idPergunta
      */
    public void listarResposta(int idPergunta)

    /**
     * 
     * @param idPergunta
     * @param idUsuario
     */
    public void listarResposta(int idPergunta, int idUsuario)

    /**
     * 
     * @param idPergunta
     * @param idUsuario
     * @param usuario
     */
    public void incluirResposta(int idPergunta, int idUsuario, String usuario)

    /**
     * 
     * @param idPergunta
     * @param idUsuario
     */
    public void alterarResposta(int idPergunta, int idUsuario)

    /**
     * 
     * @param idPergunta
     * @param idUsuario
     */
    public void arquivarResposta(int idPergunta, int idUsuario)
```

---

# Autores

<a href="https://github.com/iyanlucasd">
 <img style="border-radius: 50%;" src="IMG_20210421_101951_911.jpg" width="100px;" alt=""/>
 <br />
 <sub><b>Iyan Lucas</b></sub></a> <a href="https://blog.rocketseat.com.br/author/thiago//" title="Rocketseat">🚀</a>
 
 .

 <a href="https://github.com/SamCambraia1">
 <img style="border-radius: 50%;" src="samir.jpeg" width="100px;" alt=""/>
 <br />
 <sub><b>Samir</b></sub></a> <a href="https://github.com/SamCambraia1" title="Rocketseat">🚀</a>
