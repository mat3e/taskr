/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TaskrTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { JobGroupDetailComponent } from '../../../../../../main/webapp/app/entities/job-group/job-group-detail.component';
import { JobGroupService } from '../../../../../../main/webapp/app/entities/job-group/job-group.service';
import { JobGroup } from '../../../../../../main/webapp/app/entities/job-group/job-group.model';

describe('Component Tests', () => {

    describe('JobGroup Management Detail Component', () => {
        let comp: JobGroupDetailComponent;
        let fixture: ComponentFixture<JobGroupDetailComponent>;
        let service: JobGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TaskrTestModule],
                declarations: [JobGroupDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    JobGroupService,
                    JhiEventManager
                ]
            }).overrideTemplate(JobGroupDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobGroupDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobGroupService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new JobGroup('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.jobGroup).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
