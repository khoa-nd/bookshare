import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { BookService } from 'app/entities/book/book.service';
import { IBook, Book } from 'app/shared/model/book.model';

describe('Service Tests', () => {
  describe('Book Service', () => {
    let injector: TestBed;
    let service: BookService;
    let httpMock: HttpTestingController;
    let elemDefault: IBook;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(BookService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Book(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Book', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Book(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Book', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            author: 'BBBBBB',
            year: 'BBBBBB',
            publisher: 'BBBBBB',
            pages: 'BBBBBB',
            language: 'BBBBBB',
            topic: 'BBBBBB',
            identifier: 'BBBBBB',
            filesize: 'BBBBBB',
            extension: 'BBBBBB',
            md5: 'BBBBBB',
            coverurl: 'BBBBBB',
            averageRating: 1,
            ratingCount: 1,
            originalPublicationYear: 1,
            originalPublicationMonth: 1,
            originalPublicationDay: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Book', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            author: 'BBBBBB',
            year: 'BBBBBB',
            publisher: 'BBBBBB',
            pages: 'BBBBBB',
            language: 'BBBBBB',
            topic: 'BBBBBB',
            identifier: 'BBBBBB',
            filesize: 'BBBBBB',
            extension: 'BBBBBB',
            md5: 'BBBBBB',
            coverurl: 'BBBBBB',
            averageRating: 1,
            ratingCount: 1,
            originalPublicationYear: 1,
            originalPublicationMonth: 1,
            originalPublicationDay: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Book', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
