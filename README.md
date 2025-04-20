# 💉 Hackathon - Microsserviço de Carteira de Vacinação

Este repositório faz parte do projeto de melhoria do SUS, desenvolvido como parte da pós-graduação em Arquitetura e Desenvolvimento Java - FIAP.

Microsserviço responsável pelo gerenciamento da **Carteira Digital de Vacinação**, permitindo o registro, consulta e atualização das vacinas aplicadas aos pacientes.

---

## 📁 Estrutura do Projeto

- **Java 21**
- **Spring Boot 3**
- **MongoDB**
- **Swagger (springdoc-openapi)**
- **Arquitetura Hexagonal (Ports & Adapters)**

---

## 🚀 Como executar o projeto

### Pré-requisitos

- [Docker e Docker Compose](https://www.docker.com/)
- Java 21
- Maven 3+

### Subindo o MongoDB com Docker Compose

Na raiz do projeto, execute:

```bash
docker-compose up -d
```

Isso iniciará o MongoDB localmente na porta `27017` (ou conforme definido no `docker-compose.yml`).

### Rodando o microsserviço

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8080
```

---

## 📌 Endpoints Principais

Acesse a documentação completa da API:

```
http://localhost:8080/swagger-ui.html
```

### Exemplos de endpoints:

- `POST /v1/carteira` - Criar nova carteira de vacinação
- `GET /v1/carteira/{cpf}` - Buscar carteira por CPF
- `PUT /v1/carteira/{cpf}/vacina` - Atualizar vacina aplicada
- `POST /v1/carteira/{cpf}/vacina` - Adicionar nova vacina aplicada
- `DELETE /v1/carteira/{cpf}` - Remover carteira de vacinação

---

## 🛠️ Tecnologias Utilizadas

- **Spring Boot**
- **Spring Data MongoDB**
- **WebStruct**
- **Springdoc OpenAPI (Swagger)**
- **Docker**
- **JUnit 5 / Mockito**

---

## 🧪 Testes

Os testes unitários foram implementados com JUnit e Mockito.

Para rodar os testes:

```bash
./mvnw test
```

---

## 👨‍💻 Autores

**Renan**  
[GitHub - MrRenan](https://github.com/MrRenan)

**Samuel**  
[GitHub - SamuelXIsidorio](https://github.com/SamuelXIsidorio)

**Renato**  
[GitHub - urpdrum](https://github.com/urpdrum)

---

## 📄 Licença

Este projeto é apenas para fins acadêmicos. Nenhum uso comercial autorizado.