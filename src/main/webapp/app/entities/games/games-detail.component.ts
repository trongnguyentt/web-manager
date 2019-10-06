import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Games } from './games.model';
import { GamesService } from './games.service';

@Component({
    selector: 'jhi-games-detail',
    templateUrl: './games-detail.component.html'
})
export class GamesDetailComponent implements OnInit, OnDestroy {

    games: Games;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private gamesService: GamesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGames();
    }

    load(id) {
        this.gamesService.find(id).subscribe((games) => {
            this.games = games;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGames() {
        this.eventSubscriber = this.eventManager.subscribe(
            'gamesListModification',
            (response) => this.load(this.games.id)
        );
    }
}
