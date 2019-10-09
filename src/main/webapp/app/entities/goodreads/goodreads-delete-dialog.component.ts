import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGoodreads } from 'app/shared/model/goodreads.model';
import { GoodreadsService } from './goodreads.service';

@Component({
  selector: 'jhi-goodreads-delete-dialog',
  templateUrl: './goodreads-delete-dialog.component.html'
})
export class GoodreadsDeleteDialogComponent {
  goodreads: IGoodreads;

  constructor(protected goodreadsService: GoodreadsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.goodreadsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'goodreadsListModification',
        content: 'Deleted an goodreads'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-goodreads-delete-popup',
  template: ''
})
export class GoodreadsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ goodreads }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(GoodreadsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.goodreads = goodreads;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/goodreads', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/goodreads', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
