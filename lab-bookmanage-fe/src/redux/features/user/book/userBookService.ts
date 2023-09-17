import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { GenericResponse } from '../../model';
import { GetListBookResponse } from './userBookServiceModel';

export const userBookApi = createApi({
  reducerPath: 'userBookApi',
  baseQuery: fetchBaseQuery({
    baseUrl: `${process.env.NEXT_PUBLIC_API_HOST}/book`,
    credentials: 'include',
  }),
  endpoints: (builder) => ({
    getListBook: builder.query<GetListBookResponse, undefined | string>({
      query: (search) => ({
        url: `/`,
        method: 'GET',
        params: search ? { search } : undefined,
      }),
    }),
    updateFavouriteBook: builder.mutation<GenericResponse, number>({
      query: (bookId: number) => ({
        url: `/${bookId}/update_favourite`,
        method: 'POST',
      }),
    }),
  }),
});
