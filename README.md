# Pact Contract Application

This repository contains the code for a Pact contract application. Pact is a consumer-driven contract testing tool, which allows you to ensure that the interactions between your services remain consistent and correct.

## Prerequisites

Before you begin, ensure you have met the following requirements:
- You have installed [Java JDK 21](https://www.oracle.com/java/technologies/javase-downloads.html).
- You have installed [Maven](https://maven.apache.org/install.html).
- You have installed [Docker](https://docs.docker.com/get-docker/).

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/swarupjana38633/hdfc-life-contract-pact-demo.git
cd hdfc-life-contract-pact-demo
```

### 2. Run the Pact Broker
```bash
docker-compose up
```

### 3. Check Pact Broker is working
- Open browser and access the link [PactBroker](http://localhost:8000/).
  - Credential to access the pact broker
  - username/password :- pact_workshop/pact_workshop

### 4. Publish the contract pact in Broker
```bash
cd contract-consumer
mvn clean install
mvn pact:publish
```
- This will publish the contract in pact broker which is running
- On home page of Pact broker FetchConsumerApplication as Consumer and ProductService as Provider will be shown.
- You can check the contract by clicking the document icon and pact matrix icon.

### 5. Run the Provider for verify with contract and publish the result in Pact Broker
```bash
cd ../
cd contract-provider
mvn clean install
```
- Go to browser [PactBroker](http://localhost:8000/).
- open the Pact Matrix Icon to check the provider result.

### 6. Can I Deploy?
With just a simple use of the `pact-broker` [can-i-deploy tool](https://docs.pact.io/pact_broker/advanced_topics/provider_verification_results) - the Broker will determine if a consumer or provider is safe to release to the specified environment.

You can run the `pact-broker can-i-deploy` checks as follows:

```console
❯ docker run --rm --network host \
  	-e PACT_BROKER_BASE_URL=http://localhost:8000 \
  	-e PACT_BROKER_USERNAME=pact_workshop \
  	-e PACT_BROKER_PASSWORD=pact_workshop \
  	pactfoundation/pact-cli:latest \
  	broker can-i-deploy \
  	--pacticipant FetchConsumerApplication \
  	--latest


Computer says yes \o/

CONSUMER            | C.VERSION | PROVIDER       | P.VERSION | SUCCESS?
--------------------|-----------|----------------|-----------|---------
FrontendApplication | 2955ca5   | ProductService | 2955ca5   | true

All required verification results are published and successful


----------------------------

❯ docker run --rm --network host \
  	-e PACT_BROKER_BASE_URL=http://localhost:8000 \
  	-e PACT_BROKER_USERNAME=pact_workshop \
  	-e PACT_BROKER_PASSWORD=pact_workshop \
  	pactfoundation/pact-cli:latest \
  	broker can-i-deploy \
  	--pacticipant ProductService \
  	--latest

Computer says yes \o/

CONSUMER            | C.VERSION | PROVIDER       | P.VERSION | SUCCESS?
--------------------|-----------|----------------|-----------|---------
FrontendApplication | 2955ca5   | ProductService | 2955ca5   | true

All required verification results are published and successfull
```


