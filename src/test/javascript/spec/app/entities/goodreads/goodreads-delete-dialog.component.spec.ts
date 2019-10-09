import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BookshareTestModule } from '../../../test.module';
import { GoodreadsDeleteDialogComponent } from 'app/entities/goodreads/goodreads-delete-dialog.component';
import { GoodreadsService } from 'app/entities/goodreads/goodreads.service';

describe('Component Tests', () => {
  describe('Goodreads Management Delete Component', () => {
    let comp: GoodreadsDeleteDialogComponent;
    let fixture: ComponentFixture<GoodreadsDeleteDialogComponent>;
    let service: GoodreadsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookshareTestModule],
        declarations: [GoodreadsDeleteDialogComponent]
      })
        .overrideTemplate(GoodreadsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoodreadsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoodreadsService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
