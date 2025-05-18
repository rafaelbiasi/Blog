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

## Observação

Textos e imagens de exemplos gerados por IA.

## TODO

- ~~Dockerize (DockerFile/Compose)~~
- ~~Validações~~
- ~~Internacionalização/localização~~
- ~~Comentários~~
- ~~Melhor estruturação dos templates~~
- ~~Painel Administrativo~~
- Melhoria no layout (Novo layout, responsividade, acessibilidade, SEO)
- Tags
- Busca
- Editor WYSIWYG (talvez Markdown)

## Licença

MIT
