# Aplicação de Blog Simples

## Visão Geral

Esta aplicação de blog foi desenvolvida utilizando a linguagem de programação Java, incorporando as funcionalidades
avançadas do Spring Framework, que é conhecido por seu amplo conjunto de recursos. Entre estes, destacam-se a segurança,
o Spring Data JPA para persistência de dados, o Thymeleaf para a geração de templates no lado do servidor e o Gradle,
escolhido como a ferramenta de construção do projeto. A aplicação é projetada para oferecer uma plataforma integrada que
permite aos usuários criar, gerir e visualizar postagens de blog, contando também com sistemas de autenticação e
autorização para garantir a segurança e o controle de acesso. É importante mencionar que esta aplicação representa um
projeto pessoal, elaborado com o objetivo de experimentar e testar diversas implementações. Neste contexto, para alguns,
ela pode apresentar características de overengineering, refletindo um nível de complexidade e sofisticação técnica que
vai além do que seria estritamente necessário para as suas funcionalidades básicas.

## Dependências

- Spring Framework para MVC, Segurança, Data JPA, Thymeleaf
- Gradle para gerenciamento de build
- Lombok para redução de código boilerplate
- Conector MySQL para interações com o banco de dados
- Flyway para migrações de banco de dados
- Várias utilidades para geração de UUID, slugificação de strings e gerenciamento de variáveis de ambiente

## Primeiros Passos

### Pré-requisitos

- JDK 17 ou mais recente
- Gradle
- Banco de dados MySQL

### Configurando o Ambiente

1. Clone o repositório: `git clone https://github.com/rafaelbiasi/Blog.git`
2. Certifique-se de que o MySQL está em execução e um banco de dados para a aplicação está criado.
3. Configure as propriedades da aplicação em `src/main/resources/application.properties` com suas credenciais de banco
   de dados e outras configurações específicas do ambiente.

### Executando a Aplicação

1. Navegue até o diretório raiz do projeto.
2. Execute `gradlew bootRun` para iniciar a aplicação.
3. Acesse a aplicação em `http://localhost:8080`.

## Estrutura da Aplicação

A aplicação segue uma abordagem modular dividida em vários pacotes:

- `config`: Contém classes de configuração para a aplicação, segurança e configuração de dados iniciais.
- `controller`: Controladores da Web que lidam com solicitações e respostas HTTP.
- `data`: Objetos de transferência de dados para transferir dados entre processos.
- `entity`: Entidades JPA representando tabelas do banco de dados.
- `facade`: Implementações do padrão Facade para abstrair subsistemas complexos.
- `facade.mapper`: Implementações para transformar entidades em modelos/DTOs e vice-versa.
- `model`: Modelos de domínio representando a lógica de negócios central.
- `repository`: Repositórios Spring Data JPA para acesso ao banco de dados.
- `service`: Camada de serviço contendo lógica de negócios.
- `specification`: Especificações para DSL de consulta.
- `transformer`: Classes para transformar entidades em modelos/DTOs e vice-versa.
- `util`: Classes de utilidade.

## Observação

Textos e imagens de exemplos gerados por IA.

## TODO

- ~~Testes de unidade~~
- ~~Dockerize (DockerFile/Compose)~~
- ~~Validações~~
- Internacionalização/localização
- Comentários
- Tags
- Busca
- Painel Administrativo
- Melhoria no layout (Novo layout, responsividade e acessibilidade)
- SEO
- Editor WYSIWYG

## Licença

MIT
