import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { GetListBookResponse } from './adminBookServiceModel';

export const adminBookApi = createApi({
  reducerPath: 'adminBookApi',
  baseQuery: fetchBaseQuery({
    baseUrl: `${process.env.NEXT_PUBLIC_API_HOST}/admin/book`,
    credentials: 'include',
  }),
  endpoints: (builder) => ({
    getListBook: builder.query<GetListBookResponse, void>({
      query: () => `/`,
    }),
    uploadBook: builder.mutation<String, FormData>({
      query: (data: FormData) => ({
        url: '/upload',
        method: 'POST',
        body: data,
      }),
    }),
  }),
});
