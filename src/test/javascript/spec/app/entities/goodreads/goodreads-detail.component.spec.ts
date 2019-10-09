import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookshareTestModule } from '../../../test.module';
import { GoodreadsDetailComponent } from 'app/entities/goodreads/goodreads-detail.component';
import { Goodreads } from 'app/shared/model/goodreads.model';

describe('Component Tests', () => {
  describe('Goodreads Management Detail Component', () => {
    let comp: GoodreadsDetailComponent;
    let fixture: ComponentFixture<GoodreadsDetailComponent>;
    const route = ({ data: of({ goodreads: new Goodreads(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookshareTestModule],
        declarations: [GoodreadsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GoodreadsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoodreadsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.goodreads).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
