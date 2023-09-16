'use client';

import { createTheme } from '@/libs/mui';

export const muiTheme = createTheme({
  typography: {
    fontFamily: 'Roboto',
    button: {
      fontSize: '1rem',
      textTransform: 'none',
    },
    h5: {
      fontWeight: 700,
      marginBottom: '16px',
    },
  },
});
