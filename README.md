# api-boleto
Segue os passos para instalação é execução do projeto
Requisitos: Java 8
            Maven
Entre no endereço https://github.com/fabiorener/api-boleto.git, clique no botão clone or download e escolha a opção Download Zip
Após baixar os fontes do projeto para um diretório escolhido na sua maquina, descompacte o zip usando a opção Extract Here.
Abra o prompt e acesse o diretorio api-boleto, dentro desse diretório execute os comandos do Maven abaixo:
            mvn clean
            mvn dependency:tree
            mvn package
            mvn spring-boot:run
            
O projeto esta configurado para o tomcat ser executado na porta default 8080.
Foi usado o swagger para documentar os serviços disponíveis, acessar o endereço http://localhost:8080/swagger-ui.html

O banco de dados utilizado é H2 e pode ser acessado pelo endereço http://localhost:8080/console
Não é necessário senha o user name deve ser sa

O projeto possui teste unitário que pode ser executado pelo maven mvn test
Também é possível executar os testes usando a pagina do swagger, abaixo segue exemplos dos formatos(json) que devem ser utilizados para cada serviço.

Create Bank slip (rest/bankslips) - Conforme documentação esse serviço responde pelo HTTP POST e espera um json no formato abaixo:

{
  "customer": "Empresa Teste",
  "dueDate": "2018-11-04",
  "totalInCents": 10000
}

List Bank slip (rest/bankslips) - Conforme documentação esse serviço responde pelo HTTP GET e não recebe parametros.

Details Bank slip (/rest/bankslips/{id}) - Conforme documentação esse serviço responde pelo HTTP GET deve ser enviado um id valido, caso contrário ser lançado uma exception.

Cancel Bank slip (/rest/bankslips/{id}) -  Conforme documentação esse serviço responde pelo HTTP DELETE deve ser enviado um id valido, caso contrário ser lançado uma exception.

Payment Bank slip (/rest/bankslips/{id}/payments) - Conforme documentação esse serviço responde pelo HTTP POST e espera um json no formato abaixo, deve ser enviado um id valido, caso contrário ser lançado uma exception.

{
  "paymentDate": "2018-11-05"
}

Com intuito de auxiliar nos testes integrados, foi criado uma classe RestClientUtil.java, ela possui recursos para execução de testes de todos os serviços criados, para isso é necessário acertar os parametros com ids validos enviados para os serviços.

            
            


