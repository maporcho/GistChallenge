# Netshoes Gist Challenge

Um aplicativo Android em que os usuários podem listar todos os gists públicos disponíveis e adicionar gists a uma lista de favoritos.

![GistChallenge](/docs/gist_challenge.png)

*Read in [English](README.md)*


## Principais Funcionalidades

* **Lista de gists**: lista todos os gists públicos. Os usuários podem ver os detalhes de um gist tocando em um item da lista. Os usuários também podem adicionar/remover gists da lista de favoritos, tocando na estrela em cada item da lista.

* **Detalhe do gist**: mostra o nome do proprietário do gist, junto com a imagem do avatar. A descrição e o URL do gist também são mostrados.

* **Lista de gists favoritos**: lista todos os gists favoritos. Os usuários podem detalhar um gist tocando em um item da lista.
Os usuários podem remover um gist favorito tocando na estrela em cada item da lista de favoritos.
Tanto a lista de gists favoritos quanto o detalhe de um gist favorito estão disponíveis offline.


## A solução
O aplicativo foi construído seguindo uma arquitetura MVP, com foco na modularidade e testabilidade.

O código-fonte é organizado da seguinte forma:

* **/common:** classes não relacionadas ao negócio, usadas em vários casos de uso.

* **/use-cases/<use case>:** lógica de negócio e classes relacionadas a um caso de uso específico
  * *\<use case\>Contract:* o contrato para o caso de uso, vinculando a View e o Presenter.
  * **/model:** os modelos usados ​​no caso de uso específico
  * **/presenter:** o Presenter usado no caso de uso específico (descendentes de *BasePresenter*)
  * **/repository**: os Repositórios (Repository) usados ​​no caso de uso específico
  * **/ui:** Activities e Fragments usados na camada de visão (descendentes de *BaseView*)

Interfaces foram definidas para a maioria das classes e as implementações estão no subpacote **implementation** do pacote em que estão contidas. A intenção foi facilitar o teste dessas classes (usados mocks ou stubs para suas dependências, por exemplo).

### Tratamento de erros

Os métodos nos Presenters e Repositories tratam os erros por meio de callbacks (onSuccess e onFailure). Isso garante que as classes que os utilizam estejam cientes das condições de sucesso e de erro e possam lidar com ambas de forma adequada.

### Flavors

Existem três flavors disponíveis: índigo, blue e dark. Cada flavor define um version name e uma cor diferentes para o aplicativo.

### Testes

Um teste de unidade de exemplo está em na subpasta **/test**. A estrutura dessa pasta segue a estrutura da pasta /main/java.


