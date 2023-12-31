export type GetListBookResponse = {
  bookList: {
    id: number;
    title: string;
    author: string;
    category: string;
    isFavourite: boolean;
  }[];
  isLogin: boolean;
};
