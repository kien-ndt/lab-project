'use client';

import { adminAccountApi } from '@/redux/features/admin/account/adminAccountService';
import { CreateAccountRequest } from '@/redux/features/admin/account/adminAccountServiceModel';
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
  FormControl,
  FormHelperText,
  InputLabel,
  MenuItem,
  Modal,
  Paper,
  Select,
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

const ModalCreateAccountSchema = z.object({
  email: z.string().nonempty({ message: 'Không được bỏ trống' }),
  password: z.string().nonempty({ message: 'Không được bỏ trống' }),
  role: z.string().nonempty({ message: 'Không được bỏ trống' }),
});

export type ModalCreateAccountInput = z.infer<typeof ModalCreateAccountSchema>;

export const ModalCreateAccount = (props: Props) => {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<ModalCreateAccountInput>({
    resolver: zodResolver(ModalCreateAccountSchema),
    mode: 'onBlur',
  });

  const dispatch = useAppDispatch();
  const commonReducer = useAppSelector((state) => state.commonReducer);
  const [createAccount, resultCreateAccount] =
    adminAccountApi.useCreateAccountMutation();
  useErrorHandler(
    !resultCreateAccount.isLoading ? resultCreateAccount.error : undefined,
  );

  const handleSubmitForm = handleSubmit((data: ModalCreateAccountInput) => {
    const createAccountRequest: CreateAccountRequest = {
      email: data.email,
      password: data.password,
      role: data.role,
    };
    createAccount(createAccountRequest);
  });

  useEffect(() => {
    if (resultCreateAccount.isSuccess) {
      dispatch(
        commonActions.showSuccessNotification(resultCreateAccount.data.message),
      );
      reset();
      props.handleOnSuccess();
    }
  }, [resultCreateAccount.isSuccess]);

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
        <Typography variant="h5">Thêm tài khoản mới</Typography>
        <TextField
          label="Email"
          fullWidth
          sx={{ mb: 2 }}
          {...register('email')}
          error={!!errors.email?.message}
          helperText={errors.email?.message}
        />
        <TextField
          label="Mật khẩu"
          fullWidth
          sx={{ mb: 2 }}
          {...register('password')}
          error={!!errors.password?.message}
          helperText={errors.password?.message}
          type="password"
        />
        <FormControl fullWidth error={!!errors.role?.message} sx={{ mb: 2 }}>
          <InputLabel>Quyền hạn</InputLabel>
          <Select label="Quyền hạn" {...register('role')}>
            <MenuItem value={'ADMIN'}>ADMIN</MenuItem>
            <MenuItem value={'USER'}>USER</MenuItem>
            {!!errors.role && (
              <FormHelperText>{errors.role.message}</FormHelperText>
            )}
          </Select>
        </FormControl>
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
            loading={resultCreateAccount.isLoading}
          >
            Thêm mới
          </LoadingButton>
        </Box>
      </Paper>
    </Modal>
  );
};
