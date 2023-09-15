'use client';

import { Box, Drawer, List, Paper } from '@mui/material';
import { grey } from '@mui/material/colors';
import React from 'react';

export type Props = {
  children: React.ReactNode;
};

export const ManagementLayout = (props: Props) => {
  return (
    <Box sx={{ minWidth: '100%', minHeight: '100vh', display: 'flex' }}>
      <Drawer variant="persistent" sx={{ width: '250px' }}>
        <List></List>
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
