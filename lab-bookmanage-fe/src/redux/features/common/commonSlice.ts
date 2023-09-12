import type { PayloadAction } from '@reduxjs/toolkit';
import { createSlice } from '@reduxjs/toolkit';

interface CommonState {
  error?: {
    navigator?: {
      redirectUrl?: string;
      show404?: boolean;
    };
  };
}

const initialState: CommonState = {};

export const commonSlice = createSlice({
  name: 'commonReducer',
  initialState,
  reducers: {
    redirectAdminLogin(state) {
      state.error = { navigator: { redirectUrl: '/admin' } };
    },
    redirectUserLogin(state, action: PayloadAction<number>) {
      state.error = {};
    },
    redirect404(state) {
      state.error = { navigator: { show404: true } };
    },
    resetRedirect(state) {
      state.error = { navigator: undefined };
    },
  },
});

export default commonSlice;
