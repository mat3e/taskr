import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { JobGroup } from './job-group.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class JobGroupService {

    private resourceUrl = '/worker/api/job-groups';

    constructor(private http: Http) { }

    create(jobGroup: JobGroup): Observable<JobGroup> {
        const copy = this.convert(jobGroup);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(jobGroup: JobGroup): Observable<JobGroup> {
        const copy = this.convert(jobGroup);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<JobGroup> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to JobGroup.
     */
    private convertItemFromServer(json: any): JobGroup {
        const entity: JobGroup = Object.assign(new JobGroup(), json);
        return entity;
    }

    /**
     * Convert a JobGroup to a JSON which can be sent to the server.
     */
    private convert(jobGroup: JobGroup): JobGroup {
        const copy: JobGroup = Object.assign({}, jobGroup);
        return copy;
    }
}
