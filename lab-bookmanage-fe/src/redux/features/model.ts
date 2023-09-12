import type { LoginRequest } from './auth/authModel';
import type { AdminBookGetResponse } from './book/bookModel';

type GenericErrorResponse = {
  message: string;
};

export type { AdminBookGetResponse, GenericErrorResponse, LoginRequest };
