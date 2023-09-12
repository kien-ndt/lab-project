export type AdminBookGetResponse = {
  bookList: {
    id: number;
    title: string;
    author: string;
    category: string;
  }[];
};
