import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Games } from './games.model';
import { GamesPopupService } from './games-popup.service';
import { GamesService } from './games.service';

@Component({
    selector: 'jhi-games-delete-dialog',
    templateUrl: './games-delete-dialog.component.html'
})
export class GamesDeleteDialogComponent {

    games: Games;

    constructor(
        private gamesService: GamesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.gamesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'gamesListModification',
                content: 'Deleted an games'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-games-delete-popup',
    template: ''
})
export class GamesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private gamesPopupService: GamesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.gamesPopupService
                .open(GamesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
