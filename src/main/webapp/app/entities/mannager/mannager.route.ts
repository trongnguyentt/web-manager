import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MannagerComponent } from './mannager.component';
import { MannagerDetailComponent } from './mannager-detail.component';
import { MannagerPopupComponent } from './mannager-dialog.component';
import { MannagerDeletePopupComponent } from './mannager-delete-dialog.component';

@Injectable()
export class MannagerResolvePagingParams implements Resolve<any> {

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

export const mannagerRoute: Routes = [
    {
        path: 'mannager',
        component: MannagerComponent,
        resolve: {
            'pagingParams': MannagerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mannagers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mannager/:id',
        component: MannagerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mannagers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mannagerPopupRoute: Routes = [
    {
        path: 'mannager-new',
        component: MannagerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mannagers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mannager/:id/edit',
        component: MannagerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mannagers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mannager/:id/delete',
        component: MannagerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mannagers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
