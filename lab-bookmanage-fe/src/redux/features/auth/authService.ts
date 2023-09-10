import { createAsyncThunk } from '@reduxjs/toolkit';
import { LoginRequest } from './authModel';
import { GenericErrorResponse } from '../model';

export const login = createAsyncThunk<
  void,
  LoginRequest,
  { rejectValue: { message: string } }
>('todoList/getTodos', async (loginRequest, thunkApi) => {
  const response = await fetch(`${process.env.NEXT_PUBLIC_API_HOST}/login`, {
    method: 'POST',
    body: JSON.stringify(loginRequest),
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  });
  const jsonData = await response.json();
  if (response.status >= 400) {
    const errorResponse: GenericErrorResponse =
      jsonData as GenericErrorResponse;
    return thunkApi.rejectWithValue({
      message: errorResponse?.message,
    });
  }
  return jsonData;
});
