import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Goodreads } from 'app/shared/model/goodreads.model';
import { GoodreadsService } from './goodreads.service';
import { GoodreadsComponent } from './goodreads.component';
import { GoodreadsDetailComponent } from './goodreads-detail.component';
import { GoodreadsUpdateComponent } from './goodreads-update.component';
import { GoodreadsDeletePopupComponent } from './goodreads-delete-dialog.component';
import { IGoodreads } from 'app/shared/model/goodreads.model';

@Injectable({ providedIn: 'root' })
export class GoodreadsResolve implements Resolve<IGoodreads> {
  constructor(private service: GoodreadsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IGoodreads> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Goodreads>) => response.ok),
        map((goodreads: HttpResponse<Goodreads>) => goodreads.body)
      );
    }
    return of(new Goodreads());
  }
}

export const goodreadsRoute: Routes = [
  {
    path: '',
    component: GoodreadsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'bookshareApp.goodreads.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GoodreadsDetailComponent,
    resolve: {
      goodreads: GoodreadsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bookshareApp.goodreads.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GoodreadsUpdateComponent,
    resolve: {
      goodreads: GoodreadsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bookshareApp.goodreads.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GoodreadsUpdateComponent,
    resolve: {
      goodreads: GoodreadsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bookshareApp.goodreads.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const goodreadsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: GoodreadsDeletePopupComponent,
    resolve: {
      goodreads: GoodreadsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bookshareApp.goodreads.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
