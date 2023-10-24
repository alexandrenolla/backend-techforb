# backend-techforb
# Projeto Java Spring Boot Fullstack com MySQL

Este é o projeto back-end desenvolvido em Java Spring Boot para o desafio técnico da techforb. Este projeto fornece API REST que é acessada pelo front-end Angular para obter e enviar dados. O banco de dados utilizado é o MySQL.

## Pré-requisitos

Antes de começar, você precisa ter o Java JDK, o Apache Maven e o MySQL instalados em sua máquina.

- **Java JDK:** [https://www.oracle.com/java/technologies/javase-jdk15-downloads.html](https://www.oracle.com/java/technologies/javase-jdk15-downloads.html)
- **Apache Maven:** [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
- **MySQL:** Certifique-se de que o MySQL está instalado e em execução. O código Java deste projeto gera automaticamente o esquema no MySQL.

## Configuração do Banco de Dados

O projeto está configurado para se conectar a um banco de dados MySQL. As informações de configuração do banco de dados podem ser encontradas no arquivo `application.properties` no diretório `src/main/resources`.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nome-do-seu-banco-de-dados
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
```
## Executando o Projeto

Siga estas etapas para executar o projeto em sua máquina local:

1. Clone o repositório:

git clone [URL DO REPOSITÓRIO]

2. Navegue até o diretório do projeto Java:

cd nome-do-diretorio-do-projeto-java

3. Compile o projeto usando o Maven:

mvn clean install

4. Inicie o servidor Spring Boot:

mvn spring-boot:run

5. O servidor Spring Boot será iniciado e a API estará acessivel em http://localhost:8080.

Certifique-se de que o servidor MySQL esteja em execução e configurado corretamente antes de usar o aplicativo Angular. O front-end Angular está configurado para se comunicar com o back-end por meio de URLs específicas.
