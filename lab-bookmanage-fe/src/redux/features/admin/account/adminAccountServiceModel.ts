export type GetListAccountsResponse = {
  accountList: {
    id: number;
    email: string;
    role: string;
  }[];
};

export type CreateAccountRequest = {
  email: string;
  password: string;
  role: string;
};
