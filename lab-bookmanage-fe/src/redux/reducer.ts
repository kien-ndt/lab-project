import { authSlice } from './features/auth/authSlice';
import bookSlice from './features/book/bookSlice';
import { commonSlice } from './features/common/commonSlice';

const reducer = {
  [authSlice.name]: authSlice.reducer,
  [commonSlice.name]: commonSlice.reducer,
  [bookSlice.name]: bookSlice.reducer,
};

export default reducer;
