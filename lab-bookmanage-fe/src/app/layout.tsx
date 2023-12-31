import { FallBackComponent } from '@/components/common/fallback/FallBackComponent';
import { Notification } from '@/components/common/notification/Notification';
import { UserProvider } from '@/components/common/userprovider/UserProvider';
import { ThemeProvider } from '@/libs/emotion';
import { ReduxProvider } from '@/redux/provider';
import { muiTheme } from '@/styles/theme';
import type { Metadata } from 'next';
import { Inter } from 'next/font/google';
import './globals.css';

const inter = Inter({ subsets: ['latin'] });

export const metadata: Metadata = {
  title: 'Create Next App',
  description: 'Generated by create next app',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="vi">
      <body className={inter.className}>
        <ReduxProvider>
          <ThemeProvider theme={muiTheme}>
            <UserProvider>
              <Notification />
              <FallBackComponent>{children}</FallBackComponent>
            </UserProvider>
          </ThemeProvider>
        </ReduxProvider>
      </body>
    </html>
  );
}
