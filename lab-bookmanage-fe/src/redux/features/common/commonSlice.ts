import type { PayloadAction } from '@reduxjs/toolkit';
import { createSlice } from '@reduxjs/toolkit';

interface CommonState {
  auth: {
    isAuth: boolean;
  };
  error?: {
    navigator?: {
      redirectUrl?: string;
      show404?: boolean;
    };
  };
  notification?: {
    success?: string;
    error?: string;
  };
  modal: {
    [id: string]: {
      open?: boolean;
      data?: any;
    };
  };
}

const initialState: CommonState = { modal: {}, auth: { isAuth: false } };

export const commonSlice = createSlice({
  name: 'commonReducer',
  initialState,
  reducers: {
    redirectAdminLogin(state) {
      state.error = { navigator: { redirectUrl: '/admin' } };
      state.modal = {};
    },
    redirectUserLogin(state) {
      state.error = {};
      state.modal = {};
    },
    redirect404(state) {
      state.error = { navigator: { show404: true } };
      state.modal = {};
    },
    resetRedirect(state) {
      state.error = { navigator: undefined };
      state.modal = {};
    },
    openModal(
      state,
      data: PayloadAction<string | { modalId: string; data: any }>,
    ) {
      if (typeof data.payload === 'string') {
        state.modal[data.payload] = { open: true };
      } else {
        state.modal[data.payload.modalId] = {
          open: true,
          data: data.payload.data,
        };
      }
    },
    closeModal(state, data: PayloadAction<string>) {
      state.modal[data.payload] = { open: undefined };
    },
    showSuccessNotification(state, data: PayloadAction<string>) {
      state.notification = {
        success: data.payload,
      };
    },
    showErrorNotification(state, data: PayloadAction<string>) {
      state.notification = {
        error: data.payload,
      };
    },
    hiddenNotification(state) {
      state.notification = undefined;
    },
    setAuth(state, isAuth: PayloadAction<boolean>) {
      state.auth = { isAuth: isAuth.payload };
    },
  },
});

export const commonActions = commonSlice.actions;
export default commonSlice;
