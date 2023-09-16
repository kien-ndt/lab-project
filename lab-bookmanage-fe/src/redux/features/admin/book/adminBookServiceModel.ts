export type GetListBookResponse = {
  bookList: {
    id: number;
    title: string;
    author: string;
    category: string;
  }[];
};

export type GetBookResponse = {
  id: number;
  title: string;
  author: string;
  category: string;
};

export type CreateBookRequest = {
  title: string;
  author: string;
  category: string;
};

export type UpdateBookRequest = {
  id: number;
  title: string;
  author: string;
  category: string;
};
