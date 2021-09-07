#!/usr/bin/env sh

find '/usr/share/nginx/html' -name '*.js' -exec sed -i -e 's,VITE_BACKEND_URL_RUNTIME_REPLACEMENT,'"$VITE_BACKEND_URL"',g' {} \;
nginx -g "daemon off;"
