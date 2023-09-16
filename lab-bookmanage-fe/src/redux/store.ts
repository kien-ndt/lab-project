import { configureStore } from '@reduxjs/toolkit';
import { setupListeners } from '@reduxjs/toolkit/query';
import { adminAccountApi } from './features/admin/account/adminAccountService';
import { adminAuthApi } from './features/admin/auth/adminAuthService';
import { adminBookApi } from './features/admin/book/adminBookService';
import reducer from './reducer';
export const store = configureStore({
  reducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware()
      .concat(adminBookApi.middleware)
      .concat(adminAuthApi.middleware)
      .concat(adminAccountApi.middleware),
});
setupListeners(store.dispatch);
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
