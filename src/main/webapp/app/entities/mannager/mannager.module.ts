import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebBlogSharedModule } from '../../shared';
import {
    MannagerService,
    MannagerPopupService,
    MannagerComponent,
    MannagerDetailComponent,
    MannagerDialogComponent,
    MannagerPopupComponent,
    MannagerDeletePopupComponent,
    MannagerDeleteDialogComponent,
    mannagerRoute,
    mannagerPopupRoute,
    MannagerResolvePagingParams,
    MannagerCountComponent,
    MannagerCountPopupComponent,
} from './';

const ENTITY_STATES = [
    ...mannagerRoute,
    ...mannagerPopupRoute,
];

@NgModule({
    imports: [
        WebBlogSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MannagerComponent,
        MannagerDetailComponent,
        MannagerDialogComponent,
        MannagerDeleteDialogComponent,
        MannagerPopupComponent,
        MannagerDeletePopupComponent,
        MannagerCountPopupComponent,
        MannagerCountComponent,
    ],
    entryComponents: [
        MannagerComponent,
        MannagerDialogComponent,
        MannagerPopupComponent,
        MannagerDeleteDialogComponent,
        MannagerDeletePopupComponent,
        MannagerCountComponent,
        MannagerCountPopupComponent,
    ],
    providers: [
        MannagerService,
        MannagerPopupService,
        MannagerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebBlogMannagerModule {}
