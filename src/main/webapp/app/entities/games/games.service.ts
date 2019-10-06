import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Games } from './games.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class GamesService {

    private resourceUrl = SERVER_API_URL + 'api/games';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(games: Games): Observable<Games> {
        const copy = this.convert(games);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(games: Games): Observable<Games> {
        const copy = this.convert(games);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Games> {
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
     * Convert a returned JSON object to Games.
     */
    private convertItemFromServer(json: any): Games {
        const entity: Games = Object.assign(new Games(), json);
        entity.thoiGianTao = this.dateUtils
            .convertDateTimeFromServer(json.thoiGianTao);
        entity.thoiGianTruyCapCuoi = this.dateUtils
            .convertDateTimeFromServer(json.thoiGianTruyCapCuoi);
        return entity;
    }

    /**
     * Convert a Games to a JSON which can be sent to the server.
     */
    private convert(games: Games): Games {
        const copy: Games = Object.assign({}, games);

        copy.thoiGianTao = this.dateUtils.toDate(games.thoiGianTao);

        copy.thoiGianTruyCapCuoi = this.dateUtils.toDate(games.thoiGianTruyCapCuoi);
        return copy;
    }
}
