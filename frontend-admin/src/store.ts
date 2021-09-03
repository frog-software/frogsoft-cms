import { init, RematchDispatch, RematchRootState } from '@rematch/core';
import { QueryClient } from 'react-query';
import * as models from './models';

const store = init<typeof models>({ models });

export type Dispatch = RematchDispatch<typeof models>;
export type RootState = RematchRootState<typeof models>;

declare global {
  type Dispatch = RematchDispatch<typeof models>;
  type RootState = RematchRootState<typeof models>;
}

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
    },
  },
});

export default store;
