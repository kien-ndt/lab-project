'use client';

import { adminAuthApi } from '@/redux/features/admin/auth/adminAuthService';
import LibraryBooksIcon from '@mui/icons-material/LibraryBooks';
import LogoutIcon from '@mui/icons-material/Logout';
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
import {
  Box,
  Button,
  Divider,
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Paper,
} from '@mui/material';
import { grey } from '@mui/material/colors';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import React from 'react';

export type Props = {
  children: React.ReactNode;
};

export const ManagementLayout = (props: Props) => {
  const [authLogout, resultAuthLogout] = adminAuthApi.useLogoutMutation();
  const router = useRouter();

  const handleLogout = () => {
    authLogout().then(() => router.push('/admin'));
  };

  return (
    <Box sx={{ minWidth: '100%', minHeight: '100vh', display: 'flex' }}>
      <Drawer variant="persistent" sx={{ width: '250px' }} open={true}>
        <Button
          startIcon={<LogoutIcon color="primary" />}
          variant="text"
          onClick={handleLogout}
        >
          Đăng xuất
        </Button>
        <Divider />
        <List sx={{ width: '250px' }}>
          <ListItem disablePadding>
            <ListItemButton
              color="primary"
              LinkComponent={Link}
              href="/admin/book"
            >
              <ListItemIcon>
                <LibraryBooksIcon color="primary" />
              </ListItemIcon>
              <ListItemText primary={'Sách'} />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding>
            <ListItemButton
              color="primary"
              LinkComponent={Link}
              href="/admin/account"
            >
              <ListItemIcon>
                <ManageAccountsIcon color="primary" />
              </ListItemIcon>
              <ListItemText primary={'Tài khoản'} />
            </ListItemButton>
          </ListItem>
        </List>
      </Drawer>
      <Box sx={{ bgcolor: grey[200], flex: 1, pt: 4, pr: 3, pb: 4, pl: 3 }}>
        <Paper
          sx={{ height: '100%', p: 3, boxSizing: 'border-box' }}
          elevation={4}
        >
          {props.children}
        </Paper>
      </Box>
    </Box>
  );
};
