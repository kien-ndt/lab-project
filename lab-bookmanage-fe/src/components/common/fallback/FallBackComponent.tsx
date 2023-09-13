'use client';

import commonSlice from '@/redux/features/common/commonSlice';
import { useAppDispatch, useAppSelector } from '@/redux/hooks';
import { Box } from '@mui/material';
import { useRouter } from 'next/navigation';
import React, { useEffect } from 'react';

type Props = {
  children: React.ReactNode;
};

export const FallBackComponent = (props: Props) => {
  const dispatch = useAppDispatch();
  const commonStatus = useAppSelector((state) => state.commonReducer);
  const router = useRouter();
  useEffect(() => {
    if (commonStatus.error?.navigator) {
      if (commonStatus.error.navigator.redirectUrl) {
        dispatch(commonSlice.actions.resetRedirect());
        router.push(commonStatus.error.navigator.redirectUrl);
      }
    }
  }, [commonStatus]);

  return (
    <>
      {commonStatus?.error?.navigator?.show404 ? (
        <Box>404 Not found</Box>
      ) : (
        props.children
      )}
    </>
  );
};
