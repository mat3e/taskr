import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Worker } from './worker.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class WorkerService {

    private resourceUrl = '/worker/api/workers';

    constructor(private http: Http) { }

    create(worker: Worker): Observable<Worker> {
        const copy = this.convert(worker);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(worker: Worker): Observable<Worker> {
        const copy = this.convert(worker);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<Worker> {
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
     * Convert a returned JSON object to Worker.
     */
    private convertItemFromServer(json: any): Worker {
        const entity: Worker = Object.assign(new Worker(), json);
        return entity;
    }

    /**
     * Convert a Worker to a JSON which can be sent to the server.
     */
    private convert(worker: Worker): Worker {
        const copy: Worker = Object.assign({}, worker);
        return copy;
    }
}
