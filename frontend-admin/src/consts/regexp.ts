/**
 * Common regular expressions.
 */

export default {
  phone: /^1[3-9]\d{9}$/,
  password: /^[0-9A-Za-z_\-!@*#^$&%.?]{6,18}$/,
  code: /^\d{4}$/,
  email: /^([a-zA-Z0-9_-]|\.)+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
  price: /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/,
  negativePrice: /(^-?[1-9]\d*(\.\d{1,2})?$)|(^-?0(\.\d{1,2})?$)/,
};
