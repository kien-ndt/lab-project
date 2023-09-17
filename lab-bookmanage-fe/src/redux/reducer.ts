import { adminAccountApi } from './features/admin/account/adminAccountService';
import { adminAuthApi } from './features/admin/auth/adminAuthService';
import { adminBookApi } from './features/admin/book/adminBookService';
import { authSlice } from './features/auth/authSlice';
import bookSlice from './features/book/bookSlice';
import { commonSlice } from './features/common/commonSlice';
import { userAuthApi } from './features/user/auth/userAuthService';
import { userBookApi } from './features/user/book/userBookService';

const reducer = {
  [authSlice.name]: authSlice.reducer,
  [commonSlice.name]: commonSlice.reducer,
  [bookSlice.name]: bookSlice.reducer,
  [adminBookApi.reducerPath]: adminBookApi.reducer,
  [adminAuthApi.reducerPath]: adminAuthApi.reducer,
  [adminAccountApi.reducerPath]: adminAccountApi.reducer,
  [userAuthApi.reducerPath]: userAuthApi.reducer,
  [userBookApi.reducerPath]: userBookApi.reducer,
};

export default reducer;
