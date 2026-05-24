# Sistema de Persistência com JDBC e Padrão DAO

Este repositório contém a implementação de um sistema de persistência de dados utilizando Java Database Connectivity (JDBC) e o padrão de projeto Data Access Object (DAO). O objetivo principal deste projeto acadêmico é isolar completamente a lógica de acesso a dados da camada de aplicação, garantindo baixo acoplamento, alta coesão e facilidade de manutenção no gerenciamento de entidades em um banco de dados relacional (MySQL).

## Arquitetura e Padrões de Projeto

A arquitetura do projeto foi estruturada seguindo os princípios de SOLID e padrões de projeto (Design Patterns) consagrados:

1. **Data Access Object (DAO):** Criação de interfaces abstratas (`SellerDao`, `DepartmentDao`) que definem as operações de CRUD. A implementação concreta é feita na camada de infraestrutura (`SellerDaoJDBC`), ocultando os detalhes operacionais do driver de banco de dados da aplicação principal.
2. **Abstract Factory:** Implementação da classe `DaoFactory` responsável por injetar as dependências de conexão e instanciar os DAOs concretos. Isso elimina a necessidade de acoplamento direto da classe `Program` com as implementações JDBC.
3. **Encapsulamento e Agregação:** Modelagem de entidades orientadas a objetos (`Seller` e `Department`) com relacionamentos estruturados (um vendedor possui um departamento), mapeando chaves estrangeiras diretamente para composições de objetos em memória.

## Funcionalidades Implementadas (CRUD - Tabela Seller)

O mapeamento objeto-relacional manual via JDBC cobre os seguintes métodos de persistência:

* **`findById(Integer id)`**: Executa uma consulta com `INNER JOIN` para recuperar os dados do vendedor e de seu respectivo departamento associado, instanciando-os corretamente na memória a partir do `ResultSet`.
* **`findDepartment(Department department)`**: Retorna uma coleção (`List<Seller>`) de todos os funcionários alocados em um departamento específico. Utiliza uma estrutura de controle baseada em mapeamento (`Map<Integer, Department>`) para garantir o reaproveitamento de instâncias do departamento, evitando redundância de memória.
* **`findAll()`**: Recupera todos os registros da tabela ordenados por nome, gerenciando dinamicamente a vinculação de múltiplos departamentos sem duplicação de objetos na memória do ecossistema Java.
* **`insert(Seller obj)`**: Realiza a persistência de uma nova instância no banco de dados através de `PreparedStatement` e comandos de escrita (`executeUpdate`).
* **`update(Seller obj)`**: Atualiza de forma atômica ou completa os atributos de um registro existente no banco utilizando referenciamento por ID no bloco `WHERE`.
* **`deleteById(Integer id)`**: Remove fisicamente um registro da tabela por meio de sua chave primária.

## Detalhes de Implementação e Tratamento de Exceções

* **Gerenciamento de Recursos**: Todo o ciclo de vida das conexões, `Statement` e `ResultSet` é gerenciado rigorosamente com blocos `try-catch-finally`, garantindo o fechamento dos recursos no banco e prevenindo vazamentos de memória (memory leaks).
* **Camada de Exceções customizada**: Exceções nativas do ecossistema SQL (`SQLException`) são capturadas e encapsuladas em uma exceção de tempo de execução personalizada (`DbException`), desacoplando a assinatura dos métodos da interface do pacote `java.sql`.
* **Manipulação de Datas**: Conversão precisa entre tipos temporais da API padrão do Java (`java.util.Date`) obtidos através de parsing textual (`SimpleDateFormat`) e os formatos aceitos pelo driver JDBC (`java.sql.Date`).

## Tecnologias e Ambiente de Desenvolvimento

* **Linguagem:** Java (JDK 21)
* **Ambiente de Execução:** Ubuntu Linux
* **IDE:** IntelliJ IDEA
* **Banco de Dados:** MySQL Server
* **Controle de Versão:** Git

## Estrutura do Repositório

```text
src/
├── application/
│   └── Program.java             # Classe principal contendo os scripts de teste dos métodos
├── db/
│   ├── DB.java                  # Classe utilitária para conexão/desconexão e fechamento de fluxos
│   └── DbException.java         # Exceção personalizada da camada de dados
└── model/
    ├── dao/
    │   ├── DaoFactory.java      # Fábrica de objetos DAO
    │   ├── DepartmentDao.java   # Interface de persistência para Department
    │   └── SellerDao.java       # Interface de persistência para Seller
    │   └── impl/
    │       └── SellerDaoJDBC.java # Implementação concreta das operações utilizando JDBC
    └── entities/
        ├── Department.java      # Classe da entidade Departamento
        └── Seller.java          # Classe da entidade Vendedor
