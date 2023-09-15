import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { GenericErrorResponse } from '../../model';
import { LoginRequest } from './adminAuthServiceModel';

export const adminAuthApi = createApi({
  reducerPath: 'adminAuth',
  baseQuery: fetchBaseQuery({
    baseUrl: `${process.env.NEXT_PUBLIC_API_HOST}/admin`,
    credentials: 'include',
  }),
  endpoints: (builder) => ({
    login: builder.mutation<GenericErrorResponse, LoginRequest>({
      query: (data: LoginRequest) => ({
        url: '/login',
        method: 'POST',
        body: data,
      }),
    }),
  }),
});
