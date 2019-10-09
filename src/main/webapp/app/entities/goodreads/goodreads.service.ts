import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGoodreads } from 'app/shared/model/goodreads.model';

type EntityResponseType = HttpResponse<IGoodreads>;
type EntityArrayResponseType = HttpResponse<IGoodreads[]>;

@Injectable({ providedIn: 'root' })
export class GoodreadsService {
  public resourceUrl = SERVER_API_URL + 'api/goodreads';

  constructor(protected http: HttpClient) {}

  create(goodreads: IGoodreads): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(goodreads);
    return this.http
      .post<IGoodreads>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(goodreads: IGoodreads): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(goodreads);
    return this.http
      .put<IGoodreads>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGoodreads>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGoodreads[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(goodreads: IGoodreads): IGoodreads {
    const copy: IGoodreads = Object.assign({}, goodreads, {
      lastUpdated: goodreads.lastUpdated != null && goodreads.lastUpdated.isValid() ? goodreads.lastUpdated.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastUpdated = res.body.lastUpdated != null ? moment(res.body.lastUpdated) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((goodreads: IGoodreads) => {
        goodreads.lastUpdated = goodreads.lastUpdated != null ? moment(goodreads.lastUpdated) : null;
      });
    }
    return res;
  }
}
