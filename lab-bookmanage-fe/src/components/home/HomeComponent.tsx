'use client';

import { commonActions } from '@/redux/features/common/commonSlice';
import { userAuthApi } from '@/redux/features/user/auth/userAuthService';
import { userBookApi } from '@/redux/features/user/book/userBookService';
import { useAppDispatch, useAppSelector } from '@/redux/hooks';
import { useUserErrorHandler } from '@/utils/useUserErrorHandler';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import BookmarkBorderIcon from '@mui/icons-material/BookmarkBorder';
import LoginIcon from '@mui/icons-material/Login';
import LogoutIcon from '@mui/icons-material/Logout';
import SearchIcon from '@mui/icons-material/Search';
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
import { useEffect, useState } from 'react';
import { MODAL_LOGIN, ModalLogin } from './ModalLogin/ModalLogin';

export const HomeComponent = () => {
  const [searchInput, setSearchInput] = useState<string>();
  const [search, setSearch] = useState<string>();
  const resultGetListBook = userBookApi.useGetListBookQuery(search);
  useUserErrorHandler(resultGetListBook.error, resultGetListBook.isFetching);
  const [authLogout, resultAuthLogout] = userAuthApi.useLogoutMutation();
  const [updateFavouriteBook, resultUpdateFavouriteBook] =
    userBookApi.useUpdateFavouriteBookMutation();
  useUserErrorHandler(
    resultUpdateFavouriteBook.error,
    resultUpdateFavouriteBook.isSuccess,
  );
  const dispatch = useAppDispatch();
  const commonReducer = useAppSelector((state) => state.commonReducer);
  const onSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.value === '') {
      setSearchInput(undefined);
    } else {
      setSearchInput(e.target.value);
    }
  };
  const onSearchSubmit = () => {
    setSearch(searchInput);
  };
  const openModalLogin = () => {
    dispatch(commonActions.openModal(MODAL_LOGIN));
  };
  const onLoginSuccess = () => {
    dispatch(commonActions.closeModal(MODAL_LOGIN));
    resultGetListBook.refetch();
  };
  const handleLogout = () => {
    authLogout();
  };

  useEffect(() => {
    if (resultGetListBook.isSuccess) {
      if (!resultGetListBook.data.isLogin) {
        dispatch(commonActions.setAuth(false));
        localStorage.removeItem('isAuth');
      }
    }
  }, [resultGetListBook.isSuccess]);

  useEffect(() => {
    if (resultAuthLogout.isSuccess) {
      dispatch(commonActions.setAuth(false));
      localStorage.removeItem('isAuth');
      resultGetListBook.refetch();
    }
  }, [resultAuthLogout.isSuccess]);

  const onFavouriteClick = (id: number) => {
    updateFavouriteBook(id);
  };
  useEffect(() => {
    if (resultUpdateFavouriteBook.isSuccess) {
      resultGetListBook.refetch();
    }
  }, [resultUpdateFavouriteBook.isSuccess]);

  return (
    <Box sx={{ p: 3 }}>
      <ModalLogin id={MODAL_LOGIN} handleOnSuccess={onLoginSuccess} />
      <Box sx={{ width: '100%', display: 'flex', justifyContent: 'flex-end' }}>
        {!commonReducer.auth.isAuth ? (
          <Button
            startIcon={<LoginIcon color="primary" />}
            variant="text"
            onClick={openModalLogin}
          >
            Đăng nhập
          </Button>
        ) : (
          <Button
            startIcon={<LogoutIcon color="primary" />}
            variant="text"
            onClick={handleLogout}
          >
            Đăng xuất
          </Button>
        )}
      </Box>
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
                      <IconButton onClick={() => onFavouriteClick(book.id)}>
                        {book.isFavourite ? (
                          <BookmarkIcon color="primary" />
                        ) : (
                          <BookmarkBorderIcon color="primary" />
                        )}
                      </IconButton>
                    </Box>
                  </TableCell>
                </TableRow>
              ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};
