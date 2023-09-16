import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { GenericResponse } from '../../model';
import {
  CreateAccountRequest,
  GetListAccountsResponse,
} from './adminAccountServiceModel';

export const adminAccountApi = createApi({
  reducerPath: 'adminAccountApi',
  baseQuery: fetchBaseQuery({
    baseUrl: `${process.env.NEXT_PUBLIC_API_HOST}/admin/account`,
    credentials: 'include',
  }),
  endpoints: (builder) => ({
    getListAccount: builder.query<GetListAccountsResponse, void>({
      query: () => ({
        url: `/`,
        method: 'GET',
      }),
    }),
    createAccount: builder.mutation<GenericResponse, CreateAccountRequest>({
      query: (data: CreateAccountRequest) => ({
        url: '/',
        method: 'POST',
        body: data,
      }),
    }),
  }),
});
