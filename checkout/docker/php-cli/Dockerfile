FROM php:7.4-cli-alpine

RUN set -ex \
    && apk update \
    && apk upgrade \
    && apk add git \
    && apk --no-cache add $PHPIZE_DEPS zlib-dev \
    && docker-php-ext-install zip \
    && docker-php-source extract

COPY --from=composer:latest /usr/bin/composer /usr/bin/composer
