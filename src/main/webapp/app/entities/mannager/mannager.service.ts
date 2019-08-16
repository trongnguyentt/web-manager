import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Mannager } from './mannager.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MannagerService {

    private resourceUrl = SERVER_API_URL + 'api/mannagers';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(mannager: Mannager): Observable<Mannager> {
        const copy = this.convert(mannager);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(mannager: Mannager): Observable<Mannager> {
        const copy = this.convert(mannager);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Mannager> {
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

    delete(id: number): Observable<Response> {
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
     * Convert a returned JSON object to Mannager.
     */
    private convertItemFromServer(json: any): Mannager {
        const entity: Mannager = Object.assign(new Mannager(), json);
        entity.createdDate = this.dateUtils
            .convertDateTimeFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a Mannager to a JSON which can be sent to the server.
     */
    private convert(mannager: Mannager): Mannager {
        const copy: Mannager = Object.assign({}, mannager);

        copy.createdDate = this.dateUtils.toDate(mannager.createdDate);
        return copy;
    }
}
