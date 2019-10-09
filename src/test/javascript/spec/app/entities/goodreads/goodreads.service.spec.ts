import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { GoodreadsService } from 'app/entities/goodreads/goodreads.service';
import { IGoodreads, Goodreads } from 'app/shared/model/goodreads.model';

describe('Service Tests', () => {
  describe('Goodreads Service', () => {
    let injector: TestBed;
    let service: GoodreadsService;
    let httpMock: HttpTestingController;
    let elemDefault: IGoodreads;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(GoodreadsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Goodreads(0, 0, 0, 0, 0, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastUpdated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Goodreads', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastUpdated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            lastUpdated: currentDate
          },
          returnedFromService
        );
        service
          .create(new Goodreads(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Goodreads', () => {
        const returnedFromService = Object.assign(
          {
            bookId: 1,
            gRBookId: 1,
            ratingCount: 1,
            textReviewsCount: 1,
            averageRating: 1,
            lastUpdated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastUpdated: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Goodreads', () => {
        const returnedFromService = Object.assign(
          {
            bookId: 1,
            gRBookId: 1,
            ratingCount: 1,
            textReviewsCount: 1,
            averageRating: 1,
            lastUpdated: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            lastUpdated: currentDate
          },
          returnedFromService
        );
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

      it('should delete a Goodreads', () => {
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
