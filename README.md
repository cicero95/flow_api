# ERP FLOW
# Sistema de ERP FLOW web desenvolvido em Java API REST


> <h4> O ERP FLOW  tem como objetivo , gerenciar, organizar e registrar o trabalho de uma empresa aplicando regras de negócios e parâmetros definidos para atender os processos e tarefas diárias feitas pelos funcionários das empresas.</h4>

* [Maven](https://maven.apache.org/) - Dependency Management
* [JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard       Edition Development Kit 
* [Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring   A pplications
* [MySQL](https://dev.mysql.com/) - Open-Source Relational Database Management System
* [Git](https://git-scm.com/) - Free and Open-Source distributed version control system 


```
.
├── Spring Elements
├── src
│   └── main
│       └── java
│           ├── br.com.flow_api     
            ├── br.com.flow_api.configData
            ├── br.com.flow_api.constants
│           ├── br.com.flow_api.enums
            ├── br.com.flow_api.exceptions
            ├── br.com.flow_api.external
│           ├── br.com.flow_api.product
│           ├── br.com.flow_api.supplier
│           
├── src
│   └── main
│       └── resources
│           └── static              
│           ├── templates            
│           ├── application.properties
│            
├── src
│   └── test
│       └── java
├── JRE System Library
├── Maven Dependencies
├── bin
├── logs
│   └── application.log
├── src
├── target
│   └──application-0.0.1-SNAPSHOT
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## pacotes
- `entity` — to hold our entities;
- `repositories` — to communicate with the database;
- `services` — to hold our business logic;
- `controllers` — to listen to the user;
- `resources/` - Contains all the static resources, templates and property files.
- `resources/static` - contains static resources such as css, js and images.
- `resources/templates` - contains server-side templates which are rendered by Spring.
- `resources/application.properties` - It contains application-wide properties. 
  Spring reads the properties defined in this file to configure your application. 
  You can define server’s default port, server’s context path, database URLs etc, in this file.
- `test/` - contains unit and integration tests
- `pom.xml` - contains all the project dependencies
 
## Running the application locally
mvn spring-boot:run
## End point 
http://localhost:8080/api/v1/{recursos}


# Modulos
- Módulo de Produtos
- Módulo de Fornecedores
- Módulo de Controle de Estoque
- Módulo de Fiscal
- Módulo de Compras
- Módulo de Recursos Humanos(RH)
- Módulo de CRM
- Módulo de Projetos
- Módulo de Business Inteligente(Atualização futura/ Recurso Premium)


# Recursos
- Cadastro produtos/clientes/fornecedor
- Controle de estoque
- Gerenciar comandas
- Realizar venda
- Controle de fluxo de caixa
- Controle de pagar e receber
- Venda com cartões(atualizações futuras/ Recurso Premium)
- Gerenciar permissões de usuários por grupos
- Cadastrar novas formas de pagamentos
- Relatórios

## Tecnologias
- [x] Spring Boot
- [x] MySQL(Database)
- [x] Spring Data
- [x] Spring Web
- [x] Spring devtools

# Requisitos
- Java v1.8
- Spring v2.3.4
- MySQL  v8.0.21
- tomCat v9.xx

# Instalação
    Para instalar o sistema, você deve criar o banco de dado "flow_db" no mysql e configurar o arquivo application.properties
    com os dados do seu usuário root do mysql e rodar o projeto pelo Eclipse ou gerar o jar do mesmo e execultar.




