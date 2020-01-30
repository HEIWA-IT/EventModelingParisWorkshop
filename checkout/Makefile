export UID = $(shell id -u)
export GID = $(shell id -g)

all: tests

php:
	docker-compose run --rm php-cli sh

tests:
	docker-compose run --rm php-cli ./vendor/bin/phpunit

composer:
	docker-compose run --rm php-cli composer $(wordlist 2,$(words $(MAKECMDGOALS)),$(MAKECMDGOALS))

composer-dev:
	docker-compose run --rm php-cli composer --dev $(wordlist 2,$(words $(MAKECMDGOALS)),$(MAKECMDGOALS))

.PHONY: php tests composer composer-dev
