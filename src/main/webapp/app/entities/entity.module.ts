import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'book',
        loadChildren: () => import('./book/book.module').then(m => m.BookshareBookModule)
      },
      {
        path: 'goodreads',
        loadChildren: () => import('./goodreads/goodreads.module').then(m => m.BookshareGoodreadsModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BookshareEntityModule {}
