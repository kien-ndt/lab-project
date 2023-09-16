'use client';

import { ManagementLayout } from '@/components/common/layout/ManagementLayout';
import { UploadFile } from '@/components/common/upload/UploadFile';
import { adminBookApi } from '@/redux/features/admin/book/adminBookService';
import { commonActions } from '@/redux/features/common/commonSlice';
import { useAppDispatch } from '@/redux/hooks';
import { useErrorHandler } from '@/utils/useErrorHandler';
import AddIcon from '@mui/icons-material/Add';
import DeleteIcon from '@mui/icons-material/Delete';
import EditNoteIcon from '@mui/icons-material/EditNote';
import SearchIcon from '@mui/icons-material/Search';
import { LoadingButton } from '@mui/lab';
import {
  Box,
  Button,
  IconButton,
  InputBase,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from '@mui/material';
import { grey } from '@mui/material/colors';
import React, { useEffect, useState } from 'react';
import { ModalCreateBook } from './ModalCreateBook/ModalCreateBook';
import { ModalUpdateBook } from './ModalUpdateBook/ModalUpdateBook';

const MODAL_CREATE_BOOK = 'modal-create-book';
const MODAL_UPDATE_BOOK = 'modal-update-book';

export const BookComponent = () => {
  const dispatch = useAppDispatch();

  const [search, setSearch] = useState<string>();

  const [getListBook, resultGetListBook] =
    adminBookApi.useGetListBookMutation();
  const [uploadBook, resultUploadBook] = adminBookApi.useUploadBookMutation();
  const [deleteBook, resultDeleteBook] = adminBookApi.useDeleteBookMutation();
  useErrorHandler(resultGetListBook.error);
  useErrorHandler(resultUploadBook.error);
  useErrorHandler(resultDeleteBook.error);

  const handleUploadFile = (files: FileList) => {
    const formData = new FormData();
    formData.append('file', files[0]);
    uploadBook(formData);
  };

  useEffect(() => {
    getListBook(undefined);
  }, []);

  useEffect(() => {
    resultUploadBook.isSuccess && getListBook(search);
  }, [resultUploadBook.isSuccess]);

  const openModalCreateBook = () => {
    dispatch(commonActions.openModal(MODAL_CREATE_BOOK));
  };
  const handleOnCreateSuccess = () => {
    dispatch(commonActions.closeModal(MODAL_CREATE_BOOK));
    getListBook(search);
  };
  const openModalUpdateBook = (id: number) => {
    dispatch(
      commonActions.openModal({
        modalId: MODAL_UPDATE_BOOK,
        data: {
          bookId: id,
        },
      }),
    );
  };
  const handleOnUpdateSuccess = () => {
    dispatch(commonActions.closeModal(MODAL_UPDATE_BOOK));
    getListBook(search);
  };
  const handleOnDelete = (id: number) => {
    deleteBook(id);
    resultDeleteBook.data &&
      dispatch(
        commonActions.showSuccessNotification(resultDeleteBook.data.message),
      );
  };
  useEffect(() => {
    resultDeleteBook.isSuccess && getListBook(search);
  }, [resultDeleteBook.isSuccess]);

  const onSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.value === '') {
      setSearch(undefined);
    } else {
      setSearch(e.target.value);
    }
  };
  const onSearchSubmit = () => {
    getListBook(search);
  };

  return (
    <ManagementLayout>
      <ModalCreateBook
        id={MODAL_CREATE_BOOK}
        handleOnSuccess={handleOnCreateSuccess}
      />
      <ModalUpdateBook
        id={MODAL_UPDATE_BOOK}
        handleOnSuccess={handleOnUpdateSuccess}
      />
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
          onSubmit={(e) => {
            e.preventDefault();
            onSearchSubmit();
          }}
        >
          <InputBase
            sx={{ ml: 1, flex: 1 }}
            placeholder="Tìm kiếm sách"
            onChange={onSearchChange}
          />
          <IconButton type="submit" sx={{ p: '10px' }}>
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
            onClick={openModalCreateBook}
          >
            Thêm mới
          </Button>
        </Box>
      </Box>

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
                <TableRow key={index} hover>
                  <TableCell>{index + 1}</TableCell>
                  <TableCell>{book.title}</TableCell>
                  <TableCell>{book.author}</TableCell>
                  <TableCell>{book.category}</TableCell>
                  <TableCell>
                    <Box>
                      <IconButton onClick={() => openModalUpdateBook(book.id)}>
                        <EditNoteIcon color="primary" />
                      </IconButton>
                      <LoadingButton
                        onClick={() => handleOnDelete(book.id)}
                        loading={resultDeleteBook.isLoading}
                      >
                        <DeleteIcon color="warning" />
                      </LoadingButton>
                    </Box>
                  </TableCell>
                </TableRow>
              ))}
          </TableBody>
        </Table>
      </TableContainer>
    </ManagementLayout>
  );
};
