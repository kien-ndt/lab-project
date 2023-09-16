'use client';

import { adminBookApi } from '@/redux/features/admin/book/adminBookService';
import { CreateBookRequest } from '@/redux/features/admin/book/adminBookServiceModel';
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

const ModalCreateBookSchema = z.object({
  title: z.string().nonempty({ message: 'Không được bỏ trống' }),
  author: z.string().nonempty({ message: 'Không được bỏ trống' }),
  category: z.string().nonempty({ message: 'Không được bỏ trống' }),
});

export type ModalCreateBookInput = z.infer<typeof ModalCreateBookSchema>;

export const ModalCreateBook = (props: Props) => {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<ModalCreateBookInput>({
    resolver: zodResolver(ModalCreateBookSchema),
    mode: 'onBlur',
    defaultValues: {
      title: '',
      category: '',
    },
  });

  const dispatch = useAppDispatch();
  const commonReducer = useAppSelector((state) => state.commonReducer);
  const [createBook, statusCreateBook] = adminBookApi.useCreateBookMutation();

  const handleSubmitForm = handleSubmit((data: ModalCreateBookInput) => {
    const createBookRequest: CreateBookRequest = {
      title: data.title,
      category: data.category,
      author: data.author,
    };
    createBook(createBookRequest);
  });

  useEffect(() => {
    if (statusCreateBook.isSuccess) {
      dispatch(
        commonActions.showSuccessNotification(statusCreateBook.data.message),
      );
      reset();
      props.handleOnSuccess();
    }
  }, [statusCreateBook.isSuccess]);
  useErrorHandler(statusCreateBook.error);

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
        <Typography variant="h5">Thêm sách mới</Typography>
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
            loading={statusCreateBook.isLoading}
          >
            Thêm mới
          </LoadingButton>
        </Box>
      </Paper>
    </Modal>
  );
};
