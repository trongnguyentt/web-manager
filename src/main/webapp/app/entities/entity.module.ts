import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WebBlogMannagerModule } from './mannager/mannager.module';
import { WebBlogGamesModule } from './games/games.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        WebBlogMannagerModule,
        WebBlogGamesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebBlogEntityModule {}
