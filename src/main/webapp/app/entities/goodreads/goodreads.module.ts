import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookshareSharedModule } from 'app/shared/shared.module';
import { GoodreadsComponent } from './goodreads.component';
import { GoodreadsDetailComponent } from './goodreads-detail.component';
import { GoodreadsUpdateComponent } from './goodreads-update.component';
import { GoodreadsDeletePopupComponent, GoodreadsDeleteDialogComponent } from './goodreads-delete-dialog.component';
import { goodreadsRoute, goodreadsPopupRoute } from './goodreads.route';

const ENTITY_STATES = [...goodreadsRoute, ...goodreadsPopupRoute];

@NgModule({
  imports: [BookshareSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    GoodreadsComponent,
    GoodreadsDetailComponent,
    GoodreadsUpdateComponent,
    GoodreadsDeleteDialogComponent,
    GoodreadsDeletePopupComponent
  ],
  entryComponents: [GoodreadsDeleteDialogComponent]
})
export class BookshareGoodreadsModule {}
