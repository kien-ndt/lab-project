'use client';

import { commonActions } from '@/redux/features/common/commonSlice';
import { useAppDispatch } from '@/redux/hooks';
import { useEffect } from 'react';

type Props = {
  children: React.ReactNode;
};

export const UserProvider = (props: Props) => {
  const dispatch = useAppDispatch();
  useEffect(() => {
    const isAuth = localStorage.getItem('isAuth');
    if (!isAuth) {
      dispatch(commonActions.setAuth(false));
    } else {
      dispatch(commonActions.setAuth(true));
    }
  }, []);
  return <>{props.children}</>;
};
