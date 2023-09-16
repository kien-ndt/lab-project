'use client';

import { commonActions } from '@/redux/features/common/commonSlice';
import { useAppDispatch, useAppSelector } from '@/redux/hooks';
import { Alert, Snackbar } from '@mui/material';

export const Notification = () => {
  const dispatch = useAppDispatch();
  const commonNotificationReducer = useAppSelector(
    (state) => state.commonReducer.notification,
  );
  const onClose = () => {
    dispatch(commonActions.hiddenNotification());
  };
  return (
    <>
      {commonNotificationReducer && (
        <Snackbar
          open={!!commonNotificationReducer}
          autoHideDuration={5000}
          onClose={onClose}
          anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
        >
          <Alert
            onClose={onClose}
            severity={
              commonNotificationReducer && commonNotificationReducer.success
                ? 'success'
                : 'error'
            }
            sx={{ width: '100%' }}
          >
            {commonNotificationReducer?.success ||
              commonNotificationReducer?.error}
          </Alert>
        </Snackbar>
      )}
    </>
  );
};
