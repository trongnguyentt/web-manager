import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebBlogSharedModule } from '../../shared';
import {
    GamesService,
    GamesPopupService,
    GamesComponent,
    GamesDetailComponent,
    GamesDialogComponent,
    GamesPopupComponent,
    GamesDeletePopupComponent,
    GamesDeleteDialogComponent,
    gamesRoute,
    gamesPopupRoute,
    GamesResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...gamesRoute,
    ...gamesPopupRoute,
];

@NgModule({
    imports: [
        WebBlogSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GamesComponent,
        GamesDetailComponent,
        GamesDialogComponent,
        GamesDeleteDialogComponent,
        GamesPopupComponent,
        GamesDeletePopupComponent,
    ],
    entryComponents: [
        GamesComponent,
        GamesDialogComponent,
        GamesPopupComponent,
        GamesDeleteDialogComponent,
        GamesDeletePopupComponent,
    ],
    providers: [
        GamesService,
        GamesPopupService,
        GamesResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebBlogGamesModule {}
