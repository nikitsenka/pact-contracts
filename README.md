# Pact-example
Java example of consumer driven contracts using [pacts](https://docs.pact.io/). 

# Step1. Define contract on consumer side.
As a consumer i would like to have a registration service to get registration by name.
Writing `ServiceClientTest` to define and verify the Pact contract. 
The result pact file will be in `build/pacts` folder

```
 ./gradlew :consumer:build
```

# Step2. Share contract.
Publish contract to broker server to make it viewable for all.
Run pact broker
```
 docker-compose up -d pact-broker
```
Publish contract
```
./gradlew :consumer:pactPublish
```
Open pact broker UI (http://localhost:9292/)

# Step3. Implement contract on provider side.
Implement API and cover with tests `OrderControllerTest` 
 
 ```
 ./gradlew :provider:build
 ```

# Step4. Testing.
Continuous testing of consumer-provider agreements.
If new contract version will be published by consumer, the provider tests will detect it. 

# Step5. Run stubs using pacts
If you need mock server to simulate provider behaviour you can use `stubserver`
Using mockserver it is possible to develop consumer and provider services independently in parallel. 

```
 docker-compose up -d stubserver
```
Check it works
```
 curl http://localhost:8085/orders
```# pact-contracts
