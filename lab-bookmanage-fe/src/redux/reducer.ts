import { adminAuthApi } from './features/admin/auth/adminAuthService';
import { adminBookApi } from './features/admin/book/adminBookService';
import { authSlice } from './features/auth/authSlice';
import bookSlice from './features/book/bookSlice';
import { commonSlice } from './features/common/commonSlice';

const reducer = {
  [authSlice.name]: authSlice.reducer,
  [commonSlice.name]: commonSlice.reducer,
  [bookSlice.name]: bookSlice.reducer,
  [adminBookApi.reducerPath]: adminBookApi.reducer,
  [adminAuthApi.reducerPath]: adminAuthApi.reducer,
};

export default reducer;
