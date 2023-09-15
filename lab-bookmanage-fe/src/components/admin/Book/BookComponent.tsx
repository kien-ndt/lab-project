'use client';

import { ManagementLayout } from '@/components/common/layout/ManagementLayout';
import { UploadFile } from '@/components/common/upload/UploadFile';
import { adminBookApi } from '@/redux/features/admin/book/adminBookService';
import { useAppDispatch, useAppSelector } from '@/redux/hooks';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import {
  Box,
  Button,
  IconButton,
  InputBase,
  Paper,
  Skeleton,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from '@mui/material';
import { grey } from '@mui/material/colors';
import { useEffect, useRef } from 'react';

export const BookComponent = () => {
  const dispatch = useAppDispatch();
  const bookReducer = useAppSelector((state) => state.bookReducer);

  const { data, error, isLoading, refetch } =
    adminBookApi.useGetListBookQuery();
  const fileRef = useRef<HTMLInputElement | null>(null);
  const resultGetListBook = adminBookApi.useGetListBookQuery();
  const [uploadBook, resultUploadBook] = adminBookApi.useUploadBookMutation();

  const handleUploadFile = (files: FileList) => {
    const formData = new FormData();
    formData.append('file', files[0]);
    uploadBook(formData);
  };

  useEffect(() => {
    resultUploadBook.isSuccess && refetch();
  }, [resultUploadBook.isSuccess]);

  return (
    <ManagementLayout>
      <Box
        sx={{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
        }}
      >
        <Paper
          component="form"
          sx={{
            p: '2px 4px',
            display: 'flex',
            alignItems: 'center',
            width: 400,
          }}
        >
          <InputBase sx={{ ml: 1, flex: 1 }} placeholder="Tìm kiếm sách" />
          <IconButton type="button" sx={{ p: '10px' }}>
            <SearchIcon />
          </IconButton>
        </Paper>
        <Box>
          <UploadFile
            loading={resultUploadBook.isLoading}
            labelNoFile={'Tải lên'}
            label={'Gửi'}
            onSubmitFiles={handleUploadFile}
            sx={{ width: '120px' }}
            variant="outlined"
            size="small"
          />
          <Button
            endIcon={<AddIcon />}
            variant="contained"
            size="small"
            sx={{ ml: 2 }}
          >
            Thêm mới
          </Button>
        </Box>
      </Box>
      {resultGetListBook.isFetching ? (
        <Box sx={{ mt: 3 }}>
          <Skeleton animation="wave" />
          <Skeleton animation="wave" />
          <Skeleton animation="wave" />
        </Box>
      ) : (
        <TableContainer sx={{ mt: 3 }}>
          <Table>
            <TableHead sx={{ bgcolor: grey[100] }}>
              <TableRow>
                <TableCell sx={{ fontWeight: 700 }}>STT</TableCell>
                <TableCell sx={{ fontWeight: 700 }}>Tên sách</TableCell>
                <TableCell sx={{ fontWeight: 700 }}>Tác giả</TableCell>
                <TableCell sx={{ fontWeight: 700 }}>Thể loại</TableCell>
                <TableCell sx={{ fontWeight: 700 }}></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {resultGetListBook.data &&
                resultGetListBook.data.bookList &&
                resultGetListBook.data.bookList.length > 0 &&
                resultGetListBook.data.bookList.map((book, index) => (
                  <TableRow key={index}>
                    <TableCell>{index + 1}</TableCell>
                    <TableCell>{book.title}</TableCell>
                    <TableCell>{book.author}</TableCell>
                    <TableCell>{book.category}</TableCell>
                    <TableCell>{'action'}</TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </ManagementLayout>
  );
};
