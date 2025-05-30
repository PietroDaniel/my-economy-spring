# My Economy - Backend

Sistema de gerenciamento de finanças pessoais desenvolvido com Spring Boot 3.5.0.

## 🚀 Tecnologias

- Java 21
- Spring Boot 3.5.0
- Spring Security com JWT
- Spring Data JPA
- MySQL
- Maven
- Lombok

## 📋 Pré-requisitos

- JDK 21 ou superior
- Maven 3.6+
- MySQL 8.0+
- IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code)

## 🔧 Configuração do Ambiente

1. Clone o repositório:
```bash
git clone [url-do-repositório]
cd my-economy-spring
```

2. Configure o banco de dados MySQL:
   - Crie um banco de dados chamado `my_economy` (opcional, a aplicação cria automaticamente)
   - Ajuste as configurações de conexão em `src/main/resources/application.yml`

3. Configure as variáveis de ambiente (opcional):
```bash
JWT_SECRET=seu-segredo-jwt
```

## 🏃‍♂️ Executando o Projeto

1. Build do projeto:
```bash
mvn clean install
```

2. Execute a aplicação:
```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080/api`

## 🔐 Segurança

- Autenticação baseada em JWT
- Endpoints protegidos por autenticação
- Senha criptografada com BCrypt
- Renovação automática de token

## 📚 Principais Funcionalidades

### Usuários
- Cadastro de usuário
- Autenticação
- Atualização de dados pessoais

### Despesas
- Cadastro de despesas
- Listagem por período
- Categorização
- Relatórios mensais

### Limites Mensais
- Definição de orçamento mensal
- Acompanhamento de gastos
- Alertas de limite

## 🔗 Endpoints Principais

### Autenticação
- POST `/api/auth/register` - Registro de novo usuário
- POST `/api/auth/login` - Login

### Despesas
- POST `/api/despesas` - Criar nova despesa
- GET `/api/despesas` - Listar despesas
- GET `/api/despesas/mensal/{ano}/{mes}` - Despesas por mês
- PUT `/api/despesas/{id}` - Atualizar despesa
- DELETE `/api/despesas/{id}` - Remover despesa

### Limites
- POST `/api/limites` - Definir limite mensal
- GET `/api/limites/atual` - Consultar limite atual
- GET `/api/limites/status` - Status do orçamento

## 📦 Estrutura do Projeto

```
src/main/java/com/myeconomy/
├── api/
│   └── MyEconomyApiApplication.java
├── config/
├── model/
├── repository/
├── security/
└── service/
```

## 🔍 Monitoramento

- Logs detalhados configurados
- Métricas do Hikari Connection Pool
- Debug para SQL em ambiente de desenvolvimento

## 📝 Notas de Desenvolvimento

- O sistema usa o padrão RESTful
- Implementa boas práticas de segurança
- Validações em diferentes camadas
- Tratamento adequado de exceções

## 🤝 Contribuindo

1. Faça o fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.