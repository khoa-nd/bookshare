import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BookshareTestModule } from '../../../test.module';
import { GoodreadsUpdateComponent } from 'app/entities/goodreads/goodreads-update.component';
import { GoodreadsService } from 'app/entities/goodreads/goodreads.service';
import { Goodreads } from 'app/shared/model/goodreads.model';

describe('Component Tests', () => {
  describe('Goodreads Management Update Component', () => {
    let comp: GoodreadsUpdateComponent;
    let fixture: ComponentFixture<GoodreadsUpdateComponent>;
    let service: GoodreadsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookshareTestModule],
        declarations: [GoodreadsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GoodreadsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoodreadsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoodreadsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Goodreads(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Goodreads();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
