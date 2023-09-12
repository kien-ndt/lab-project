import { fetcher } from '@/utils/fetcher';
import { createAsyncThunk } from '@reduxjs/toolkit';
import { LoginRequest } from './authModel';

export const login = createAsyncThunk<
  void,
  LoginRequest,
  { rejectValue: { message: string; status: number } }
>('admin/login', async (loginRequest, thunkApi) => {
  try {
    const res = await fetcher(
      '/admin/login',
      'POST',
      JSON.stringify(loginRequest),
    );
    return res;
  } catch (err: any) {
    return thunkApi.rejectWithValue({
      message: err.errBody.message,
      status: err.status,
    });
  }
});
