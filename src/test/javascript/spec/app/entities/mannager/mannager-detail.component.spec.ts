/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WebBlogTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MannagerDetailComponent } from '../../../../../../main/webapp/app/entities/mannager/mannager-detail.component';
import { MannagerService } from '../../../../../../main/webapp/app/entities/mannager/mannager.service';
import { Mannager } from '../../../../../../main/webapp/app/entities/mannager/mannager.model';

describe('Component Tests', () => {

    describe('Mannager Management Detail Component', () => {
        let comp: MannagerDetailComponent;
        let fixture: ComponentFixture<MannagerDetailComponent>;
        let service: MannagerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebBlogTestModule],
                declarations: [MannagerDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MannagerService,
                    JhiEventManager
                ]
            }).overrideTemplate(MannagerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MannagerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MannagerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Mannager(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.mannager).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
