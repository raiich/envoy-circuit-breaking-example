# envoy-circuit-breaking-example

## Start example services

```
$ docker-compose up
```

Health check by curl:

```
$ curl http://localhost:8080/heavy
OK!
$ curl http://localhost:8080/envoy
OK!
$ curl http://localhost:8080/light
ok
```

## Gatling

### Single Request Scenario

Gatling test without circuit breaker:

```
$ QPS=400 REQUEST=/heavy sbt "benchmark/gatling:testOnly SingleRequestSimulation"
```

Gatling test with circuit breaker:

```
$ QPS=400 REQUEST=/envoy sbt "benchmark/gatling:testOnly SingleRequestSimulation"
```

Open result:
```
$ open ./benchmark/target/gatling/*/index.html
```

### Compound Request Scenario

Gatling test without circuit breaker:

```
$ QPS=400 REQUEST_1=/heavy REQUEST_2=/light sbt "benchmark/gatling:testOnly CompoundRequestSimulation"
```

Gatling test with circuit breaker:

```
$ QPS=400 REQUEST_1=/envoy REQUEST_2=/light sbt "benchmark/gatling:testOnly CompoundRequestSimulation"
```

Open result:

```
$ open ./benchmark/target/gatling/*/index.html
```


## Layout

![Layout](https://github.com/ichiro-arai/envoy-circuit-breaking-example/blob/images/layout-compound.svg "layout")
