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


