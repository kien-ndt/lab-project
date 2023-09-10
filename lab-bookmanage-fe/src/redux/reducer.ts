import { authSlice } from './features/auth/authSlice';

const reducer = {
  [authSlice.name]: authSlice.reducer,
};

export default reducer;
