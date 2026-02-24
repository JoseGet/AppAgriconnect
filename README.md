# Agriconnect 

Este projeto está licenciado sob a licença AGPLv3 - consulte o arquivo LICENSE para detalhes.

O projeto Agriconnect é uma plataforma de código aberto dedicada à auxiliar produtores agrícolas e consumidores de feiras locais.
Ele nasceu da necessidade de digitalizar e dar visibilidade ao trabalho do produtor rural, facilitando o escoamento da produção e o acesso do consumidor a produtos frescos e de origem garantida.

Este projeto tem apoio da iniciativa [Mover-se na Web](https://moverse.ceweb.br/), desenvolvida pelo NIC.br e Ceweb.br, que incentiva tecnologias com alto potencial de impacto social e transformação comunitária. 

## Preview do Aplicativo

Demonstração de algumas telas: 

<img width="350" height="650" alt="Screenshot_20260117_112416" src="https://github.com/user-attachments/assets/624457ec-db89-4656-b1c0-42a8cec09722" />

<img width="350" height="650" alt="Screenshot_20260117_112454" src="https://github.com/user-attachments/assets/1ba119de-a8ec-4b83-b41c-2f69726b1d9f" />

<img width="350" height="650" alt="Screenshot_20260117_122520" src="https://github.com/user-attachments/assets/74343e4b-e05b-4e87-bb20-6ba44e502066" />

<img width="350" height="650" alt="Screenshot_20260117_122531" src="https://github.com/user-attachments/assets/23bbfb7e-d664-4585-8492-c626775a6e80" />

<img width="350" height="650" alt="Screenshot_20260117_122903" src="https://github.com/user-attachments/assets/19be3c51-0b9d-4433-a48c-1465e422aa4a" />

## Descrição 

Este respositório consiste no aplicativo do projeto Agriconnect. O aplicativo em questão foi desenvolvido para o a plataforma Android. 

## Arquitetura 

O projeto do aplicativo foi constuindo seguindo a arquitetura MVVM e padrões de Arquitetura Limpa. 

Arquitetura MVVM: 
Esta arquitetura é sugerida pela própria Google para projetos Android modernos. 
Resumidamente, ela consiste no View-Model-ViewModel. Onde a View é responsável apenas por renderizar a interface e capturar eventos do usuário. A camada de Model representa a estrutura de dados e o estado da aplicação. E, por fim, o ViewModel é quem faz o gerenciamento do estado da View e dos dados que devem aparecer nela, além de sobreviver a mudanças de configuração e se comunicar com a cadama de domínio. 

Arquitetura Limpa(Clean Architecture): 
Consiste num conjunto de boas práticas e separação e definição de responsabilidades claras entre as camadas dos componentes do sistema, em que as camadas de externas nao tem conhecimento das camadas mais internas. 

Outras boas práticas:
- Arquitetura de uma única Activity -> O fluxo de navegação é centralizado em uma única Activity.
- Camadas de UI, Domain e Data bem definidas.
- Injeção de Dependência -> Garante o baixo acoplamento entre as classes.

## Tecnologias 

- **Kotlin** -> Linguagem de programação recomendada pelo Google para programação de aplicativos Android atualmente.
- **Jetpack Compose** -> Kit de ferramentas moderno para construção de interfaces nativas de forma declarativa.
- **Coil** -> Biblioteca de carregamento de imagens leve e moderna.
- **Dagger e Hilt** -> Solução padrão para Injeção de Dependência.
- **Logging** -> Implementação de registros de eventos para monitoramento do comportamento do sistema e facilitação do debug em tempo de execução.
- **Retrofit** -> Cliente HTTP do tipo type-safe para Android, utilizado para consumir APIs e realizar a comunicação com o servidor de forma eficiente.
- **Navigation Compose** -> Biblioteca para gerenciamento de navegação entre telas de forma robusta e integrada ao Jetpack Compose.
- **Room DB** -> Biblioteca oficial do Android que atua como uma camada de abstração sobre o SQLite, facilitando a criação e o gerenciamento de bancos de dados locais dentro de aplicações Android.
- **Data Store** -> DataStore é uma solução de armazenamento de dados que permite armazenar pares de chave-valor ou objetos tipados com buffers de protocolo.

## Estruturas de Pastas

No sistema, as pastas são organizadas e dividas por módulos. Por exemplo, módulo de Produtos. Todas as interfaces de usuários, fonte de dados, lógicas, componentes relacionados aos Produtos encontram-se nela.
Além disso, todas as pastas de módulos são internamente separadas em ui, domain e data. Isso ajuda manter o tópico da separação de responsabilidades sempre em jogo. 

## Instalação e Execução 

Clonando o repositório 

```
git clone git@github.com:JoseGet/AppCareiro.git

```

Agora, bastar abrir na IDE Android Studio, fazer o sync, gerar o build e executar. 

## Situação atual do aplicativo: Encontra-se na fase interna de testes do GooglePlayStore. 
