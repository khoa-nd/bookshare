import { Moment } from 'moment';

export interface IGoodreads {
  id?: number;
  bookId?: number;
  gRBookId?: number;
  ratingCount?: number;
  textReviewsCount?: number;
  averageRating?: number;
  lastUpdated?: Moment;
}

export class Goodreads implements IGoodreads {
  constructor(
    public id?: number,
    public bookId?: number,
    public gRBookId?: number,
    public ratingCount?: number,
    public textReviewsCount?: number,
    public averageRating?: number,
    public lastUpdated?: Moment
  ) {}
}
