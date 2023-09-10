'use client';

import {
  Box,
  Button,
  FormControl,
  Paper,
  TextField,
  Typography,
} from '@mui/material';
import { grey } from '@mui/material/colors';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import { useAppDispatch, useAppSelector } from '@/redux/hooks';
import { login } from '@/redux/features/auth/authService';
import { useEffect, useState } from 'react';
import { LoadingButton } from '@mui/lab';

const LoginFormSchema = z.object({
  username: z.string().nonempty({ message: 'Không được bỏ trống' }),
  password: z.string().nonempty({ message: 'Không được bỏ trống' }),
});

type LoginFormInput = z.infer<typeof LoginFormSchema>;

export const LoginComponent = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormInput>({
    resolver: zodResolver(LoginFormSchema),
    mode: 'onBlur',
    defaultValues: {
      username: '',
      password: '',
    },
  });

  const dispatch = useAppDispatch();
  const loginFetchStatus = useAppSelector((state) => state.authReducer);

  useEffect(() => {
    if (loginFetchStatus.data) {
      console.log('login thanh cong');
    }
  }, [loginFetchStatus.data]);

  return (
    <Box
      sx={{
        width: '100%',
        height: '100vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: grey[100],
      }}
    >
      <Paper
        sx={{
          display: 'flex',
          justifyContent: 'center',
          flexDirection: 'column',
          alignItems: 'center',
          p: 3,
        }}
      >
        <Typography variant="h4" sx={{ mb: 5 }}>
          Đăng nhập admin
        </Typography>
        <FormControl
          onSubmit={handleSubmit((data) => {
            dispatch(login(data));
          })}
          component={'form'}
        >
          <TextField
            label="Tên đăng nhập"
            sx={{ mb: 3, width: '256px' }}
            {...register('username')}
            error={!!errors.username?.message}
            helperText={errors.username?.message}
          />
          <TextField
            label="Mật khẩu"
            type="password"
            sx={{ mb: 3, width: '256px' }}
            {...register('password')}
            error={!!errors.password?.message}
            helperText={errors.password?.message}
          />
          <LoadingButton
            variant="contained"
            type="submit"
            loading={loginFetchStatus.isLoading}
          >
            Đăng nhập
          </LoadingButton>
        </FormControl>
      </Paper>
    </Box>
  );
};
