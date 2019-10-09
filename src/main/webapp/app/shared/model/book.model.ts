export interface IBook {
  id?: number;
  title?: string;
  author?: string;
  year?: string;
  publisher?: string;
  pages?: string;
  language?: string;
  topic?: string;
  identifier?: string;
  filesize?: string;
  extension?: string;
  md5?: string;
  coverurl?: string;
  averageRating?: number;
  ratingCount?: number;
  originalPublicationYear?: number;
  originalPublicationMonth?: number;
  originalPublicationDay?: number;
}

export class Book implements IBook {
  constructor(
    public id?: number,
    public title?: string,
    public author?: string,
    public year?: string,
    public publisher?: string,
    public pages?: string,
    public language?: string,
    public topic?: string,
    public identifier?: string,
    public filesize?: string,
    public extension?: string,
    public md5?: string,
    public coverurl?: string,
    public averageRating?: number,
    public ratingCount?: number,
    public originalPublicationYear?: number,
    public originalPublicationMonth?: number,
    public originalPublicationDay?: number
  ) {}
}
