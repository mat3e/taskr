/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TaskrTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { JobDetailComponent } from '../../../../../../main/webapp/app/entities/job/job-detail.component';
import { JobService } from '../../../../../../main/webapp/app/entities/job/job.service';
import { Job } from '../../../../../../main/webapp/app/entities/job/job.model';

describe('Component Tests', () => {

    describe('Job Management Detail Component', () => {
        let comp: JobDetailComponent;
        let fixture: ComponentFixture<JobDetailComponent>;
        let service: JobService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TaskrTestModule],
                declarations: [JobDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    JobService,
                    JhiEventManager
                ]
            }).overrideTemplate(JobDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Job('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.job).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
