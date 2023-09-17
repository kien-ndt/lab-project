import { commonActions } from '@/redux/features/common/commonSlice';
import { GenericResponse } from '@/redux/features/model';
import { useAppDispatch } from '@/redux/hooks';
import { SerializedError } from '@reduxjs/toolkit';
import { FetchBaseQueryError } from '@reduxjs/toolkit/query';
import { useEffect, useState } from 'react';

export const useErrorHandler = <T>(
  err: SerializedError | FetchBaseQueryError | undefined,
  isFetching?: boolean,
) => {
  const [errorHandle, setError] = useState<T>();
  const dispatch = useAppDispatch();
  useEffect(() => {
    if (isFetching) return;
    if (!err) {
      setError(undefined);
      return;
    }
    if ('data' in err) {
      if (typeof err.status === 'number' && err.status === 403) {
        dispatch(commonActions.redirectAdminLogin());
        return;
      }
      setError(err.data as T);
      (err.data as GenericResponse)?.message &&
        dispatch(
          commonActions.showErrorNotification(
            (err.data as GenericResponse).message,
          ),
        );
    } else {
      setError(undefined);
      dispatch(commonActions.redirect404());
    }
  }, [err, isFetching]);
  return [errorHandle] as const;
};
