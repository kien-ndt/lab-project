import commonSlice from '@/redux/features/common/commonSlice';
import { useAppDispatch } from '@/redux/hooks';
import { SerializedError } from '@reduxjs/toolkit';
import { FetchBaseQueryError } from '@reduxjs/toolkit/query';
import { useEffect, useState } from 'react';

export const useErrorHandler = <T>(
  err: SerializedError | FetchBaseQueryError | undefined,
) => {
  const [errorHandle, setError] = useState<T>();
  const dispatch = useAppDispatch();
  useEffect(() => {
    if (!err) {
      setError(undefined);
      return;
    }
    if ('data' in err) {
      if (typeof err.status === 'number' && err.status === 403) {
        dispatch(commonSlice.actions.redirectAdminLogin());
      }
      setError(err.data as T);
    } else {
      setError(undefined);
      dispatch(commonSlice.actions.redirect404());
    }
  }, [err]);
  return [errorHandle] as const;
};
