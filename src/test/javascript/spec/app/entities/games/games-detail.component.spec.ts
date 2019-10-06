/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WebBlogTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GamesDetailComponent } from '../../../../../../main/webapp/app/entities/games/games-detail.component';
import { GamesService } from '../../../../../../main/webapp/app/entities/games/games.service';
import { Games } from '../../../../../../main/webapp/app/entities/games/games.model';

describe('Component Tests', () => {

    describe('Games Management Detail Component', () => {
        let comp: GamesDetailComponent;
        let fixture: ComponentFixture<GamesDetailComponent>;
        let service: GamesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WebBlogTestModule],
                declarations: [GamesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GamesService,
                    JhiEventManager
                ]
            }).overrideTemplate(GamesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GamesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GamesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Games(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.games).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
