import { createSlice } from '@reduxjs/toolkit';
import type { PayloadAction } from '@reduxjs/toolkit';
import type { RootState } from '../../store';
import { login } from './authService';

interface AuthState {
  data?: {};
  isLoading: boolean;
  error?: {
    message?: string;
  };
}

const initialState: AuthState = {
  isLoading: false,
};

export const authSlice = createSlice({
  name: 'authReducer',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(login.pending, (state) => {
      state.isLoading = true;
      state.error = undefined;
    });

    builder.addCase(login.fulfilled, (state, { payload }) => {
      state.data = {};
      state.isLoading = false;
      state.error = undefined;
    });

    builder.addCase(login.rejected, (state, { payload }) => {
      state.isLoading = false;
      state.error = { message: payload?.message };
    });
  },
});

export default authSlice;
