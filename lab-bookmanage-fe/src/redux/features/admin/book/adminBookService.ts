import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { GenericResponse } from '../../model';
import {
  CreateBookRequest,
  GetBookResponse,
  GetListBookResponse,
  UpdateBookRequest,
} from './adminBookServiceModel';

export const adminBookApi = createApi({
  reducerPath: 'adminBookApi',
  baseQuery: fetchBaseQuery({
    baseUrl: `${process.env.NEXT_PUBLIC_API_HOST}/admin/book`,
    credentials: 'include',
  }),
  endpoints: (builder) => ({
    getListBook: builder.mutation<GetListBookResponse, undefined | string>({
      query: (search) => ({
        url: `/`,
        method: 'GET',
        params: search ? { search } : undefined,
      }),
    }),
    getBook: builder.mutation<GetBookResponse, number>({
      query: (id: number) => ({
        url: `/${id}`,
        method: 'GET',
      }),
    }),
    createBook: builder.mutation<GenericResponse, CreateBookRequest>({
      query: (data: CreateBookRequest) => ({
        url: '/',
        method: 'POST',
        body: data,
      }),
    }),
    updateBook: builder.mutation<GenericResponse, UpdateBookRequest>({
      query: (data: CreateBookRequest) => ({
        url: '/',
        method: 'PUT',
        body: data,
      }),
    }),
    deleteBook: builder.mutation<GenericResponse, number>({
      query: (id: number) => ({
        url: `/${id}`,
        method: 'DELETE',
      }),
    }),
    uploadBook: builder.mutation<GenericResponse, FormData>({
      query: (data: FormData) => ({
        url: '/upload',
        method: 'POST',
        body: data,
      }),
    }),
  }),
});
