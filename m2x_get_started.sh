#! /usr/bin/env bash

m2x_create_stream() {
    curl -s -H "X-M2X-KEY: ${M2X_API_KEY}" \
        -XPUT -d "" \
        http://api-m2x.att.com/v2/devices/${DEVICE}/streams/$1
}

m2x_write() {
    local body=$(curl -s -H "X-M2X-KEY: ${M2X_API_KEY}" \
        -H "Content-Type: application/json" \
        -XPOST -d "$1" \
        http://api-m2x.att.com/v2/devices/${DEVICE}/updates)

    [[ -z $body ]] || echo $body
}

# Create streams if they don't exist
m2x_create_stream "load_1m"
m2x_create_stream "load_5m"
m2x_create_stream "load_15m"

while :
do
    load=$(uptime | awk '{ print $(NF-2), $(NF-1), $NF }' | tr -d ,)

    load_1m=$(echo $load | cut -f 1 -d\ )
    load_5m=$(echo $load | cut -f 2 -d\ )
    load_15m=$(echo $load | cut -f 3 -d\ )

    timestamp=$(date -u +"%Y-%m-%dT%H:%M:%SZ")

    payload="{ \"values\": { \
        \"load_1m\"  : [{ \"timestamp\": \"$timestamp\", \"value\": $load_1m }], \
        \"load_5m\"  : [{ \"timestamp\": \"$timestamp\", \"value\": $load_5m }], \
        \"load_15m\" : [{ \"timestamp\": \"$timestamp\", \"value\": $load_15m }] \
    } }"

    m2x_write "$payload"

    sleep 1
done
