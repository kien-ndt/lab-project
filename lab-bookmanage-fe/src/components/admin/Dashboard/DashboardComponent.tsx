'use client';
import { adminBookGet } from '@/redux/features/book/bookService';
import { useAppDispatch, useAppSelector } from '@/redux/hooks';
import { Box } from '@mui/material';
import { useEffect } from 'react';

export const DashboardComponent = () => {
  const dispatch = useAppDispatch();
  const bookReducer = useAppSelector((state) => state.bookReducer);
  useEffect(() => {
    dispatch(adminBookGet());
  }, []);
  useEffect(() => {
    console.log(bookReducer.data);
  }, [bookReducer]);

  return <Box>asjdaslkdjlkasd</Box>;
};
