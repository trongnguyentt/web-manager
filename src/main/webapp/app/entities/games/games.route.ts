import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { GamesComponent } from './games.component';
import { GamesDetailComponent } from './games-detail.component';
import { GamesPopupComponent } from './games-dialog.component';
import { GamesDeletePopupComponent } from './games-delete-dialog.component';

@Injectable()
export class GamesResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const gamesRoute: Routes = [
    {
        path: 'gamexx',
        component: GamesComponent,
        resolve: {
            'pagingParams': GamesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Games'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'games/:id',
        component: GamesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Games'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gamesPopupRoute: Routes = [
    {
        path: 'games-new',
        component: GamesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Games'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'games/:id/edit',
        component: GamesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Games'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'games/:id/delete',
        component: GamesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Games'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
