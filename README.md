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
```

## Gatling

Gatling test without circuit breaker:

```
$ QPS=400 REQUEST=/heavy sbt "benchmark/gatling:testOnly AppRequestSimulation"
```

Gatling test with circuit breaker:

```
$ QPS=400 REQUEST=/envoy sbt "benchmark/gatling:testOnly AppRequestSimulation"
```

Open result:
```
$ open ./benchmark/target/gatling/*/index.html
```

## Layout

![Layout](https://github.com/ichiro-arai/envoy-circuit-breaking-example/blob/images/layout.svg "layout")
