import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { GenericErrorResponse, GenericResponse } from '../../model';
import { LoginRequest } from './userAuthServiceModel';

export const userAuthApi = createApi({
  reducerPath: 'userAuth',
  baseQuery: fetchBaseQuery({
    baseUrl: `${process.env.NEXT_PUBLIC_API_HOST}/user`,
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
    logout: builder.mutation<GenericResponse, void>({
      query: () => '/logout',
    }),
  }),
});
