'use client';

import { adminBookApi } from '@/redux/features/admin/book/adminBookService';
import { UpdateBookRequest } from '@/redux/features/admin/book/adminBookServiceModel';
import commonSlice, {
  commonActions,
} from '@/redux/features/common/commonSlice';
import { useAppDispatch, useAppSelector } from '@/redux/hooks';
import { useErrorHandler } from '@/utils/useErrorHandler';
import { zodResolver } from '@hookform/resolvers/zod';
import { LoadingButton } from '@mui/lab';
import {
  Box,
  Button,
  CircularProgress,
  Modal,
  Paper,
  TextField,
  Typography,
} from '@mui/material';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { z } from 'zod';

export type Props = {
  id: string;
  handleOnSuccess: () => void;
};

const ModalUpdateBookSchema = z.object({
  title: z.string().nonempty({ message: 'Không được bỏ trống' }),
  author: z.string().nonempty({ message: 'Không được bỏ trống' }),
  category: z.string().nonempty({ message: 'Không được bỏ trống' }),
});

export type ModalUpdateBookInput = z.infer<typeof ModalUpdateBookSchema>;

export const ModalUpdateBook = (props: Props) => {
  const {
    register,
    handleSubmit,
    reset,
    setValue,
    formState: { errors },
  } = useForm<ModalUpdateBookInput>({
    resolver: zodResolver(ModalUpdateBookSchema),
    mode: 'onBlur',
    defaultValues: {
      title: '',
      author: '',
      category: '',
    },
  });

  const dispatch = useAppDispatch();
  const commonReducer = useAppSelector((state) => state.commonReducer);
  const [getBook, statusGetBook] = adminBookApi.useGetBookMutation();
  const [updateBook, statusUpdateBook] = adminBookApi.useUpdateBookMutation();

  const handleSubmitForm = handleSubmit((data: ModalUpdateBookInput) => {
    const updateBookRequest: UpdateBookRequest = {
      id: commonReducer.modal[props.id].data.bookId,
      title: data.title,
      category: data.category,
      author: data.author,
    };
    updateBook(updateBookRequest);
  });

  useEffect(() => {
    if (commonReducer.modal[props.id]?.open) {
      getBook(commonReducer.modal[props.id].data.bookId);
    }
  }, [commonReducer.modal]);

  useEffect(() => {
    if (statusGetBook.isSuccess) {
      setValue('title', statusGetBook.data.title);
      setValue('author', statusGetBook.data.author);
      setValue('category', statusGetBook.data.category);
    }
  }, [statusGetBook.isSuccess]);
  useErrorHandler(statusGetBook.error);
  useErrorHandler(statusUpdateBook.error);

  useEffect(() => {
    if (statusUpdateBook.isSuccess) {
      dispatch(
        commonActions.showSuccessNotification(statusUpdateBook.data.message),
      );
      reset();
      props.handleOnSuccess();
    }
  }, [statusUpdateBook]);

  return (
    <Modal
      id={props.id}
      open={!!commonReducer.modal[props.id]?.open}
      onClose={() => {
        dispatch(commonSlice.actions.closeModal(props.id));
      }}
      sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}
      keepMounted={false}
    >
      <Paper
        sx={{ width: '400px', p: 3 }}
        component={'form'}
        onSubmit={handleSubmitForm}
      >
        <Typography variant="h5">Chỉnh sửa thông tin sách</Typography>
        {statusGetBook.isLoading ? (
          <CircularProgress />
        ) : (
          <Box>
            <TextField
              label="Tên sách"
              fullWidth
              sx={{ mb: 2 }}
              {...register('title')}
              error={!!errors.title?.message}
              helperText={errors.title?.message}
            />
            <TextField
              label="Tác giả"
              fullWidth
              sx={{ mb: 2 }}
              {...register('author')}
              error={!!errors.author?.message}
              helperText={errors.author?.message}
            />
            <TextField
              label="Thể loại"
              fullWidth
              sx={{ mb: 2 }}
              {...register('category')}
              error={!!errors.category?.message}
              helperText={errors.category?.message}
            />
            <Box sx={{ display: 'flex', justifyContent: 'flex-end' }}>
              <Button
                onClick={() => {
                  dispatch(commonSlice.actions.closeModal(props.id));
                }}
                variant="outlined"
                type="button"
                sx={{ mr: 2 }}
              >
                Hủy
              </Button>
              <LoadingButton
                variant="contained"
                type="submit"
                loading={statusUpdateBook.isLoading}
              >
                Thêm mới
              </LoadingButton>
            </Box>
          </Box>
        )}
      </Paper>
    </Modal>
  );
};
