import type { LoginRequest } from './auth/authModel';
import type { AdminBookGetResponse } from './book/bookModel';

type GenericResponse = {
  message: string;
};
type GenericErrorResponse = {
  message: string;
};

export type {
  AdminBookGetResponse,
  GenericErrorResponse,
  GenericResponse,
  LoginRequest,
};
