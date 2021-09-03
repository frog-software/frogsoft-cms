import { createModel } from '@rematch/core';
import { Configure } from 'types/app';

interface State {
  configure: Configure,
}

export const app = createModel({
  state: {
    configure: {
      test: 0,
    },
  } as State,
  reducers: {
    setConfigure: (state, payload: Configure): State => ({
      ...state,
      configure: payload,
    }),
  },
});
