#!/usr/bin/env sh
echo "replacing URLs with $VITE_BACKEND_URL"
find '/usr/share/nginx/html' -name '*.js' -exec sed -i -e 's,VITE_BACKEND_URL_RUNTIME_REPLACEMENT,'"$VITE_BACKEND_URL"',g' {} \;
echo "result: $?, now starting nginx"
nginx -g "daemon off;"
