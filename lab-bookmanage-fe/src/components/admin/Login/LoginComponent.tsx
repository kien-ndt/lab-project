'use client';

import { adminAuthApi } from '@/redux/features/admin/auth/adminAuthService';
import { GenericErrorResponse } from '@/redux/features/model';
import { useErrorHandler } from '@/utils/useErrorHandler';
import { zodResolver } from '@hookform/resolvers/zod';
import { LoadingButton } from '@mui/lab';
import {
  Alert,
  Box,
  FormControl,
  Paper,
  TextField,
  Typography,
} from '@mui/material';
import { grey } from '@mui/material/colors';
import { useRouter } from 'next/navigation';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import * as z from 'zod';

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

  const [login, statusLogin] = adminAuthApi.useLoginMutation();

  const [errorHandle] = useErrorHandler<GenericErrorResponse>(
    statusLogin.error,
  );

  const router = useRouter();
  const handleSubmitForm = handleSubmit((data: LoginFormInput) => {
    login(data);
  });

  useEffect(() => {
    if (statusLogin.isSuccess) {
      router.push('/admin/book');
    }
  }, [statusLogin.isSuccess]);

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
        {errorHandle && (
          <Alert severity="error" sx={{ mb: 3 }}>
            {errorHandle.message}
          </Alert>
        )}
        <FormControl onSubmit={handleSubmitForm} component={'form'}>
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
            loading={statusLogin.isLoading}
          >
            Đăng nhập
          </LoadingButton>
        </FormControl>
      </Paper>
    </Box>
  );
};
