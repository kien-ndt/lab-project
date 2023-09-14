'use client';
import { adminBookApi } from '@/redux/features/admin/book/adminBookService';
import { useAppDispatch, useAppSelector } from '@/redux/hooks';
import { Box, Button, TextField } from '@mui/material';
import { useEffect, useRef } from 'react';

export const BookComponent = () => {
  const dispatch = useAppDispatch();
  const bookReducer = useAppSelector((state) => state.bookReducer);

  const { data, error, isLoading, refetch } =
    adminBookApi.useGetListBookQuery();
  const fileRef = useRef<HTMLInputElement | null>(null);
  const [uploadBook, resultUploadBook] = adminBookApi.useUploadBookMutation();
  useEffect(() => {
    console.log(data);
  }, [data]);

  const handleFormSubmit = (e) => {
    e.preventDefault();
    console.log(fileRef.current?.files);
    const formData = new FormData();
    fileRef.current?.files && formData.append('file', fileRef.current.files[0]);
    uploadBook(formData);
  };

  useEffect(() => {
    resultUploadBook.isSuccess && refetch();
  }, [resultUploadBook]);

  return (
    <Box>
      <form onSubmit={(e) => handleFormSubmit(e)}>
        <TextField
          type="file"
          onChange={(e) => {
            console.log(e);
          }}
          inputRef={fileRef}
        />
        <Button type="submit">Submit</Button>
      </form>
    </Box>
  );
};
