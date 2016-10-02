#!/usr/local/bin/dumb-init /bin/bash
set -e

consul-template \
    -consul consul:8500 \
    -template "/root/hosts.consul.ctmpl:/etc/hosts.consul" \
    -retry 30s \
    -once

echo $PATH

# Disabling nginx daemon mode
export KONG_NGINX_DAEMON="off"

[ -z "$KONG_NGINX_DAEMON" ] && export KONG_NGINX_DAEMON="off"

exec "$@"
