# 🚗 Sistema de Aluguel de Carros

API e paginas web para gerenciamento de aluguel de automoveis, com cadastro de clientes/agentes, autenticacao JWT e fluxo de pedidos.

## 📌 Visao geral

Este projeto foi desenvolvido para a disciplina de Laboratorio de Desenvolvimento de Software, seguindo o padrao MVC com Micronaut.

Funcionalidades principais:
- Cadastro e gerenciamento de `Cliente`, `Agente`, `Automovel` e `Pedido`
- Login de cliente e agente com JWT
- Pagina web de login em `/auth/login`
- Dashboards HTML em `/pages/dashboard-cliente` e `/pages/dashboard-agente`
- Documentacao da API com Swagger/OpenAPI

## 🛠️ Stack

- Java 21
- Micronaut 4.6.2
- Gradle (Kotlin DSL)
- PostgreSQL
- JPA/Hibernate
- Micronaut Security JWT
- Micronaut Views + Thymeleaf

## ✅ Requisitos

- JDK 21
- PostgreSQL rodando localmente
- Banco criado: `carrental`

## ⚙️ Configuracao

As configuracoes principais estao em `src/main/resources/application.yml`.

Datasource atual:
- URL: `jdbc:postgresql://localhost:5432/carrental`
- Usuario: `lucas`
- Senha: `root`

> 💡 Recomendacao: para uso fora do ambiente local, mover credenciais e segredo JWT para variaveis de ambiente.

## ▶️ Como executar

1. Clone o repositorio.
2. Garanta que o PostgreSQL esteja ativo e o banco `carrental` exista.
3. Execute a aplicacao:

```bash
./gradlew run
```

## 🔗 URLs importantes

- API base: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui`
- Login web: `http://localhost:8080/auth/login`

## 🔐 Autenticacao

Endpoints de login:
- `POST /auth/login/cliente`
- `POST /auth/login/agente`

Body esperado:

```json
{
  "login": "usuario",
  "senha": "123456"
}
```

Resposta de sucesso:

```json
{
  "token": "<jwt>",
  "tipoUsuario": "CLIENTE"
}
```

ou

```json
{
  "token": "<jwt>",
  "tipoUsuario": "AGENTE"
}
```

Para acessar rotas protegidas, enviar o header:

```text
Authorization: Bearer <jwt>
```

## 📡 Endpoints principais

- `GET/POST/PUT/DELETE /clientes`
- `GET/POST/PUT/DELETE /agentes`
- `GET/POST/PUT/DELETE /automovel`
- `POST /pedido?clienteId=...`
- `GET /pedido?clienteId=...`
- `PUT /pedido/{id}/status?agenteId=...`

## 🖥️ Paginas web

- `GET /auth/login` -> tela de login
- `GET /pages/dashboard-cliente` -> dashboard cliente
- `GET /pages/dashboard-agente` -> dashboard agente

## 🧱 Estrutura resumida

- `src/main/java/com/carrental/controller` -> controladores HTTP
- `src/main/java/com/carrental/service` -> regras de negocio
- `src/main/java/com/carrental/repository` -> acesso a dados
- `src/main/java/com/carrental/model` -> entidades JPA
- `src/main/resources/views` -> paginas HTML (Thymeleaf)
- `src/main/resources/application.yml` -> configuracao da aplicacao

## 🚀 Melhorias recomendadas

- Padronizar tratamento global de excecoes para todas as rotas
- Aumentar cobertura de testes automatizados
- Ajustar politicas de acesso no `intercept-url-map` conforme os perfis
- Externalizar secrets e credenciais por ambiente

## 👥 Participantes

| Nome | GitHub |
|------|--------|
| Matheus Ruas | [@MatheusRuas77](https://github.com/MatheusRuas77) |
| Caio Resende | [@CaioSResende](https://github.com/CaioSResende) |
| Lucas Ferreira | [@iTsLJ](https://github.com/iTsLJ) |
| Gabriel Starling | [@gabrielstarling1](https://github.com/gabrielstarling1) |
