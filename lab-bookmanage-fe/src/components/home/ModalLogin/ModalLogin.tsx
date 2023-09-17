import { commonActions } from '@/redux/features/common/commonSlice';
import { GenericErrorResponse } from '@/redux/features/model';
import { userAuthApi } from '@/redux/features/user/auth/userAuthService';
import { useAppDispatch, useAppSelector } from '@/redux/hooks';
import { useUserErrorHandler } from '@/utils/useUserErrorHandler';
import { zodResolver } from '@hookform/resolvers/zod';
import { LoadingButton } from '@mui/lab';
import {
  Alert,
  FormControl,
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

const LoginFormSchema = z.object({
  username: z.string().nonempty({ message: 'Không được bỏ trống' }),
  password: z.string().nonempty({ message: 'Không được bỏ trống' }),
});

type LoginFormInput = z.infer<typeof LoginFormSchema>;

export const MODAL_LOGIN = 'modal-login';

export const ModalLogin = (props: Props) => {
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
  const commonReducer = useAppSelector((state) => state.commonReducer);
  const [login, resultLogin] = userAuthApi.useLoginMutation();

  const [errorHandle] = useUserErrorHandler<GenericErrorResponse>(
    resultLogin.error,
  );

  const handleSubmitForm = handleSubmit((data: LoginFormInput) => {
    login(data);
  });

  useEffect(() => {
    if (resultLogin.isSuccess) {
      props.handleOnSuccess();
      dispatch(commonActions.setAuth(true));
      localStorage.setItem('isAuth', '1');
      dispatch(commonActions.showSuccessNotification(resultLogin.data.message));
    }
  }, [resultLogin]);

  return (
    <Modal
      id={props.id}
      open={!!commonReducer.modal[props.id]?.open}
      onClose={() => {
        dispatch(commonActions.closeModal(props.id));
      }}
      sx={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}
      keepMounted={false}
    >
      <Paper sx={{ width: '400px', p: 3 }}>
        <Typography variant="h5">Đăng nhập user</Typography>

        {errorHandle && (
          <Alert severity="error" sx={{ mb: 3 }}>
            {errorHandle.message}
          </Alert>
        )}

        <FormControl onSubmit={handleSubmitForm} component={'form'} fullWidth>
          <TextField
            label="Tên đăng nhập"
            sx={{ mb: 3 }}
            {...register('username')}
            error={!!errors.username?.message}
            helperText={errors.username?.message}
            fullWidth
          />
          <TextField
            label="Mật khẩu"
            type="password"
            sx={{ mb: 3 }}
            {...register('password')}
            error={!!errors.password?.message}
            helperText={errors.password?.message}
            fullWidth
          />
          <LoadingButton
            variant="contained"
            type="submit"
            loading={resultLogin.isLoading}
            fullWidth
          >
            Đăng nhập
          </LoadingButton>
        </FormControl>
      </Paper>
    </Modal>
  );
};
