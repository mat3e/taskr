# JHipster generated Docker-Compose configuration

## Usage

Launch all your infrastructure by running: `docker-compose up -d`.

## Configured docker services

### Service registry and configuration server:
- [Consul](http://localhost:8500)

### Applications and dependencies:
- taskr (gateway application)
- taskr's mongodb database
- task (microservice application)
- task's mongodb database
- uaa (uaa application)
- uaa's mongodb database
- worker (microservice application)
- worker's mongodb database

### Additional Services:

- [JHipster Console](http://localhost:5601)
- [Zipkin](http://localhost:9411)
