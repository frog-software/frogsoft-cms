import { createModel }   from '@rematch/core';
import { Configuration } from 'types/app';

interface State {
  configuration: Configuration,
}

export const app = createModel({
  state: {
    configuration: {
      isBackgroundDisabled: false,
    },
  } as State,
  reducers: {
    setConfiguration: (state, payload: Configuration): State => ({
      ...state,
      configuration: payload,
    }),
  },
});
