'use client';

import { ManagementLayout } from '@/components/common/layout/ManagementLayout';
import { adminAccountApi } from '@/redux/features/admin/account/adminAccountService';
import { commonActions } from '@/redux/features/common/commonSlice';
import { useAppDispatch } from '@/redux/hooks';
import { useErrorHandler } from '@/utils/useErrorHandler';
import AddIcon from '@mui/icons-material/Add';
import {
  Box,
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from '@mui/material';
import { grey } from '@mui/material/colors';
import { ModalCreateAccount } from './ModalCreateAccount/ModalCreateAccount';

const MODAL_CREATE_ACCOUNT = 'modal-create-account';

export const AccountComponent = () => {
  const dispatch = useAppDispatch();

  const resultGetListAccount = adminAccountApi.useGetListAccountQuery();

  useErrorHandler(resultGetListAccount.error, resultGetListAccount.isFetching);

  const openModalCreateAccount = () => {
    dispatch(commonActions.openModal(MODAL_CREATE_ACCOUNT));
  };
  const handleOnCreateSuccess = () => {
    dispatch(commonActions.closeModal(MODAL_CREATE_ACCOUNT));
    resultGetListAccount.refetch();
  };

  return (
    <ManagementLayout>
      <ModalCreateAccount
        id={MODAL_CREATE_ACCOUNT}
        handleOnSuccess={handleOnCreateSuccess}
      />
      <Box
        sx={{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
        }}
      >
        <Button
          endIcon={<AddIcon />}
          variant="contained"
          size="small"
          sx={{ ml: 2 }}
          onClick={openModalCreateAccount}
        >
          Thêm mới
        </Button>
      </Box>

      <TableContainer sx={{ mt: 3 }}>
        <Table>
          <TableHead sx={{ bgcolor: grey[100] }}>
            <TableRow>
              <TableCell sx={{ fontWeight: 700 }}>STT</TableCell>
              <TableCell sx={{ fontWeight: 700 }}>Email</TableCell>
              <TableCell sx={{ fontWeight: 700 }}>Quyền hạn</TableCell>
              {/* <TableCell sx={{ fontWeight: 700 }}></TableCell> */}
            </TableRow>
          </TableHead>
          <TableBody>
            {resultGetListAccount.isSuccess &&
              resultGetListAccount.data.accountList &&
              resultGetListAccount.data.accountList.length > 0 &&
              resultGetListAccount.data.accountList.map((account, index) => (
                <TableRow key={index} hover>
                  <TableCell>{index + 1}</TableCell>
                  <TableCell>{account.email}</TableCell>
                  <TableCell>{account.role}</TableCell>
                  {/* <TableCell>
                    <Box>
                      <IconButton onClick={() => openModalUpdateBook(book.id)}>
                        <EditNoteIcon color="primary" />
                      </IconButton>
                      <LoadingButton
                        onClick={() => handleOnDelete(book.id)}
                        loading={resultDeleteBook.isLoading}
                      >
                        <DeleteIcon color="warning" />
                      </LoadingButton>
                    </Box>
                  </TableCell> */}
                </TableRow>
              ))}
          </TableBody>
        </Table>
      </TableContainer>
    </ManagementLayout>
  );
};
