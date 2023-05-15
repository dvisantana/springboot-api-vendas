
# springboot-api-vendas

![logo-xbrain](https://media.licdn.com/dms/image/C560BAQGLt9y-6EhXNQ/company-logo_200_200/0/1634563931271?e=2147483647&v=beta&t=onFUfyl6UUqlaPoLxVUjXPLVWoPIVR6dcD4M0YqTwsI)

> API de Vendas para teste prático de vaga de estágio X-Brain

## EndPoints
### Necessários/Tarefas
- **Criar uma nova venda:** `localhost:8080/vendas/cadastrar`
- **Retornar lista de vendedores:** `localhost:8080/vendedores/resumo?dataInicio=&dataFim=`

### Todos
Vendas:
- **Listar Vendas (Todas):** `localhost:8080/vendas/listar`
- **Listar Vendas (Id):** `localhost:8080/vendas/{id}`
- **Criar Venda:** `localhost:8080/vendas/cadastrar`
- **Modificar/Alterar Venda:** `localhost:8080/vendas/alterar/{id}`
- **Deletar Venda:** `localhost:8080/vendas/deletar/{id}`

Vendedores
- **Listar Vendedores (Todos):** `localhost:8080/vendedores/listar`
- **Listar Vendedores (Id):** `localhost:8080/vendedores/{id}`
- **Resumo Vendedores (Nome, Total, Media):** `localhost:8080/vendedores/resumo?dataInicio=&dataFim=`
- **Cadastrar Vendedor:** `localhost:8080/vendedores/cadastrar`
- **Modificar/Alterar Vendedor:** `localhost:8080/vendedor/alterar/{id}`
- **Deletar Vendedor:** `localhost:8080/vendedor/deletar/{id}`
