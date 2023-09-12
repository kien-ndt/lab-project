import { createSlice } from '@reduxjs/toolkit';
import { AdminBookGetResponse } from './bookModel';
import { adminBookGet } from './bookService';

interface BookState {
  data: AdminBookGetResponse;
  isLoading: boolean;
  error?: {
    message?: string;
  };
}

const initialState: BookState = {
  isLoading: false,
  data: {
    bookList: [],
  },
};

export const bookSlice = createSlice({
  name: 'bookReducer',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(adminBookGet.pending, (state) => {
      state.isLoading = true;
      state.error = undefined;
    });

    builder.addCase(adminBookGet.fulfilled, (state, { payload }) => {
      state.data = payload;
      state.isLoading = false;
      state.error = undefined;
    });

    builder.addCase(adminBookGet.rejected, (state, { payload }) => {
      state.isLoading = false;
      state.error = { message: payload?.message };
    });
  },
});

export default bookSlice;
