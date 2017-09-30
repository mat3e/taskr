import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { JhiDateUtils } from 'ng-jhipster';

import { Task } from './task.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class TaskService {

    private resourceUrl = '/task/api/tasks';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(task: Task): Observable<Task> {
        const copy = this.convert(task);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(task: Task): Observable<Task> {
        const copy = this.convert(task);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    changeStatus(id: String): Observable<Task> {
        return this.http.put(`${this.resourceUrl}/${id}?status=true`, {}).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<Task> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        options.params.set('sent', req ? req.sent : false);
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
     * Convert a returned JSON object to Task.
     */
    private convertItemFromServer(json: any): Task {
        const entity: Task = Object.assign(new Task(), json);
        entity.dispatchDate = this.dateUtils
            .convertDateTimeFromServer(json.dispatchDate);
        entity.readDate = this.dateUtils
            .convertDateTimeFromServer(json.readDate);
        return entity;
    }

    /**
     * Convert a Task to a JSON which can be sent to the server.
     */
    private convert(task: Task): Task {
        const copy: Task = Object.assign({}, task);

        copy.dispatchDate = this.dateUtils.toDate(task.dispatchDate);

        copy.readDate = this.dateUtils.toDate(task.readDate);
        return copy;
    }
}
