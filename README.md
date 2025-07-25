# Kalix Workshop - Loan application - Java

## Prerequisite
Java 11 or later<br>
Apache Maven 3.6 or higher<br>
[Akka CLI](https://doc.akka.io/operations/cli/installation.html) <br>
Docker 20.10.14 or higher (client and daemon)<br>
cURL<br>
IDE / editor<br>

## Create kickstart maven project

```shell
mvn archetype:generate \
-DarchetypeGroupId=io.kalix \
-DarchetypeArtifactId=kalix-maven-archetype \
-DarchetypeVersion=1.5.11
```
Define value for property 'groupId': `io.kx`<br>
Define value for property 'artifactId': `loan-application`<br>
Define value for property 'version' 1.0-SNAPSHOT: :<br>
Define value for property 'package' io.kx: : `io.kx.loanapp`<br>

## Import generated project in your IDE/editor
<i><b>Delete all proto files after done</b></i>

## Update main class
In `pom.xml` in `<mainClass>io.kx.loanapp.Main</mainClass>` replace `io.kx.loanapp.Main` with `io.kx.Main`

# Loan application service

## Define API data structure and endpoints (GRPC)
Create `io/kx/loanapp/api` folder in `src/main/proto` folder. <br>
Create `loan_app_api.proto` in `src/main/proto/io/kx/loanapp/api` folder. <br>
Create: <br>
- headers
- state
- commands
- service

<i><b>Tip</b></i>: Check content in `step-1` git branch

## Define persistence (domain) data structure  (GRPC)
Create `io/kx/loanapp/doman` folder in `src/main/proto` folder. <br>
Create `loan_app_domain.proto` in `src/main/proto/io/kx/loanapp/domain` folder. <br>
Create: <br>
- headers
- state
- events

<i><b>Tip</b></i>: Check content in `step-1` git branch
## Add codegen annotations in API data structure and endpoints (GRPC)
In `src/main/proto/io/kx/loanapp/api/loan_app_api.proto` add Kalix codegen annotations to GRPC service
```protobuf
service LoanAppService {
  option (kalix.service).acl.allow = { principal: ALL };
  option (kalix.codegen) = {
    event_sourced_entity: {
      name: "io.kx.loanapp.domain.LoanAppEntity"
      type_id: "loanapp"
      state: "io.kx.loanapp.domain.LoanAppDomainState"
      events: [
        "io.kx.loanapp.domain.Submitted",
        "io.kx.loanapp.domain.Approved",
        "io.kx.loanapp.domain.Declined"
      ]
    }
  };
}
```
<i><b>Note</b></i>: `event_sourced_entity.name` has to be a unique name
## Compile maven project to trigger codegen
```shell
mvn compile
```
Compile will generate help classes (`target/generated-*` folders) and skeleton classes<br><br>
Business logic:<br>
`src/main/java/io/kx/Main`<br>
`src/main/java/io/kx/loanapp/domain/LoanAppEntity`<br>
<br>
Unit tests:<br>
`src/test/java/io/kx/loanapp/domain/LoanAppEntityTest`<br>
Integration tests:<br>
`src/it/java/io/kx/loanapp/api/LoanAppEntityIntegrationTest`<br>

<i><b>Tip</b></i>: If required reimport/re-sync project in your IDE
## Implement entity skeleton class
Implement `src/main/java/io/kx/loanapp/domain/LoanAppEntity` class <br>
<i><b>Tip</b></i>: Check content in `step-1` git branch

## Implement unit test
Implement  `src/test/java/io/kx/loanapp/domain/LoanAppEntityTest` class<br>
<i><b>Tip</b></i>: Check content in `step-1` git branch

## Run unit test
```shell
mvn test
```
## Implement integration test
Implement `src/it/java/io/kx/loanapp/api/LoanAppEntityIntegrationTest` class<br>
<i><b>Tip</b></i>: Check content in `step-1` git branch

## Run integration test
```shell
mvn -Pit verify
```

<i><b>Note</b></i>: Integration tests uses [TestContainers](https://www.testcontainers.org/) to span integration environment so it could require some time to download required containers.
Also make sure docker is running.

## Run locally
Start the service:
```shell
mvn compile kalix:runAll
```

Start the local console:
```shell
akka local console
```

## Test service locally
Submit loan application:
```shell
curl -XPOST -d '{
  "client_id": "12345",
  "client_monthly_income_cents": 60000,
  "loan_amount_cents": 20000,
  "loan_duration_months": 12
}' http://localhost:9000/loanapp/1 -H "Content-Type: application/json"
```

Get loan application:
```shell
curl -XGET http://localhost:9000/loanapp/1 -H "Content-Type: application/json"
```

Approve:
```shell
curl -XPUT http://localhost:9000/loanapp/1/approve -H "Content-Type: application/json"
```

## Package
```shell
mvn install
```
<i><b>Note</b></i>:Copy the image tag to be used in deploy
## Register for Akka account or Login with an existing account
[Register](https://console.akka.io/register)

## Akka CLI
Login (need to be logged in the Akka Console in web browser):
```shell
akka auth login
```
Create new project:
```shell
akka projects new workshop --region <REGION> --organization <ORGANIZATION>
```
<i><b>Note</b></i>: Replace `<REGION>` with a desired region and `<ORGANIZATION>` your organization 

Set project:
```shell
akka config set project workshop
```
## Deploy service
```shell
akka service deploy loan-application <IMAGE TAG> --push --classic
```
<i><b>Note</b></i>: Replace `IMAGE TAG` with your image tag from `mvn install`

List services:
```shell
akka services list
```
```text
NAME               AGE   INSTANCES   STATUS   IMAGE TAG                     
loan-application   46s   3           Ready    1.0-SNAPSHOT-20250704154412 
```
## Expose service
```shell
akka services expose loan-application
```
Result:
```text
Service 'loan-application' was successfully exposed at: <EXPOSED HOST>
```
## Test service in production
Submit loan application:
```shell
curl -XPOST -d '{
  "client_id": "12345",
  "client_monthly_income_cents": 60000,
  "loan_amount_cents": 20000,
  "loan_duration_months": 12
}' https://<EXPOSED HOST>/loanapp/1 -H "Content-Type: application/json"
```
Get loan application:
```shell
curl -XGET https://<EXPOSED HOST>/loanapp/1 -H "Content-Type: application/json"
```
Approve:
```shell
curl -XPUT https://<EXPOSED HOST>/loanapp/1/approve -H "Content-Type: application/json"
```

## Explore Akka console
(Akka console)[https://console.akka.io/]


# Loan application processing service

## Define API data structure and endpoints (GRPC)
Create `io/kx/loanproc/api` folder in `src/main/proto` folder. <br>
Create `loan_proc_api.proto` in `src/main/proto/io/kx/loanproc/api` folder. <br>
Create: <br>
- state
- commands
- service

<i><b>Tip</b></i>: Check content in `step-2` git branch

## Define persistence (domain) data structure  (GRPC)
Create `io/kx/loanproc/domain` folder in `src/main/proto` folder. <br>
Create `loan_proc_domain.proto` in `src/main/proto/io/kx/loanproc/domain` folder. <br>
Create: <br>
- state
- events

<i><b>Tip</b></i>: Check content in `step-2` git branch
## Add codegen annotations in API data structure and endpoints (GRPC)
In `src/main/proto/io/kx/loanproc/api/loan_proc_api.proto` add AkkaServerless codegen annotations to GRPC service
```protobuf
service LoanProcService {
  option (kalix.service).acl.allow = { principal: ALL };
  option (kalix.codegen) = {
    event_sourced_entity: {
      name: "io.kx.loanproc.domain.LoanProcEntity"
      type_id: "loanproc"
      state: "io.kx.loanproc.domain.LoanProcDomainState"
      events: [
        "io.kx.loanproc.domain.ReadyForReview",
        "io.kx.loanproc.domain.Approved",
        "io.kx.loanproc.domain.Declined"
      ]
    }
  };
}
```
<i><b>Note</b></i>: `event_sourced_entity.name` has to be a unique name
## Compile maven project to trigger codegen
```shell
mvn compile
```

Compile will generate help classes (`target/generated-*` folders) and skeleton classes<br><br>
Business logic:<br>
`src/main/java/io/kx/loanproc/domain/LoanProcEntity`<br>
<br>
Unit tests:<br>
`src/test/java/io/kx/loanproc/domain/LoanProcEntityTest`<br>
Integration tests:<br>
`src/it/java/io/kx/loanproc/api/LoanProcEntityIntegrationTest`<br>

<i><b>Tip</b></i>: If required reimport project in your IDE

## Update Main class
In `src/main/java/io/kx/Main` you need to add new entity component (`LoanProcEntity`):
```java
return KalixFactory.withComponents(LoanAppEntity::new, LoanProcEntity::new);
```
## Implement entity skeleton class
Implement `src/main/java/io/kx/loanproc/domain/LoanProcEntity` class<br>
<i><b>Tip</b></i>: Check content in `step-2` git branch

## Implement unit test
Implement `src/test/java/io/kx/loanproc/domain/LoanProcEntityTest` class<br>
<i><b>Tip</b></i>: Check content in `step-2` git branch

## Run unit test
```shell
mvn test
```
## Implement integration test
Implement `src/it/java/io/kx/loanproc/api/LoanProcEntityIntegrationTest` class<br>
<i><b>Tip</b></i>: Check content in `step-2` git branch

## Run integration test
```shell
mvn -P verify
```

<i><b>Note</b></i>: Integration tests uses [TestContainers](https://www.testcontainers.org/) to span integration environment so it could require some time to download required containers.
Also make sure docker is running.

## Run locally
Start the service:
```shell
mvn compile kalix:runAll
```

Start the local console:
```shell
akka local console
```

## Test service locally
Start processing:
```shell
curl -XPOST -d '{
  "client_monthly_income_cents": 60000,
  "loan_amount_cents": 20000,
  "loan_duration_months": 12
}' http://localhost:9000/loanproc/1 -H "Content-Type: application/json"
```

Get loan processing:
```shell
curl -XGET http://localhost:9000/loanproc/1 -H "Content-Type: application/json"
```

Approve:
```shell
curl -XPUT http://localhost:9000/loanproc/1/approve -H "Content-Type: application/json"
```

## Package
```shell
mvn install
```
<i><b>Note</b></i>:Copy the image tag to be used in deploy
## Deploy (update) service
```shell
akka service deploy loan-application <IMAGE TAG> --push --classic
```
<i><b>Note</b></i>: Replace `IMAGE TAG` with your image tag from `mvn install`

## Test service in production
Start processing:
```shell
curl -XPOST -d '{
  "client_monthly_income_cents": 60000,
  "loan_amount_cents": 20000,
  "loan_duration_months": 12
}' https://<EXPOSED HOST>/loanproc/1 -H "Content-Type: application/json"
```

Get loan processing:
```shell
curl -XGET https://<EXPOSED HOST>/loanproc/1 -H "Content-Type: application/json"
```

Approve:
```shell
curl -XPUT https://<EXPOSED HOST>/loanproc/1/approve -H "Content-Type: application/json"
```

## Explore Akka console
(Akka console)[https://console.akka.io/]



## Create a view
Create `io/kx/loanproc/view` folder in `src/main/proto` folder. <br>
Create `loan_proc_by_status_view.proto` in `src/main/proto/io/kx/loanproc/view` folder. <br>
Create: <br>
- state
- request/response
- service

<i><b>Note</b></i>: `SELECT` result alias `AS results` needs to correspond with `GetLoanProcByStatusResponse` parameter name `repeated LoanProcViewState results`<br>
<i><b>Note</b></i>: Currently `enums` are not supported as query parameters ([issue 1141](https://github.com/lightbend/kalix-proxy/issues/1141)) so enum `number` value is used for query<br>
<i><b>Tip</b></i>: Check content in `step-3` git branch

## Compile maven project to trigger codegen for views
```shell
mvn compile
```

Compile will generate help classes (`target/generated-*` folders) and skeleton classes<br><br>

`src/main/java/io/kx/loanproc/view/LoanProcByStatusView`<br>

In `src/main/java/io/kx/Main` you need to add view (`LoanProcByStatusView`) initialization:
```java
    return KalixFactory.withComponents(LoanAppEntity::new, LoanProcEntity::new, LoanProcByStatusView::new);
```

## Implement view LoanProcByStatusView skeleton class
Implement `src/main/java/io/kx/loanproc/view/LoanProcByStatusView` class<br>
<i><b>Tip</b></i>: Check content in `step-3` git branch

## Unit test

Because of the nature of views only Integration tests are done.

## Create integration tests for view
1. Copy `io/kx/loanproc/view/LoanProcEntityIntegrationTest` class to `io/kx/loanproc/view/LoanProcViewIntegrationTest`
2. Remove all methods annotated with `@Test`
3. Add test case
```java
@Test
public void viewTest() throws Exception {
...  
```
<i><b>Tip</b></i>: Check content in `step-3` git branch

## Run integration test
```shell
mvn verify -Pit
```

<i><b>Note</b></i>: Integration tests uses [TestContainers](https://www.testcontainers.org/) to span integration environment so it could require some time to download required containers.
Also make sure docker is running.


## Package
```shell
mvn install
```
<i><b>Note</b></i>:Copy the image tag to be used in deploy
## Deploy (update) service
```shell
akka service deploy loan-application <IMAGE TAG> --push --classic
```
<i><b>Note</b></i>: Replace `IMAGE TAG` with your image tag from `mvn install`


## Test service in production
Get loan processing by status:
```shell
curl -XPOST -d {"status_id":2} https://<EXPOSED HOST>/loanproc/views/by-status -H "Content-Type: application/json"
```

## Explore Akka console
(Akka console)[https://console.akka.io/]


# Event driven communication

## Action for submitted event (Loan application service -> Loan application processing service)
Create `io/kx/loanapp/action` folder in `src/main/proto` folder. <br>
Create `loan_app_eventing_to_proc_action.proto` in `src/main/proto/io/kx/loanapp/action` folder. <br>
Create: <br>
- service

<i><b>Tip</b></i>: Check content in `step-4` git branch

## Action for approved & declined processing event (Loan application processing service -> Loan application service)
Create `io/kx/loanproc/action` folder in `src/main/proto` folder. <br>
Create `loan_proc_eventing_to_app_action.proto` in `src/main/proto/io/kx/loanproc/action` folder. <br>
Create: <br>
- service

<i><b>Tip</b></i>: Check content in `step-4` git branch

## Compile maven project to trigger codegen for action
```shell
mvn compile
```
Compile will generate help classes (`target/generated-*` folders) and skeleton classes<br><br>

`src/main/java/io/kx/loanapp/action/LoanAppEventingToProcAction`<br>
`src/main/java/io/kx/loanproc/action/LoanProcEventingToAppAction`<br>

In `src/main/java/io/kx/Main` you need to add view (`LoanAppEventingToProcAction` & `LoanProcEventingToAppAction`) initialization:
```java
    return KalixFactory.withComponents(LoanAppEntity::new, LoanProcEntity::new, LoanAppEventingToProcAction::new, LoanProcByStatusView::new, LoanProcEventingToAppAction::new);
```
## Implement view LoanAppEventingToProcAction skeleton class
Implement `src/main/java/io/kx/loanapp/action/LoanAppEventingToProcAction` class<br>
<i><b>Tip</b></i>: Check content in `step-4` git branch

## Implement view LoanProcEventingToAppAction skeleton class
Implement `src/main/java/io/kx/loanproc/action/LoanProcEventingToAppAction` class<br>
<i><b>Tip</b></i>: Check content in `step-4` git branch

## System integration tests (multiple services)
In `src/it/java/io/kx` folder create new class `SystemIntegrationTest`.
<i><b>Tip</b></i>: Check content in `step-4` git branch

## Run integration test
```shell
mvn verify -Pit
```

<i><b>Note</b></i>: Integration tests uses [TestContainers](https://www.testcontainers.org/) to span integration environment so it could require some time to download required containers.
Also make sure docker is running.

## Package
```shell
mvn install
```
<i><b>Note</b></i>:Copy the image tag to be used in deploy
## Deploy (update) service
```shell
akka service deploy loan-application <IMAGE TAG> --push --classic
```
<i><b>Note</b></i>: Replace `IMAGE TAG` with your image tag from `mvn install`

## Test service in production
Submit loan application:
```shell
curl -XPOST -d '{
  "client_id": "123456",
  "client_monthly_income_cents": 60000,
  "loan_amount_cents": 20000,
  "loan_duration_months": 12
}' https://<EXPOSED HOST>/loanapp/2 -H "Content-Type: application/json"
```
Approve loan processing:
```shell
curl -XPUT -d '{
"reviewer_id": "9999"
}' https://<EXPOSED HOST>/loanproc/2/approve -H "Content-Type: application/json"
```
Get loan application :
```shell
curl -XGET https://<EXPOSED HOST>/loanapp/2 -H "Content-Type: application/json"
```



# Timer

## Action configuring timer
Create `io/kx/loanproc/action` folder in `src/main/proto` folder. <br>
Create `loan_proc_timeout_action.proto` in `src/main/proto/io/kx/loanproc/action` folder. <br>
Create: <br>
- service

<i><b>Tip</b></i>: Check content in `step-5` git branch

## Compile maven project to trigger codegen for action
```shell
mvn compile
```
Compile will generate help classes (`target/generated-*` folders) and skeleton classes<br><br>

`src/main/java/io/kx/loanproc/action/LoanProcTimeoutAction`<br>

In `src/main/java/io/kx/Main` you need to add view (`LoanProcTimeoutAction`) initialization:
```java
return KalixFactory.withComponents(LoanAppEntity::new, LoanProcEntity::new, LoanAppEventingToProcAction::new, LoanProcByStatusView::new, LoanProcEventingToAppAction::new, LoanProcTimeoutAction::new);
```
## Implement view LoanProcTimeoutAction skeleton class
Implement `src/main/java/io/kx/loanproc/action/LoanProcTimeoutAction` class<br>
<i><b>Tip</b></i>: Check content in `step-5` git branch

## System integration tests (multiple services)
In `SystemIntegrationTest` add `timeout` test..
<i><b>Tip</b></i>: Check content in `step-5` git branch

## Run integration test
```shell
mvn verify -Pit
```

<i><b>Note</b></i>: Integration tests uses [TestContainers](https://www.testcontainers.org/) to span integration environment so it could require some time to download required containers.
Also make sure docker is running.

## Run locally
Start the service:
```shell
mvn compile kalix:runAll
```

Start the local console:
```shell
akka local console
```

## Test service locally
Start processing:
```shell
curl -XPOST -d '{
  "client_monthly_income_cents": 60000,
  "loan_amount_cents": 20000,
  "loan_duration_months": 12
}' http://localhost:9000/loanproc/1 -H "Content-Type: application/json"
```

Get loan processing (after 5 seconds):
```shell
curl -XGET http://localhost:9000/loanproc/1 -H "Content-Type: application/json"
```

## Package
```shell
mvn install
```
<i><b>Note</b></i>:Copy the image tag to be used in deploy


## Package
```shell
mvn install
```
<i><b>Note</b></i>:Copy the image tag to be used in deploy
## Deploy (update) service
```shell
akka service deploy loan-application <IMAGE TAG> --push --classic
```
<i><b>Note</b></i>: Replace `IMAGE TAG` with your image tag from `mvn install`


# Action API Controller 

## Action configuring timer
Create `io/kx/loanapp/action` folder in `src/main/proto` folder. <br>
Create `loan_proc_controller_action.proto` in `src/main/proto/io/kx/loanapp/action` folder. <br>
Create: <br>
- service

<i><b>Tip</b></i>: Check content in `step-6` git branch

## Compile maven project to trigger codegen for action
```shell
mvn compile
```
Compile will generate help classes (`target/generated-*` folders) and skeleton classes<br><br>

`src/main/java/io/kx/loanapp/action/LoanAppApiControllerAction`<br>

In `src/main/java/io/kx/Main` you need to add action (`LoanAppApiControllerAction`) initialization:
```java
return KalixFactory.withComponents(LoanAppEntity::new, LoanProcEntity::new,  LoanAppApiControllerAction::new, LoanAppEventingToProcAction::new, LoanProcByStatusView::new, LoanProcEventingToAppAction::new, LoanProcTimeoutAction::new);
```
## Implement view LoanAppApiControllerAction skeleton class
Implement `src/main/java/io/kx/loanapp/action/LoanAppApiControllerAction` class<br>
<i><b>Tip</b></i>: Check content in `step-6` git branch


## Run locally
Start the service:
```shell
mvn compile kalix:runAll
```

Start the local console:
```shell
akka local console
```

## Test service locally
Submit loan application:
```shell
curl -XPOST -d '{
  "client_id": "12345",
  "client_monthly_income_cents": 60000,
  "loan_amount_cents": 20000,
  "loan_duration_months": 12
}' http://localhost:9000/api/loanapp -H "Content-Type: application/json"
```
Check the response for `loanAppId`.
Get loan application:
```shell
curl -XGET http://localhost:9000/api/loanapp/[loanAppId from ] -H "Content-Type: application/json"
```

Approve:
```shell
curl -XPUT http://localhost:9000/api/loanapp/1/approve -H "Content-Type: application/json"
```

## Package
```shell
mvn install
```
<i><b>Note</b></i>:Copy the image tag to be used in deploy


## Package
```shell
mvn install
```
<i><b>Note</b></i>:Copy the image tag to be used in deploy
## Deploy (update) service
```shell
akka service deploy loan-application <IMAGE TAG> --push --classic
```
<i><b>Note</b></i>: Replace `IMAGE TAG` with your image tag from `mvn install`
