import type { PayloadAction } from '@reduxjs/toolkit';
import { createSlice } from '@reduxjs/toolkit';

interface CommonState {
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

const initialState: CommonState = { modal: {} };

export const commonSlice = createSlice({
  name: 'commonReducer',
  initialState,
  reducers: {
    redirectAdminLogin(state) {
      state.error = { navigator: { redirectUrl: '/admin' } };
    },
    redirectUserLogin(state) {
      state.error = {};
    },
    redirect404(state) {
      state.error = { navigator: { show404: true } };
    },
    resetRedirect(state) {
      state.error = { navigator: undefined };
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
  },
});

export const commonActions = commonSlice.actions;
export default commonSlice;
