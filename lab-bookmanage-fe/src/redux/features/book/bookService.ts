import { fetcher } from '@/utils/fetcher';
import { createAsyncThunk } from '@reduxjs/toolkit';
import commonSlice from '../common/commonSlice';
import { AdminBookGetResponse } from './bookModel';

export const adminBookGet = createAsyncThunk<
  AdminBookGetResponse,
  void,
  { rejectValue: { message: string; status: number } }
>('admin/book', async (args, thunkApi) => {
  try {
    const res = await fetcher('/admin/book', 'GET');
    return res;
  } catch (err: any) {
    if (err.status === 403) {
      thunkApi.dispatch(commonSlice.actions.redirectAdminLogin());
      throw new Error();
    }
    return thunkApi.rejectWithValue({
      message: err.errBody.message,
      status: err.status,
    });
  }
});
