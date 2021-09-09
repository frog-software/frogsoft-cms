import { createModel }   from '@rematch/core';
import { Configuration } from 'types/app';
import { User }          from 'types/user';

interface State {
  configuration: Configuration,
  userInfo: User,
}

export const app = createModel({
  state: {
    configuration: {
      isBackgroundDisabled: false,
    },
    userInfo: {},
  } as State,
  reducers: {
    setConfiguration: (state, payload: Configuration): State => ({
      ...state,
      configuration: payload,
    }),
    setUserInfo: (state, payload: User): State => ({
      ...state,
      userInfo: payload,
    }),
  },
});
